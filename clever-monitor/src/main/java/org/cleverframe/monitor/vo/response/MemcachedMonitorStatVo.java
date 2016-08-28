package org.cleverframe.monitor.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

import java.util.HashMap;
import java.util.Map;

/**
 * Memcached Stat信息封装<be>
 *
 * @author LiZW
 * @version 2015年10月6日 下午2:45:56
 */
public class MemcachedMonitorStatVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;
    /**
     * Memcached Stat信息说明
     */
    private static final Map<String, String> INDO_DESCRIPTION = new HashMap<>();

    static {
        INDO_DESCRIPTION.put("pid", "memcache服务器进程ID");
        INDO_DESCRIPTION.put("uptime", "服务器已运行秒数");
        INDO_DESCRIPTION.put("time", "服务器当前Unix时间戳");
        INDO_DESCRIPTION.put("version", "memcache版本");
        INDO_DESCRIPTION.put("pointer_size", "操作系统指针大小");
        INDO_DESCRIPTION.put("rusage_user", "进程累计用户时间");
        INDO_DESCRIPTION.put("rusage_system", "进程累计系统时间");
        INDO_DESCRIPTION.put("curr_connections", "当前连接数量");
        INDO_DESCRIPTION.put("total_connections", "Memcached运行以来连接总数");
        INDO_DESCRIPTION.put("connection_structures", "Memcached分配的连接结构数量");
        INDO_DESCRIPTION.put("cmd_get", "get命令请求次数");
        INDO_DESCRIPTION.put("cmd_set", "set命令请求次数");
        INDO_DESCRIPTION.put("cmd_flush", "flush命令请求次数");
        INDO_DESCRIPTION.put("get_hits", "get命令命中次数");
        INDO_DESCRIPTION.put("get_misses", "get命令未命中次数");
        INDO_DESCRIPTION.put("delete_misses", "delete命令未命中次数");
        INDO_DESCRIPTION.put("delete_hits", "delete命令命中次数");
        INDO_DESCRIPTION.put("incr_misses", "incr命令未命中次数");
        INDO_DESCRIPTION.put("incr_hits", "incr命令命中次数");
        INDO_DESCRIPTION.put("decr_misses", "decr命令未命中次数");
        INDO_DESCRIPTION.put("decr_hits", "decr命令命中次数");
        INDO_DESCRIPTION.put("cas_misses", "cas命令未命中次数");
        INDO_DESCRIPTION.put("cas_hits", "cas命令命中次数");
        INDO_DESCRIPTION.put("cas_badval", "使用擦拭次数");
        INDO_DESCRIPTION.put("auth_cmds", "认证命令处理的次数");
        INDO_DESCRIPTION.put("auth_errors", "认证失败数目");
        INDO_DESCRIPTION.put("bytes_read", "读取总字节数");
        INDO_DESCRIPTION.put("bytes_written", "发送总字节数");
        INDO_DESCRIPTION.put("limit_maxbytes", "分配的内存总大小（字节）");
        INDO_DESCRIPTION.put("accepting_conns", "服务器是否达到过最大连接（0/1）");
        INDO_DESCRIPTION.put("listen_disabled_num", "失效的监听数");
        INDO_DESCRIPTION.put("threads", "当前线程数");
        INDO_DESCRIPTION.put("conn_yields", "连接操作主动放弃数目");
        INDO_DESCRIPTION.put("bytes", "当前存储占用的字节数");
        INDO_DESCRIPTION.put("curr_items", "当前存储的数据总数");
        INDO_DESCRIPTION.put("total_items", "启动以来存储的数据总数");
        INDO_DESCRIPTION.put("evictions", "LRU释放的对象数目");
        INDO_DESCRIPTION.put("reclaimed", "已过期的数据条目来存储新数据的数目");
    }

    /**
     * Stat字段key
     */
    private String key;
    /**
     * Stat字段value
     */
    private String value;
    /**
     * Stat字段说明
     */
    private String note;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        String note = INDO_DESCRIPTION.get(key);
        this.setNote(note);
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
