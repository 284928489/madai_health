package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.UserDao;
import site.madai.pojo.Menu;
import site.madai.pojo.Role;
import site.madai.pojo.User;
import site.madai.service.UserService;

import java.util.List;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:06
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    /**
     *  思路一： 1） 根据username查询用户信息
     *           2）  根据用户id查询相应的角色信息
     *           3） 根据角色id查询相应的权限信息
     *  思路二：
     *        根据username查询用户信息，使用mybatis的一对多查询,查询关联数据
     * @param username
     * @return
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public User findByUsername(String username) {

        return userDao.findByUsername(username);
    }
}
