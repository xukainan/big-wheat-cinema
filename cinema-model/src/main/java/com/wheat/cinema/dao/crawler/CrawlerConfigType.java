package com.wheat.cinema.dao.crawler;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("crawler_config_type")
public class CrawlerConfigType {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    @TableField("type_name")
    private String typeName;
}
