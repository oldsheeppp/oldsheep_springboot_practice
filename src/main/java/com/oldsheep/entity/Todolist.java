package com.oldsheep.entity;

import java.time.LocalDate;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
    public class Todolist implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId("TodoId")
      @JSONField(name = "TodoId")
      @ApiModelProperty(value = "编号")
      private String TodoId;

    @TableField("Title")
    @JSONField(name = "Title")
    @ApiModelProperty(value = "标题")
    private String Title;

    @TableField("StartTime")
    @JSONField(name = "StartTime")
    @ApiModelProperty(value = "发起的时间")
    private LocalDate StartTime;

    @TableField("Done")
    @JSONField(name = "Done")
    @ApiModelProperty(value = "当前todo是否完成:0->不是，1->是")
    private Boolean Done;

    @TableField("Username")
    @JSONField(name = "Username")
    @ApiModelProperty(value = "创建的用户名")
    private String Username;


}
