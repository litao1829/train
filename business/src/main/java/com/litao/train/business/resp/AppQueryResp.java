package com.litao.train.business.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
        import java.util.Date;
        import com.fasterxml.jackson.annotation.JsonFormat;

public class AppQueryResp {

    /**
    * 主键
    */
        @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
    * 版本号
    */
    private String version;

    /**
    * 更新摘要
    */
    private String summary;

    /**
    * 下载地址
    */
    private String download;

    /**
    * 创建时间
    */
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
    * 修改时间
    */
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;


    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

    public String getVersion() {
    return version;
    }

    public void setVersion(String version) {
    this.version = version;
    }

    public String getSummary() {
    return summary;
    }

    public void setSummary(String summary) {
    this.summary = summary;
    }

    public String getDownload() {
    return download;
    }

    public void setDownload(String download) {
    this.download = download;
    }

    public Date getCreateTime() {
    return createTime;
    }

    public void setCreateTime(Date createTime) {
    this.createTime = createTime;
    }

    public Date getUpdateTime() {
    return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    }

@Override
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(getClass().getSimpleName());
sb.append(" [");
sb.append("Hash = ").append(hashCode());
    sb.append(", id=").append(id);
    sb.append(", version=").append(version);
    sb.append(", summary=").append(summary);
    sb.append(", download=").append(download);
    sb.append(", createTime=").append(createTime);
    sb.append(", updateTime=").append(updateTime);
sb.append("]");
return sb.toString();
}
}