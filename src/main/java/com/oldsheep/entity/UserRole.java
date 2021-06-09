package com.oldsheep.entity;

import java.io.Serializable;
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
    public class UserRole implements Serializable {

    private static final long serialVersionUID=1L;

      private Integer id;

    private Integer userId;

    private Integer roleId;


}
