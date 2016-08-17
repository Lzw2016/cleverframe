package org.cleverframe.quartz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：<br/>
 */

/**
 * 实体类，对应表qrtz_scheduler_log(Scheduler调度日志表)<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-8-3 17:52 <br/>
 */
@Entity
@Table(name = "qrtz_scheduler_log")
@DynamicInsert
@DynamicUpdate
public class QrtzSchedulerLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 监听器名称
     */
    private String listenerName;

    /**
     * Scheduler名称
     */
    private String schedName;

    /**
     * Scheduler实例的唯一标识，配置文件中的Instance Id
     */
    private String instanceName;

    /**
     * 触发事件调用的方法
     */
    private String methodName;

    /**
     * 触发事件记录的日志数据
     */
    @Lob
    @Column(columnDefinition = "MediumText")
    private String logData;

    /**
     * 触发节点IP,可能有多个(‘;’分隔)
     */
    private String ipAddress;

    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date logTime;
    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置 编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 监听器名称
     */
    public String getListenerName() {
        return listenerName;
    }

    /**
     * 设置 监听器名称
     */
    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

    /**
     * 获取 Scheduler名称
     */
    public String getSchedName() {
        return schedName;
    }

    /**
     * 设置 Scheduler名称
     */
    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    /**
     * 获取 Scheduler实例的唯一标识，配置文件中的Instance Id
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * 设置 Scheduler实例的唯一标识，配置文件中的Instance Id
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * 获取 触发事件调用的方法
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置 触发事件调用的方法
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 获取 触发事件记录的日志数据
     */
    public String getLogData() {
        return logData;
    }

    /**
     * 设置 触发事件记录的日志数据
     */
    public void setLogData(String logData) {
        this.logData = logData;
    }

    /**
     * 获取 触发节点IP,可能有多个(‘;’分隔)
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 设置 触发节点IP,可能有多个(‘;’分隔)
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}
