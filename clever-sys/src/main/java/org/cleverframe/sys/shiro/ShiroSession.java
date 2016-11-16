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

    /**
     * 最后修改时间，记录由于 {@link #CHANGE_INTERVAL} 值而设置isChanged=true的时间
     */
    private Date changeTime;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    private void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * Session最后访问时间超过 {@link #CHANGE_INTERVAL} 才设置 isChanged=true<br/>
     * 同时更新changeTime=new Date()<br/>
     * <b>注意：LastAccessTime 值更新之后再调用此方法</b>
     */
    private void updateChangeTime() {
        if (changeTime == null) {
            changeTime = new Date();
            setChanged(true);
            logger.debug("当前用户是第一次访问，SessionID={}", getId());
        } else {
            long now = getLastAccessTime().getTime();
            long last = changeTime.getTime();
            long interval = now - last;
            if (interval >= CHANGE_INTERVAL) {
                changeTime = new Date();
                setChanged(true);
            }
            logger.debug("当前用户两次访问时间间隔： {}ms， SessionID={}", interval, getId());
        }
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
        super.setLastAccessTime(lastAccessTime);
        updateChangeTime();
    }

    @Override
    public void touch() {
        super.touch();
        updateChangeTime();
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
