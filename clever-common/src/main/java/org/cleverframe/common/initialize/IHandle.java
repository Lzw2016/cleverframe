package org.cleverframe.common.initialize;

import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 服务器初始化处理
 * 作者：LiZW <br/>
 * 创建时间：2016/11/12 19:47 <br/>
 */
public interface IHandle {

    /**
     * Spring容器初始化完毕时调用(Servlet容器初始化完毕调用)
     *
     * @param event Spring容器初始化完毕事件对象,可获取ApplicationContext、ServletContext等对象
     * @return 返回处理的数据数量 或 更新新增的数据数量
     */
    int initialize(ContextRefreshedEvent event);
}
