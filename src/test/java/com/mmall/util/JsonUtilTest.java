package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;

/**
 * JsonUtil Tester.
 *
 * @author <Authors name>
 * @since <pre>ʮ���� 7, 2018</pre>
 * @version 1.0
 */
public class JsonUtilTest {
    private User u1;

    @Before
    public void before() throws Exception {
        u1 = new User();
        u1.setId(1);
        u1.setEmail("a22783276@163.com");
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: obj2String(T obj)
     *
     */
    @Test
    public void testObj2String() throws Exception {
        String user1Json = JsonUtil.obj2String(u1);
        System.out.println(user1Json);
    }

    /**
     *
     * Method: obj2StringPretty(T obj)
     *
     */
    @Test
    public void testObj2StringPretty() throws Exception {
        String user1Json = JsonUtil.obj2StringPretty(u1);
        System.out.println(user1Json);
    }

    /**
     *
     * Method: string2Obj(String str, Class<T> clazz)
     *
     */
    @Test
    public void testString2Obj() throws Exception {
        String user1Json = JsonUtil.obj2StringPretty(u1);
        User user = JsonUtil.string2Obj(user1Json, User.class);
        System.out.println(u1);
        System.out.println(user);
    }

    @Test
    public void testListToString() throws Exception {
        List<User> list = Lists.newArrayList();
        list.add(u1);
        list.add(u1);
        list.add(u1);
        System.out.println(JsonUtil.obj2StringPretty(list));
    }

    @Test
    public void testStringToList() throws Exception {
        List<User> list = Lists.newArrayList();
        list.add(u1);
        list.add(u1);
        String value = JsonUtil.obj2StringPretty(list);
        List<User> list2 = JsonUtil.string2Obj(value, new TypeReference<List<User>>() {});
        list2.forEach(e-> System.out.println(e));
    }

    @Test
    public void testStringToList2() throws Exception {
        List<User> list = Lists.newArrayList();
        list.add(u1);
        list.add(u1);
        String value = JsonUtil.obj2StringPretty(list);
        List<User> list2 = JsonUtil.string2Obj(value, List.class, User.class);
        list2.forEach(e-> System.out.println(e));
    }
}
