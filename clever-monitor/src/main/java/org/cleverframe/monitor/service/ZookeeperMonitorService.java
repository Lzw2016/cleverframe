package org.cleverframe.monitor.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.ZNodeInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 16:06 <br/>
 */
@Component(MonitorBeanNames.ZookeeperMonitorService)
public class ZookeeperMonitorService extends BaseService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(ZookeeperMonitorService.class);

    private CuratorFramework curatorFramework;

    public CuratorFramework getCuratorFramework() {
        if (curatorFramework == null) {
            curatorFramework = SpringContextHolder.getBean(SpringBeanNames.CuratorFramework);
        }
        return curatorFramework;
    }

    /**
     * 获取子节点
     *
     * @return 失败返回null
     */
    public List<String> getChildren(String path) {
        CuratorFramework client = this.getCuratorFramework();
        List<String> childrenList = null;
        try {
            childrenList = client.getChildren().forPath(path);
        } catch (Throwable e) {
            logger.error("获取子节点失败", e);
        }

        return childrenList;
    }

    /**
     * 获取节点数据
     *
     * @param path 节点路径
     * @return 失败返回null
     */
    public ZNodeInfoVo getZNodeInfo(String path) {
        ZNodeInfoVo zNodeInfoVo = null;
        CuratorFramework client = this.getCuratorFramework();
        try {
            Stat stat = new Stat();
            byte[] data = client.getData().storingStatIn(stat).forPath(path);
            zNodeInfoVo = new ZNodeInfoVo();
            zNodeInfoVo.setCzxid(stat.getCzxid());
            zNodeInfoVo.setMzxid(stat.getMzxid());
            zNodeInfoVo.setCtime(new Date(stat.getCtime()));
            zNodeInfoVo.setMtime(new Date(stat.getMtime()));
            zNodeInfoVo.setVersion(stat.getVersion());
            zNodeInfoVo.setCversion(stat.getCversion());
            zNodeInfoVo.setAversion(stat.getAversion());
            zNodeInfoVo.setEphemeralOwner(stat.getEphemeralOwner());
            zNodeInfoVo.setDataLength(stat.getDataLength());
            zNodeInfoVo.setNumChildren(stat.getNumChildren());
            zNodeInfoVo.setPzxid(stat.getPzxid());
            zNodeInfoVo.setData(data);
            if (data != null) {
                zNodeInfoVo.setDataStr(new String(data, Charset.forName("UTF-8")));
            }

            // List<ACL> aclList = client.getACL().forPath(path);
            // for (ACL acl : aclList){
            //     logger.info(acl.toString());
            // }
        } catch (Throwable e) {
            logger.error("获取节点数据失败", e);
        }
        return zNodeInfoVo;
    }
}
