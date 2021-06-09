package com.oldsheep.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author oldsheep
 * @since 2021-05-21
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

      private Integer id;

    @TableField("permName")
    private String permName;

    private String method;

    private String url;

    @TableField("permTag")
    private String permTag;


}
