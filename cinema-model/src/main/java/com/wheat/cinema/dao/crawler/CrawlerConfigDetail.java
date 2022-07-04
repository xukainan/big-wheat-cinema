package com.wheat.cinema.dao.crawler;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("crawler_config_detail")
public class CrawlerConfigDetail {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField("config_id")
    private int configId;
    @TableField("config_url")
    private String configUrl;
    @TableField("config_xpath")
    private String configXpath;
    @TableField("config_suffix")
    private String configSuffix;
    @TableField("config_attribute")
    private String configAttribute;
    @TableField("config_regex")
    private String configRegex;
    @TableField("config_time")
    private Date createTime;
    @TableField("config_time")
    private Date updateTime;
    @TableField("is_delete")
    private int isDelete;
    @TableField("config_type_id")
    private int configTypeId;
}
