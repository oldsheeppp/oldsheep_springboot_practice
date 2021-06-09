package com.oldsheep.response;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class RetResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;


    @JSONField(name = "Code")
    private int Code;
    @JSONField(name = "Msg")
    private String Msg;
    @JSONField(name = "Success")
    private boolean Success;
    @JSONField(name = "Data")
    private T Data;
}
