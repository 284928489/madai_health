package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.pojo.Role;
import site.madai.service.RoleService;

import java.util.List;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 23:28
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("findAll")
    public Result findAll(){
        List<Role> roleList = roleService.findAll();
        if (roleList != null && roleList.size() > 0) {
            Result result = new Result(true,
                    MessageConstant.QUERY_ROLE_SUCCESS);
            result.setData(roleList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = roleService.queryPage(queryPageBean);
        if (pageResult.getRows().size() == 0) {
            if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
                queryPageBean.setCurrentPage(1);
                pageResult = roleService.queryPage(queryPageBean);
            }
        }
        return pageResult;
    }

    @RequestMapping("add")
    public Result add(@RequestBody Role role,Integer[] permissionIds,
                      Integer[] menuIds){
        try {
            roleService.add(role, permissionIds,menuIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            //新增失败
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    @RequestMapping("getRoleById")
    public Result getRoleById(Integer id){
        Role role = roleService.getRoleById(id);
        if (role != null) {
            Result result = new Result(true,
                    MessageConstant.QUERY_ROLE_SUCCESS);
            result.setData(role);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
    }

    @RequestMapping("findPermissionIdsByRoleId")
    public List<Integer> findPermissionIdsByRoleId(Integer id){
        return roleService.findPermissionIdsByRoleId(id);
    }

    @RequestMapping("findMenuIdsByRoleId")
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleService.findMenuIdsByRoleId(id);
    }

    @RequestMapping("edit")
    public Result edit(@RequestBody Role role,Integer[] permissionIds,
                       Integer[] menuIds){
        try {
            roleService.edit(role, permissionIds, menuIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
    }

    @RequestMapping("delRoleByroleId")
    public Result delRoleByroleId(Integer id){
        try {
            roleService.delRoleByroleId(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }
}
