package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.pojo.CheckItem;
import site.madai.service.CheckItemService;

import java.util.List;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-24 21:36
 * @Description: 预约管理 > 检查项管理 (CRUD)
 * @Version: 1.0
 */
@RestController
@RequestMapping("checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        if(queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0)
            queryPageBean.setCurrentPage(1);
        return checkItemService.queryPage(queryPageBean);
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        CheckItem checkItem =  checkItemService.findById(id);
        return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    @RequestMapping("update")
    public Result update(@RequestBody CheckItem checkItem){
        try {
            checkItemService.update(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("delById")
    public Result delById(Integer id){
        try {
            checkItemService.delById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e){
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    //查询所有
    @RequestMapping("findAll")
    public Result findAll(){
        List<CheckItem> checkItemList = checkItemService.findAll();
        if(checkItemList != null && checkItemList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS);
            result.setData(checkItemList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}
