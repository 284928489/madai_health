package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.UserDao;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Menu;
import site.madai.pojo.Role;
import site.madai.pojo.User;
import site.madai.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private UserDao userDao;

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

    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageResult queryPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 查询所有
        Page<User> page =
                userDao.findByCondition(queryPageBean.getQueryString());
        //3. 返回对象
        return new PageResult(page.getTotal(), page, queryPageBean.getCurrentPage());
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public void add(User user, Integer[] roleIds) {
        userDao.add(user);
        setUserAndRole(user.getId(), roleIds);
    }

    private void setUserAndRole(Integer uid,Integer[] roleIds){
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("user_id", uid);
                map.put("role_id", roleId);
                userDao.setUserAndRole(map);
            }
        }
    }


    @Override
    public void edit(User user, Integer[] roleIds) {
        userDao.deleteUserAndRoleIdsByUserId(user.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setUserAndRole(user.getId(), roleIds);
        //更新检查组基本信息
        userDao.edit(user);
    }

    @Override
    public void delUserById(Integer id) {
        userDao.deleteUserAndRoleIdsByUserId(id);
        userDao.delUserById(id);
    }
}
