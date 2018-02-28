package com.hfutonline.mly.common.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author chenliangliang
 * @date 2018/2/27
 */
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = -3467384032017429237L;

    private String header;

    private String payload;

    private String signature;

    public Token(String jwtToken) {
        if (StringUtils.isNotBlank(jwtToken)) {
            String[] strs = StringUtils.split(jwtToken, ".");
            this.header = strs[0];
            this.payload = strs[1];
            this.signature = strs[2];
        }
    }

    public Token(String header, String payload, String signature) {
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    public String toJwt() {
        return header + "." + payload + "." + signature;
    }
}
