package com.mmall.util;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * RedispoolUtil Tester.
 *
 * @author <Authors name>
 * @since <pre>ʮ���� 7, 2018</pre>
 * @version 1.0
 */
public class RedispoolUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: set(String key, String value)
     *
     */
    @Test
    public void testSet() throws Exception {
        String value = RedispoolUtil.set("keyTest", "value");
    }

    /**
     *
     * Method: get(String key)
     *
     */
    @Test
    public void testGet() throws Exception {
        String value = RedispoolUtil.get("keyTest");
        System.out.println(value);
    }

    /**
     *
     * Method: del(String key)
     *
     */
    @Test
    public void testDel() throws Exception {
        RedispoolUtil.del("keyTest");
    }

    /**
     *
     * Method: setEx(String key, String value, int exTime)
     *
     */
    @Test
    public void testSetEx() throws Exception {
        RedispoolUtil.setEx("keyTest", "value", 600);
    }

    /**
     *
     * Method: expire(String key, int exTime)
     *
     */
    @Test
    public void testExpire() throws Exception {
        RedispoolUtil.expire("keyTest", 600);
    }


}
