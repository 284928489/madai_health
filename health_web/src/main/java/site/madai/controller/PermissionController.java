package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.pojo.Permission;
import site.madai.service.PermissionService;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-07 08:36
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = permissionService.queryPage(queryPageBean);
        if (pageResult.getRows().size() == 0) {
            if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
                queryPageBean.setCurrentPage(1);
                pageResult = permissionService.queryPage(queryPageBean);
            }
        }
        return pageResult;
    }

    @RequestMapping("add")
    public Result add(@RequestBody Permission permission) {
        try {
            permissionService.add(permission);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    @RequestMapping("findById")
    public Result findById(Integer id) {
        Permission permission = permissionService.findById(id);
        if (permission != null) {
            Result result = new Result(true,
                    MessageConstant.QUERY_PERMISSION_SUCCESS);
            result.setData(permission);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
    }

    @RequestMapping("edit")
    public Result edit(@RequestBody Permission permission) {
        try {
            permissionService.edit(permission);
            return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }

    @RequestMapping("delPermissionById")
    public Result delPermissionById(Integer id) {
        try {
            permissionService.delPermissionById(id);
            return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }
}
