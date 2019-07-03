package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.pojo.CheckGroup;
import site.madai.pojo.Setmeal;
import site.madai.service.CheckGroupService;

import java.util.List;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-25 13:10
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("findAll")
    public Result findAll() {
        List<Setmeal> checkGrouplist = checkGroupService.findAll();
        if (checkGrouplist != null && checkGrouplist.size() > 0)
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkGrouplist);
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    @RequestMapping("delCheckgroupById")
    public Result delCheckgroupById(Integer id) {
        try {
            checkGroupService.delCheckgroupById(id);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    //编辑
    @RequestMapping("edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.edit(checkGroup, checkitemIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    //根据检查组合id查询对应的所有检查项id
    @RequestMapping("findCheckItemIdsByCheckGroupId")
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupService.findCheckItemIdsByCheckGroupId(id);
    }

    @RequestMapping("findById")
    public Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupService.findById(id);
        if (checkGroup != null) {
            Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS);
            result.setData(checkGroup);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    @RequestMapping("add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkGroupService.queryPage(queryPageBean);
        if(pageResult.getRows().size() == 0){
            if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0){
                queryPageBean.setCurrentPage(1);
                pageResult = checkGroupService.queryPage(queryPageBean);
            }
        }
        return pageResult;
    }
}
