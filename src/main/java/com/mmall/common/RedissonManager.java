package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author huizhe
 * @date 2018/12/14
 * @time 15:53
 */
@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    public Redisson getRedisson() {
        return redisson;
    }

    private Redisson redisson = null;

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip", "127.0.0.1");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port","6379"));

    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip", "127.0.0.1");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port","6380"));

    @PostConstruct
    private void init() {
        try{
            config.useSingleServer().setAddress(new StringBuilder().append("redis://").append(redis1Ip).append(":").append(redis1Port).toString());
            redisson = (Redisson) Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("redisson int error",e);
        }
    }
}
