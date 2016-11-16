package org.cleverframe.sys.shiro;

import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 由于SimpleSession lastAccessTime更改后也会调用SessionDao update方法<br/>
 * 增加标识位，如果只是更新lastAccessTime SessionDao update方法直接返回<br/>
 * <b>目的：为了减少Session的更新存储操作</b>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/16 10:47 <br/>
 */
public class ShiroSession extends SimpleSession implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ShiroSession.class);

    /**
     * LastAccessTime 更新间隔超过这个时间就设置isChanged = true
     */
    public static final long CHANGE_INTERVAL = 1000 * 10;

    public ShiroSession() {
        super();
        setChanged(false);
    }

    public ShiroSession(String host) {
        super(host);
        setChanged(false);
    }

    /**
     * 除lastAccessTime以外其他字段发生改变时为true
     */
    private boolean isChanged = false;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    /*--------------------------------------------------------------
     *          isChanged = true
     * -------------------------------------------------------------*/

    @Override
    public void setId(Serializable id) {
        super.setId(id);
//        if()
        setChanged(true);
    }

    @Override
    public void setStopTimestamp(Date stopTimestamp) {
        super.setStopTimestamp(stopTimestamp);
        setChanged(true);
    }

    @Override
    public void setExpired(boolean expired) {
        super.setExpired(expired);
    }

    @Override
    public void setTimeout(long timeout) {
        super.setTimeout(timeout);
        setChanged(true);
    }

    @Override
    public void setHost(String host) {
        super.setHost(host);
        setChanged(true);
    }

    @Override
    public void setAttributes(Map<Object, Object> attributes) {
        super.setAttributes(attributes);
        setChanged(true);
    }

    @Override
    public void setLastAccessTime(Date lastAccessTime) {
        if (getLastAccessTime() != null) {
            long last = getLastAccessTime().getTime();
            long now = lastAccessTime.getTime();
            //如果10s内访问 则不更新session,否则需要更新远端过期时间
            if ((last - now) >= CHANGE_INTERVAL) {
                setChanged(true);
            }
        }
        super.setLastAccessTime(lastAccessTime);
    }

    @Override
    public void touch() {
        Date preLastAccessTime = getLastAccessTime();
        super.touch();
        if (preLastAccessTime != null) {
            long now = getLastAccessTime().getTime();
            long pre = preLastAccessTime.getTime();
            long interval = now - pre;
            //如果10s内访问 则不更新session,否则需要更新远端过期时间
            if (interval >= CHANGE_INTERVAL) {
                setChanged(true);
            }
            logger.debug("当前用户两次访问时间间隔： {}ms", interval);
        }
    }

    @Override
    public void setAttribute(Object key, Object value) {
        super.setAttribute(key, value);
        setChanged(true);
    }

    @Override
    public Object removeAttribute(Object key) {
        setChanged(true);
        return super.removeAttribute(key);
    }


    @Override
    public void setStartTimestamp(Date startTimestamp) {
        super.setStartTimestamp(startTimestamp);
        setChanged(true);
    }

    @Override
    public Map<Object, Object> getAttributes() {
        // setChanged(true);
        return super.getAttributes();
    }

    @Override
    public void stop() {
        super.stop();
        setChanged(true);
    }

    @Override
    protected void expire() {
        super.expire();
        setChanged(true);
    }
}
