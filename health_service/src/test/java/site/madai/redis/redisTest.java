package site.madai.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;
import site.madai.constant.RedisConstant;

import java.util.Set;

/**
 * @Project: site.madai.jedis
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-28 09:23
 * @Description:
 * @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-redis.xml"})
public class redisTest {

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool jedisPool;

    @Test
    public void test01(){
//        RedisConstant.SETMEAL_PIC_RESOURCES
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,
                "a");
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,
                "b");
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,
                "c");
//        RedisConstant.SETMEAL_PIC_DB_RESOURCES
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,
                "a");
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,
                "e");

        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        System.out.println(sdiff);
    }

    @Test
    public void delReidsKey(){
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_RESOURCES);
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
    }
    @Test
    public void delRedisKey1(){
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,
                "567d4eee7ed24b06adc5a7de31d5cad7.jpg");
    }
}
