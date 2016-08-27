package org.cleverframe.monitor.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis Info命令信息封装<br>
 *
 * @author LiZW
 * @version 2015年9月29日 下午9:04:56
 */
public class RedisMonitorInfoVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * Redis Info信息说明
     */
    private static final Map<String, String> INDO_DESCRIPTION = new HashMap<>();

    static {
        // # Server--------------------------------------------------------------
        INDO_DESCRIPTION.put("redis_version", "Redis 服务器版本");
        INDO_DESCRIPTION.put("redis_git_sha1", "Git SHA1");
        INDO_DESCRIPTION.put("redis_git_dirty", "Git dirty flag");
        INDO_DESCRIPTION.put("redis_build_id", "");
        INDO_DESCRIPTION.put("redis_mode", "");
        INDO_DESCRIPTION.put("os", "Redis 服务器的宿主操作系统");
        INDO_DESCRIPTION.put("arch_bits", "架构（32 或 64 位）");
        INDO_DESCRIPTION.put("multiplexing_api", "Redis 所使用的事件处理机制");
        INDO_DESCRIPTION.put("gcc_version", "编译 Redis 时所使用的 GCC 版本");
        INDO_DESCRIPTION.put("process_id", "服务器进程的 PID");
        INDO_DESCRIPTION.put("run_id", "Redis 服务器的随机标识符（用于 Sentinel 和集群）");
        INDO_DESCRIPTION.put("tcp_port", "TCP/IP 监听端口");
        INDO_DESCRIPTION.put("uptime_in_seconds", "自 Redis 服务器启动以来，经过的秒数");
        INDO_DESCRIPTION.put("uptime_in_days", "自 Redis 服务器启动以来，经过的天数");
        INDO_DESCRIPTION.put("lru_clock", "以分钟为单位进行自增的时钟，用于 LRU 管理");
        INDO_DESCRIPTION.put("config_file", "");

        // # Clients--------------------------------------------------------------
        INDO_DESCRIPTION.put("connected_clients", "");
        INDO_DESCRIPTION.put("client_longest_output_list", "当前连接的客户端当中，最长的输出列表");
        INDO_DESCRIPTION.put("client_longest_input_buf", "当前连接的客户端当中，最大输入缓存");
        INDO_DESCRIPTION.put("blocked_clients", "正在等待阻塞命令（BLPOP、BRPOP、BRPOPLPUSH）的客户端的数量");

        // # Memory--------------------------------------------------------------
        INDO_DESCRIPTION.put("used_memory", "由 Redis 分配器分配的内存总量，以字节（byte）为单位");
        INDO_DESCRIPTION.put("used_memory_human", "以人类可读的格式返回 Redis 分配的内存总量");
        INDO_DESCRIPTION.put("used_memory_rss", "从操作系统的角度，返回 Redis 已分配的内存总量（俗称常驻集大小）。这个值和 top 、 ps 等命令的输出一致");
        INDO_DESCRIPTION.put("used_memory_peak", "Redis 的内存消耗峰值（以字节为单位）");
        INDO_DESCRIPTION.put("used_memory_peak_human", "以人类可读的格式返回 Redis 的内存消耗峰值");
        INDO_DESCRIPTION.put("used_memory_lua", "Lua 引擎所使用的内存大小（以字节为单位）");
        INDO_DESCRIPTION.put("mem_fragmentation_ratio", "used_memory_rss 和 used_memory 之间的比率");
        INDO_DESCRIPTION.put("mem_allocator", "在编译时指定的， Redis 所使用的内存分配器。可以是 libc 、 jemalloc 或者 tcmalloc");

        // # Persistence--------------------------------------------------------------
        INDO_DESCRIPTION.put("loading", "");
        INDO_DESCRIPTION.put("rdb_changes_since_last_save", "自上次dump后rdb的改动");
        INDO_DESCRIPTION.put("rdb_bgsave_in_progress", "标识rdb save是否进行中");
        INDO_DESCRIPTION.put("rdb_last_save_time", "上次save的时间戳");
        INDO_DESCRIPTION.put("rdb_last_bgsave_status", "上次的save操作状态");
        INDO_DESCRIPTION.put("rdb_last_bgsave_time_sec", "上次rdb save操作使用的时间(单位s)");
        INDO_DESCRIPTION.put("rdb_current_bgsave_time_sec", "如果rdb save操作正在进行，则是所使用的时间");

        INDO_DESCRIPTION.put("aof_enabled", "是否开启aof，默认没开启");
        INDO_DESCRIPTION.put("aof_rewrite_in_progress", "标识aof的rewrite操作是否在进行中");
        INDO_DESCRIPTION.put("aof_rewrite_scheduled", "标识是否将要在rdb save操作结束后执行");
        INDO_DESCRIPTION.put("aof_last_rewrite_time_sec", "上次rewrite操作使用的时间(单位s)");
        INDO_DESCRIPTION.put("aof_current_rewrite_time_sec", "如果rewrite操作正在进行，则记录所使用的时间");
        INDO_DESCRIPTION.put("aof_last_bgrewrite_status", "上次rewrite操作的状态");
        INDO_DESCRIPTION.put("aof_last_write_status", "标识aof最后写的状态");

        INDO_DESCRIPTION.put("aof_current_size", "aof上次启动或rewrite的大小");
        INDO_DESCRIPTION.put("aof_base_size", "同上面的aof_current_size");
        INDO_DESCRIPTION.put("aof_pending_rewrite", "");
        INDO_DESCRIPTION.put("aof_buffer_length", "aof buffer的大小");
        INDO_DESCRIPTION.put("aof_rewrite_buffer_length", "aof rewrite buffer的大小");
        INDO_DESCRIPTION.put("aof_pending_bio_fsync", "后台IO队列中等待fsync任务的个数");
        INDO_DESCRIPTION.put("aof_delayed_fsync", "延迟的fsync计数器");

        // # Stats--------------------------------------------------------------
        INDO_DESCRIPTION.put("total_connections_received", "自启动起连接过的总数");
        INDO_DESCRIPTION.put("total_commands_processed", "自启动起运行命令的总数");
        INDO_DESCRIPTION.put("instantaneous_ops_per_sec", "每秒执行的命令个数");
        INDO_DESCRIPTION.put("total_net_input_bytes", "");
        INDO_DESCRIPTION.put("total_net_output_bytes", "");
        INDO_DESCRIPTION.put("instantaneous_input_kbps", "");
        INDO_DESCRIPTION.put("instantaneous_output_kbps", "");
        INDO_DESCRIPTION.put("rejected_connections", "因为最大客户端连接书限制，而导致被拒绝连接的个数");
        INDO_DESCRIPTION.put("sync_full", "");
        INDO_DESCRIPTION.put("sync_partial_ok", "");
        INDO_DESCRIPTION.put("sync_partial_err", "");
        INDO_DESCRIPTION.put("expired_keys", "自启动起过期的key的总数");
        INDO_DESCRIPTION.put("evicted_keys", "因为内存大小限制，而被驱逐出去的键的个数");
        INDO_DESCRIPTION.put("keyspace_hits", "在main dictionary(todo)中成功查到的key个数");
        INDO_DESCRIPTION.put("keyspace_misses", "在main dictionary(todo)中未查到的key的个数");
        INDO_DESCRIPTION.put("pubsub_channels", "发布/订阅频道数");
        INDO_DESCRIPTION.put("pubsub_patterns", "发布/订阅模式数");
        INDO_DESCRIPTION.put("latest_fork_usec", "上次的fork操作使用的时间（单位ms）");

        // # Replication--------------------------------------------------------------
        INDO_DESCRIPTION.put("role", "角色");
        INDO_DESCRIPTION.put("connected_slaves", "连接的从库数");
        INDO_DESCRIPTION.put("master_repl_offset", "");
        INDO_DESCRIPTION.put("repl_backlog_active", "");
        INDO_DESCRIPTION.put("repl_backlog_size", "");
        INDO_DESCRIPTION.put("repl_backlog_first_byte_offset", "");
        INDO_DESCRIPTION.put("repl_backlog_histlen", "");

        INDO_DESCRIPTION.put("master_host", "");
        INDO_DESCRIPTION.put("master_port", "");
        INDO_DESCRIPTION.put("master_link_status", "");
        INDO_DESCRIPTION.put("master_last_io_seconds_ago", "");
        INDO_DESCRIPTION.put("master_sync_in_progress", "标识主redis正在同步到从redis");
        INDO_DESCRIPTION.put("slave_priority", "");
        INDO_DESCRIPTION.put("slave_read_only", "");
        INDO_DESCRIPTION.put("connected_slaves", "");

        // # CPU--------------------------------------------------------------
        INDO_DESCRIPTION.put("used_cpu_sys", "Redis Server的sys消耗CPU");
        INDO_DESCRIPTION.put("used_cpu_user", "Redis Server的user消耗CPU");
        INDO_DESCRIPTION.put("used_cpu_sys_children", "Redis Server后台进程的sys消耗CPU");
        INDO_DESCRIPTION.put("used_cpu_user_children", "Redis Server后台进程的user消耗CPU");

        // # Keyspace--------------------------------------------------------------
    }

    /**
     * Info字段key
     */
    private String key;
    /**
     * Info字段value
     */
    private String value;
    /**
     * Info字段说明
     */
    private String note;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        // 为note赋值
        this.note = INDO_DESCRIPTION.get(key);
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
