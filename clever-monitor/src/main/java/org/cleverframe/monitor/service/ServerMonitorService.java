package org.cleverframe.monitor.service;

import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.ServerAttributeVo;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-20 12:07 <br/>
 */
@Component(MonitorBeanNames.ServerMonitorService)
public class ServerMonitorService extends BaseService {

    /**
     * 创建BeanInfoVo
     *
     * @param attributeName 属性名称
     * @param object        对象
     * @return 返回创建的BeanInfoVo
     */
    private ServerAttributeVo getServerAttributeVo(String attributeName, Object object) {
        ServerAttributeVo serverAttributeVo = new ServerAttributeVo();
        serverAttributeVo.setName(attributeName);
        if (object != null) {
            serverAttributeVo.setClazz(object.getClass().getCanonicalName());
             serverAttributeVo.setJsonValue(object.toString());
        }
        return serverAttributeVo;
    }

    /**
     * 获取Session属性范围值<br>
     */
    @SuppressWarnings("Duplicates")
    public List<ServerAttributeVo> getSessionAttribute(HttpServletRequest request, String attributeName) {
        HttpSession session = request.getSession();
        List<ServerAttributeVo> attributeList = new ArrayList<>();
        // 获取一个
        if (StringUtils.isNotBlank(attributeName)) {
            Object object = session.getAttribute(attributeName);
            if (object != null) {
                attributeList.add(this.getServerAttributeVo(attributeName, object));
            }
            return attributeList;
        }

        // 获取所有
        Enumeration<String> attributes = session.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String name = attributes.nextElement();
            Object object = session.getAttribute(name);
            attributeList.add(this.getServerAttributeVo(name, object));
        }
        return attributeList;
    }

    /**
     * 获取application属性范围值<br>
     */
    @SuppressWarnings("Duplicates")
    public List<ServerAttributeVo> getApplicationAttribute(HttpServletRequest request, String attributeName) {
        ServletContext context = request.getServletContext();
        List<ServerAttributeVo> attributeList = new ArrayList<>();
        // 获取一个
        if (StringUtils.isNotBlank(attributeName)) {
            Object object = context.getAttribute(attributeName);
            if (object != null) {
                attributeList.add(this.getServerAttributeVo(attributeName, object));
            }
            return attributeList;
        }

        // 获取所有
        Enumeration<String> attributes = context.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String name = attributes.nextElement();
            Object object = context.getAttribute(name);
            attributeList.add(this.getServerAttributeVo(name, object));
        }
        return attributeList;
    }

    /**
     * 移除Session中的属性<br>
     */
    public boolean removeSessionAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession();
        session.removeAttribute(name);
        return true;
    }

    /**
     * 移除Application中的属性<br>
     */
    public boolean removeApplicationAttribute(HttpServletRequest request, String name) {
        ServletContext context = request.getServletContext();
        context.removeAttribute(name);
        return true;
    }
}
