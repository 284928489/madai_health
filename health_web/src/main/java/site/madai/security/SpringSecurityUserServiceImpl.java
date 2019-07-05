package site.madai.security;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import site.madai.pojo.Permission;
import site.madai.pojo.Role;
import site.madai.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Project: site.madai.security
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:00
 * @Description:
 * @Version: 1.0
 */
@Component
public class SpringSecurityUserServiceImpl implements UserDetailsService {

    @Reference
    UserService userService;


    /**
     * 根据用户名载入用户信息
     *   1. 根据用户名从数据库查询用户（SysUser， 包含角色信息，角色包含权限信息）信息
     *   2. 封装成用户（User）对象返回给安全框架
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 根据用户名从数据库查询用户（SysUser， 包含角色信息，角色包含权限信息）信息
        site.madai.pojo.User sysUser = userService.findByUsername(username);

        //2. 封装成用户（User）对象返回给安全框架
        if(sysUser != null){
            //创建权限列表
            Collection<GrantedAuthority> list = new ArrayList<>();
            //遍历每一个角色
            for (Role role : sysUser.getRoles()) {
                //遍历每一个角色中的权限
                for (Permission permission : role.getPermissions()) {
                    //创建权限
                    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getKeyword());
                    //添加到权限集合中
                    list.add(grantedAuthority);
                }
            }
            //创建框架能解析的User对象
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(sysUser.getUsername(),sysUser.getPassword(), list);

            return  user;
        }

        return null;
    }
}
