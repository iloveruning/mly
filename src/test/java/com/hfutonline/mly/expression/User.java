package com.hfutonline.mly.expression;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/2/24
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3626892074757553843L;

    private String username;

    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
