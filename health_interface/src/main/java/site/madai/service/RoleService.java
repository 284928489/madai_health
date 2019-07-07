package site.madai.service;

import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Role;

import java.util.List;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 23:31
 * @Description:
 * @Version: 1.0
 */
public interface RoleService {
    List<Role> findAll();

    PageResult queryPage(QueryPageBean queryPageBean);

    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    Role getRoleById(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    void edit(Role role, Integer[] permissionIds, Integer[] menuIds);

    void delRoleByroleId(Integer id);
}
