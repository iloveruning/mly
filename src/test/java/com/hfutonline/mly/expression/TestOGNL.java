package com.hfutonline.mly.expression;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.junit.Test;

/**
 * @author chenliangliang
 * @date 2018/2/24
 */
public class TestOGNL {

    @Test
    public void test() throws OgnlException {

        User user = new User();
        user.setAge(22);
        user.setUsername("cll");

        OgnlContext context=new OgnlContext();
        context.setRoot(user);

        Object value = Ognl.getValue( Ognl.parseExpression("@java.lang.Math@PI"),context , context.getRoot());

        Object value1 = Ognl.getValue( Ognl.parseExpression("username"),context , context.getRoot());
        System.out.println(value);
        System.out.println(value1);

       // Object value2 = Ognl.getValue("@com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit@getUserId()", context, context.getRoot());
        //System.out.println(value2);

        context.put("user",user);
        Object value3 = Ognl.getValue("#user.username", context, context.getRoot());
        System.out.println(value3);
    }
}
