package site.madai.service;

import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Menu;
import site.madai.pojo.User;

import java.util.List;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:06
 * @Description:
 * @Version: 1.0
 */
public interface UserService {

    List<Integer> findRoleIdsByUserId(Integer id);

    User findByUsername(String username);

    PageResult queryPage(QueryPageBean queryPageBean);

    void add(User user, Integer[] roleIds);

    User findById(Integer id);

    void edit(User user, Integer[] roleIds);

    void delUserById(Integer id);
}
