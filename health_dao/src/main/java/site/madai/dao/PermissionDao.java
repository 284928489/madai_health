package site.madai.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import site.madai.pojo.Permission;

import java.util.List;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:21
 * @Description:
 * @Version: 1.0
 */
public interface PermissionDao {
    List<Permission> findPermissionListByRoleId(@Param("roleId") Integer roleId);

    Page<Permission> findByCondition(String queryString);

    void add(Permission permission);

    Permission findById(Integer id);

    void edit(Permission permission);

    long getRoleCountFrom_t_role_permissionByPermissionId(Integer id);

    void delPermissionById(Integer id);

    List<Permission> findAllPermission();
}
