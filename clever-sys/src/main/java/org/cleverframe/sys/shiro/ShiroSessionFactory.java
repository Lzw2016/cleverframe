package org.cleverframe.sys.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/16 11:17 <br/>
 * 参考 {@link org.apache.shiro.session.mgt.SimpleSessionFactory}
 */
public class ShiroSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        if (initData != null) {
            String host = initData.getHost();
            if (host != null) {
                return new ShiroSession(host);
            }
        }
        return new ShiroSession();
    }
}
