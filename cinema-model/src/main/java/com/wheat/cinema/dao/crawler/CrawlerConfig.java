package com.wheat.cinema.dao.crawler;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("crawler_config")
public class CrawlerConfig {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField("crawler_name")
    private String crawlerName;
    @TableField("crawler_index_url")
    private String crawlerIndexUrl;
    @TableField("crawler_status")
    private int crawlerStatus;
}
