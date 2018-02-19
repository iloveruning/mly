package com.hfutonline.mly.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author chenliangliang
 * @date 2018/1/12
 */
public class IPUtil {

    /**
     * txt|jsonp|xml
     */
    private static final String DATATYPE = "jsonp";

    /**
     * token = "00d5cb1fac5dc5cbfe2ff218292a2dfd33"
     */
    private static final String TOKEN = "859476648b3de65d76804906dd1a1c6a";

    private static String get(String urlString, String token) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("token", token);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                StringBuilder builder = new StringBuilder();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"));
                for (String s = br.readLine(); s != null; s = br
                        .readLine()) {
                    builder.append(s);
                }
                br.close();
                return builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * {
     * "ret": "ok",    // ret 值为 ok 时 返回 data 数据 为err时返回msg数据
     * "ip": "117.25.13.123",  // ip
     * "data": [
     * "中国",     // 国家
     * "福建",     // 省会或直辖市
     * "福州",     // 地区或城市
     * "电信",     // 运营商
     * "361000",  // 邮政编码(暂不提供)
     * "0592"     // 地区区号(暂不提供)
     * ]
     * }
     */
    public static String queryIP(String ip) {
        String url = "http://api.ip138.com/query/?ip=" + ip + "&datatype=" + DATATYPE;
        String res = get(url, TOKEN);
        JSONObject obj = JSONObject.parseObject(res);

        String ret = obj.getString("ret");
        if (StringUtils.equals(ret, "ok")) {
            JSONArray data = obj.getJSONArray("data");
            return data.getString(0) + data.getString(1) + data.getString(2);
        }
        return null;
    }


    /**
     * 获取访问者IP
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null) {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        ip =  request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


}
