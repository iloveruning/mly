package com.hfutonline.mly.modules.api.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/10/17
 */
@Data
public class ApiQuery implements Serializable {
    private static final long serialVersionUID = 5486716965937300484L;
    @NotBlank(message = "token不能为空", groups = {ApiGroup.SetAuthToken.class,ApiGroup.SetCross.class})
    private String token;
    @NotBlank(message = "enable字段不能为空", groups = {ApiGroup.SetAuthToken.class,ApiGroup.SetCross.class})
    private String enable;

}
