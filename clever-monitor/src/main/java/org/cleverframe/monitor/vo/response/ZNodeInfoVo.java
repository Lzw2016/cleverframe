package org.cleverframe.monitor.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.cleverframe.common.vo.response.BaseResponseVo;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-27 16:43 <br/>
 */
public class ZNodeInfoVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 节点创建时的zxid
     */
    private long czxid;

    /**
     * 节点最新一次更新发生时的zxid
     */
    private long mzxid;

    /**
     * 节点创建时的时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date ctime;

    /**
     * 节点最新一次更新发生时的时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date mtime;

    /**
     * 节点数据的更新次数
     */
    private int version;

    /**
     * 其子节点的更新次数
     */
    private int cversion;

    /**
     * 节点ACL(授权信息)的更新次数(aclVersion)
     */
    private int aversion;

    /**
     * 如果该节点为ephemeral节点, ephemeralOwner值表示与该节点绑定的session id. <br/>
     * 如果该节点不是ephemeral节点, ephemeralOwner值为0. <br/>
     */
    private long ephemeralOwner;

    /**
     * 节点数据的字节数
     */
    private int dataLength;

    /**
     * 子节点个数
     */
    private int numChildren;

    /**
     * ?
     */
    private long pzxid;


    /**
     * 节点数据
     */
    private byte[] data;

    /**
     * 节点数据 字符串格式
     */
    private String dataStr;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public long getCzxid() {
        return czxid;
    }

    public void setCzxid(long czxid) {
        this.czxid = czxid;
    }

    public long getMzxid() {
        return mzxid;
    }

    public void setMzxid(long mzxid) {
        this.mzxid = mzxid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCversion() {
        return cversion;
    }

    public void setCversion(int cversion) {
        this.cversion = cversion;
    }

    public int getAversion() {
        return aversion;
    }

    public void setAversion(int aversion) {
        this.aversion = aversion;
    }

    public long getEphemeralOwner() {
        return ephemeralOwner;
    }

    public void setEphemeralOwner(long ephemeralOwner) {
        this.ephemeralOwner = ephemeralOwner;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public long getPzxid() {
        return pzxid;
    }

    public void setPzxid(long pzxid) {
        this.pzxid = pzxid;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }
}
