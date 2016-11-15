package org.cleverframe.sys.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.shiro.session.Session;
import org.cleverframe.common.ehcache.EhCacheNames;
import org.cleverframe.common.ehcache.EhCacheUtils;
import org.cleverframe.sys.entity.User;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 维护登录的用户和其SessionID关系结构数据，用于实现限制一个用户并发登录数量<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/15 14:42 <br/>
 */
public class KickOutCacheUtils {

    /**
     * 数据存储缓存(Ehcache实现)<br/>
     */
    private static Cache KickOutCache;

    /**
     * 返回数据存储的缓存
     */
    private static Cache getKickOutCache() {
        if (KickOutCache == null) {
            KickOutCache = EhCacheUtils.createCache(EhCacheNames.ShiroKickOutCache);
        }
        return KickOutCache;
    }

    /**
     * 返回需要踢出的人的
     *
     * 根据登入名称获取所有登录的SessionID集合,如果集合中不包含当前SessionID或集合为空,就把当前SessionID存入集合 并保存到缓存中<br/>
     * 移除无效的SessionID 和 未登录的SessionID<br/>
     * <b>注意:此方法是同步方法 保证线程安全</b>
     *
     * @param loginName     当前登录用户的名称
     * @param sessionId     当前登录用户的SessionID
     * @param maxLoginCount 同一个用户最多同时登录次数
     * @return 返回需要踢出的人SessionID, 不需要踢出返回null
     */
    @SuppressWarnings("unchecked")
    public static synchronized List<Serializable> getKickOutSessionId(String loginName, Serializable sessionId, int maxLoginCount, boolean kickOutBefore) {
        Deque<Serializable> loginSessionDeque;
        Cache cache = getKickOutCache();
        Element element = cache.get(loginName);
        if (element == null) {
            loginSessionDeque = new ArrayDeque<>();
            element = new Element(loginName, loginSessionDeque);
        } else {
            Object object = element.getObjectValue();
            if ((object == null) || !(object instanceof Deque)) {
                cache.remove(loginName);
                loginSessionDeque = new ArrayDeque<>();
            } else {
                loginSessionDeque = (Deque<Serializable>) object;
            }
        }
        boolean isSave = false;
        // 如果不包含当前Session ID，添加当前Session ID再保存
        if (!loginSessionDeque.contains(sessionId)) {
            loginSessionDeque.push(sessionId);
            isSave = true;
        }
        // 验证所有的 SessionID 有效(Session没有过期而且登录用户已经登录)
        List<Serializable> invalidSessionId = new ArrayList<>();
        for (Serializable sId : loginSessionDeque) {
            if (sessionId.equals(sId)) {
                //不用检查当前的 SessionId
                continue;
            }
            Session s = ShiroSessionUtils.getSession(sId);
            if (s == null) {
                invalidSessionId.add(sId);
                continue;
            }
            User u = ShiroSessionUtils.getUserBySession(s);
            if (u == null || !loginName.equals(u.getLoginName())) {
                invalidSessionId.add(sId);
            }
        }
        if (invalidSessionId.size() > 0) {
            isSave = true;
        }
        for (Serializable sId : invalidSessionId) {
            loginSessionDeque.remove(sId);
        }
        // 踢出用户
        List<Serializable> kickOutSessionIdList = new ArrayList<>();
        while (loginSessionDeque.size() > maxLoginCount) {
            Serializable kickOutSessionId;
            if (kickOutBefore) {
                //移除最先push的元素
                kickOutSessionId = loginSessionDeque.removeLast();
            } else {
                //移除最后push的元素
                kickOutSessionId = loginSessionDeque.removeFirst();
            }
            kickOutSessionIdList.add(kickOutSessionId);
            isSave = true;
        }
        // 更新缓存
        if (isSave) {
            cache.put(element);
        }
        return kickOutSessionIdList;
    }
}
