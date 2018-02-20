package com.hfutonline.mly.modules.sys.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/1/8
 */
@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 5585139850213297768L;

    private Integer id;

    private String name;

    private String salt;

}
