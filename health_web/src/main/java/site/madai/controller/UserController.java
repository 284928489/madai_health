package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.pojo.Menu;
import site.madai.service.MenuService;
import site.madai.service.UserService;

import java.util.List;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 23:17
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {


    @Reference
    private UserService userService;

    @Reference
    private MenuService menuService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 获取用户名流程
     *     思路一：
     *          1） 获取session对象
     *          2） 获取session中所有的属性名 session.getAttributeNames()
     *                  认证信息属性名： SPRING_SECURITY_CONTEXT
     *          3) 根据SPRING_SECURITY_CONTEXT 属性名获取上下文对象
     *          4）从上下文对象中获取认证信息
     *     思路二:
     *          1） 使用工具类直接获取上下文对象
     *          2） 从上下文对象中获取认证信息
     * @return
     */
    @RequestMapping("findUsername")
    public Result findUsername(){
        try {
//        1） 使用工具类直接获取上下文对象
            SecurityContext securityContext = SecurityContextHolder.getContext();
//        2） 从上下文对象中获取认证信息
            //获取认证对象
            Authentication authentication = securityContext.getAuthentication();
            //获取principal === User
            Object principal = authentication.getPrincipal();
            //强制转换为user类型
            User user = (User) principal;
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("getUserMenus")
    public Result getUserMenus(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();

        List<Menu> menuList = menuService.getMenuListByUsername(username);
        return new Result(true,"asdf",menuList);

    }

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = userService.queryPage(queryPageBean);
        if (pageResult.getRows().size() == 0) {
            if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
                queryPageBean.setCurrentPage(1);
                pageResult = userService.queryPage(queryPageBean);
            }
        }
        return pageResult;
    }

    @RequestMapping("add")
    public Result add(@RequestBody site.madai.pojo.User user,
                      Integer[] roleIds) {
        try {
            // 将前端传来的明文密码通过security提供的加密工具类加密一下
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.add(user, roleIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
        //新增成功
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }

    @RequestMapping("findById")
    public Result findById(Integer id){
        site.madai.pojo.User user = userService.findById(id);
        if (user != null) {
            Result result = new Result(true,
                    MessageConstant.QUERY_USER_SUCCESS);
            result.setData(user);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }

    @RequestMapping("findRoleIdsByUserId")
    public List<Integer> findRoleIdsByUserId(Integer id){
        return userService.findRoleIdsByUserId(id);
    }

    @RequestMapping("edit")
    public Result edit(@RequestBody site.madai.pojo.User user,
                       Integer[] roleIds){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.edit(user, roleIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
    }

    @RequestMapping("delUserById")
    public Result delUserById(Integer id){
        try {
            userService.delUserById(id);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }
}
