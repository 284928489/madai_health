package site.madai.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import site.madai.constant.RedisConstant;
import site.madai.service.SetmealService;
import site.madai.utils.QiniuUtils;

import java.util.List;
import java.util.Set;

/**
 * 清理图片的任务
 *
 * @author shaodi wang
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Component
public class ClearImgJob {

    @Reference
    private SetmealService setmealService;

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool jedisPool;

    public void init() {
        List<String> setmealList = setmealService.findAllImg();
        for (String s : setmealList) {
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, s);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, s);
        }
    }

    /**
     * 1. 获取垃圾图片的名称集合
     * 2. 删除七牛云上的垃圾图片
     * 3. 删除redis集合中的垃圾图片名称
     */
    public void clearImg() {
//             * 1. 获取垃圾图片的名称集合：获取redis集合的差值
        Set<String> imgSet = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String imgName : imgSet) {
//             * 2. 删除七牛云上的垃圾图片
            QiniuUtils.deleteFileFromQiniu(imgName);
//             * 3. 删除redis集合中的垃圾图片名称
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, imgName);
        }
    }
}
