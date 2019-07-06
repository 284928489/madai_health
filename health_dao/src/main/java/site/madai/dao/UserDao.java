package site.madai.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import site.madai.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:08
 * @Description:
 * @Version: 1.0
 */
public interface UserDao {

    List<Integer> findRoleIdsByUserId(Integer id);

    User findByUsername(@Param("username") String username);

    Page<User> findByCondition(String queryString);

    void add(User user);

    void setUserAndRole(Map<String, Integer> map);

    User findById(Integer id);

    void deleteUserAndRoleIdsByUserId(Integer id);

    void edit(User user);

    void delUserById(Integer id);
}
