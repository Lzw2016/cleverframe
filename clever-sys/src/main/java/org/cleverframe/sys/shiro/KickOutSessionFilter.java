package org.cleverframe.sys.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.utils.HttpSessionUtils;
import org.cleverframe.sys.utils.KickOutCacheUtils;
import org.cleverframe.sys.utils.ShiroSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

/**
 * 用户被踢出出过滤器-检查用户是否被踢出
 * 作者：LiZW <br/>
 * 创建时间：2016/11/14 23:02 <br/>
 */
public class KickOutSessionFilter extends AccessControlFilter {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(KickOutSessionFilter.class);

    /**
     * 当用户被踢出后，跳转的Url地址
     */
    private String kickOutUrl = "/kickOut.html";

    /**
     * 默认 后登陆系统的踢出之前登录系统的(先登录的被提出)
     */
    private boolean kickOutBefore = true;

    /**
     * 同一个用户最多同时登入几次(默认1次)
     */
    private int maxLoginCount = 1;

    /**
     * 验证用户是否被踢出
     *
     * @return 返回false 进入 {@link #onAccessDenied} 方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("请求ServletRequest不能转换成HttpServletRequest,用户踢出检查失败");
        }
        Subject subject = getSubject(request, response);
        // 判断用户是否已登录
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }
        Session session = subject.getSession();

        // 判断用户是否已经被设置踢出了
        if (ShiroSessionUtils.isKickOut(session)) {
            return false;
        }
        // 获取需要踢出登录的SessionId集合
        User user = ShiroSessionUtils.getUserBySession(session);
        if (user == null || user.getLoginName() == null) {
            logger.warn("Shiro Session中获取用户失败");
            return false;
        }
        Serializable sessionId = session.getId();
        List<Serializable> kickOutSessionIdList = KickOutCacheUtils.getKickOutSessionId(user.getLoginName(), sessionId, maxLoginCount, kickOutBefore);
        if (kickOutSessionIdList == null || kickOutSessionIdList.size() <= 0) {
            return true;
        }
        // 设置踢出标识
        for (Serializable sId : kickOutSessionIdList) {
            Session kickOutSession = ShiroSessionUtils.getSession(sId);
            if (kickOutSession != null) {
                ShiroSessionUtils.setkickOutFlag(kickOutSession);
            }
        }
        return !kickOutSessionIdList.contains(sessionId);
    }

    /**
     * 当用户被踢出后的操作-(强制用户退出登录)
     *
     * @return 返回true请求被继续处理，返回false请求结束直接返回
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpServletRequest.getSession();
        // 清除用户登入信息
        HttpSessionUtils.logout(httpSession);
        // 先退出 Shiro
        Subject subject = getSubject(request, response);
        subject.logout();
        //再重定向
        WebUtils.issueRedirect(request, response, kickOutUrl);
        return false;
    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getKickOutUrl() {
        return kickOutUrl;
    }

    public void setKickOutUrl(String kickOutUrl) {
        this.kickOutUrl = kickOutUrl;
    }

    public boolean isKickOutBefore() {
        return kickOutBefore;
    }

    public void setKickOutBefore(boolean kickOutBefore) {
        this.kickOutBefore = kickOutBefore;
    }

    public int getMaxLoginCount() {
        return maxLoginCount;
    }

    public void setMaxLoginCount(int maxLoginCount) {
        this.maxLoginCount = maxLoginCount;
    }
}
