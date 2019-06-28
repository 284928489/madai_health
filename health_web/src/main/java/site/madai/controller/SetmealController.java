package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;
import site.madai.constant.MessageConstant;
import site.madai.constant.RedisConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.pojo.Setmeal;
import site.madai.service.SetmealService;
import site.madai.utils.QiniuUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-27 20:11
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool jedisPool;

    //编辑
    @RequestMapping("edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.edit(setmeal,checkgroupIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    @RequestMapping("add")
    public Result add(@RequestBody Setmeal setmeal,
                      @RequestParam("checkgroupIds") Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal, checkgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e) {
            //新增失败
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("upload")
    public Result upload(@RequestBody MultipartFile imgFile){
        String originalFilename = imgFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0)
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        //生成唯一的文件名称
        //uuid 的名称
        String uuid = UUID.randomUUID().toString().replace("-","");
        //获取原始文件名   03a36073-a140-4942-9b9b-712cecb144901.jpg
        //获取文件的后缀名
        String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //拼接成唯一的文件名
        String uniqueName = uuid + extendName;
        //进行上传
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes() ,uniqueName);
            //已经上传成功， 需要把图片名称存储在redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, uniqueName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, uniqueName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        Setmeal setmeal = setmealService.findById(id);
        if(setmeal != null){
            return new Result(true,
                    MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @RequestMapping("findCheckGroupIdsBySetmealId")
    public List<Integer> findCheckGroupIdsBySetmealId(Integer id){
        return setmealService.findCheckGroupIdsBySetmealId(id);
    }


    @RequestMapping("findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        if(queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0)
            queryPageBean.setCurrentPage(1);
        return setmealService.findByPage(queryPageBean);
    }

    @RequestMapping("delSetmealById")
    public Result delSetmealById(Integer id){
        try {
            setmealService.delSetmealById(id);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (RuntimeException e){
//            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }
}
