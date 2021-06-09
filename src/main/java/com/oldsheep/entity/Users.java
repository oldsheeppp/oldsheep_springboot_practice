package com.oldsheep.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-05-23
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Users implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "ID", type = IdType.AUTO)
      @ApiModelProperty("用户编号")
      private Integer id;

    @TableField("Username")
    @ApiModelProperty("用户名")
    private String Username;

    @TableField("Password")
    @ApiModelProperty("密码")
    private String Password;


}
