package site.madai.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import site.madai.pojo.Role;

import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:18
 * @Description:
 * @Version: 1.0
 */
public interface RoleDao {
    List<Role> findRoleListByUserId(@Param("userId") Integer userId);

    List<Role> findAll();

    Page<Role> findByCondition(String queryString);

    void add(Role role);

    void setRoleAndPermissions(Map<String, Integer> map);

    void setRoleAndMenus(Map<String, Integer> map);

    Role getRoleById(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    void delRoleAndPermissionsByroleId(Integer id);

    void delRoleAndMenusByroleId(Integer id);

    void updateRole(Role role);

    long getUserAndRoleCountByroleId(Integer id);

    void delRoleByroleId(Integer id);
}
