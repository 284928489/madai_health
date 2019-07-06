package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
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
}
