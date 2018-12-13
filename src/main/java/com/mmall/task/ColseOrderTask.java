package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedpoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author huizhe
 * @date 2018/12/13
 * @time 21:53
 */
@Component
@Slf4j
public class ColseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    /**
     * Tomcat 执行shutdown的时候执行此直接的方法
     */
    @PreDestroy
    public void delLock(){
        RedisShardedpoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

    //@Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV1(){
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV2(){
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "2000"));
        Long setnxResult = RedisShardedpoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis()+lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            // 获取到锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV4(){
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "2000"));
        Long setnxResult = RedisShardedpoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis()+lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            // 获取到锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            // 未获取到锁,继续判断,判断时间戳,看是否可以重制并获取到锁
            String lockValueStr = RedisShardedpoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                String getSetValue = RedisShardedpoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis()+lockTimeout));
                // 再次用当前时间戳getset
                // 返回给定的key的旧值 -> 旧值判断，是否可以获取锁
                // 当key没有旧值时，即key不存在时，返回null -> 获取锁
                // z喝了我们set了一个新的value值，获取旧的值
                if (getSetValue == null || (getSetValue != null && StringUtils.equals(lockValueStr, getSetValue))) {
                    // 真正获取到锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                log.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("关闭订单定时任务结束");
    }

    private void closeOrder(String lockName) {
        RedisShardedpoolUtil.expire(lockName, Integer.parseInt(PropertiesUtil.getProperty("lock.timeout", "2000")));
        log.info("获取{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        iOrderService.closeOrder(hour);
        RedisShardedpoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
        log.info("===========================");

    }
}
