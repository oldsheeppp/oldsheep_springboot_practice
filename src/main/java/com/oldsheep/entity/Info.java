package com.oldsheep.entity;

import java.time.LocalDate;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author oldsheep
 * @since 2021-04-22
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Info implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId("InfoId")
      @JSONField(name = "InfoId")
      @ApiModelProperty(value = "编号")
      private String InfoId;

    @TableField("Title")
    @JSONField(name = "Title")
    @ApiModelProperty(value = "标题")
    private String Title;

    @TableField("Content")
    @JSONField(name = "Content")
    @ApiModelProperty(value = "内容")
    private String Content;

    @TableField("StartTime")
    @JSONField(name = "StartTime")
    @ApiModelProperty(value = "发起时间")
    private LocalDate StartTime;

    @TableField("Username")
    @JSONField(name = "Username")
    @ApiModelProperty(value = "创建它的用户名")
    private String Username;


}
