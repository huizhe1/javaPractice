package com.mmall.common;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import redis.clients.jedis.ShardedJedis;

/**
 * RedisShardedPool Tester.
 *
 * @author <Authors name>
 * @since <pre>ʮ���� 8, 2018</pre>
 * @version 1.0
 */
public class RedisShardedPoolTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: getJedis()
     *
     */
    @Test
    public void testGetJedis() throws Exception {
        //TODO: Test goes here...
    }

    /**
     *
     * Method: returnResource(ShardedJedis jedis)
     *
     */
    @Test
    public void testReturnResource() throws Exception {
        //TODO: Test goes here...
    }


    /**
     *
     * Method: initPool()
     *
     */
    @Test
    public void testInitPool() throws Exception {
        //TODO: Test goes here...
            /*
            try {
               Method method = RedisShardedPool.getClass().getMethod("initPool");
               method.setAccessible(true);
               method.invoke(<Object>, <Parameters>);
            } catch(NoSuchMethodException e) {
            } catch(IllegalAccessException e) {
            } catch(InvocationTargetException e) {
            }
            */
    }

    @Test
    public void testMain() throws Exception{
        ShardedJedis jedis =  RedisShardedPool.getJedis();
        for (int i = 0; i<10; i++) {
            jedis.set("key"+i, "value"+i);
        }
        RedisShardedPool.returnResource(jedis);
    }
}
