package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import site.madai.dao.RoleDao;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Role;
import site.madai.service.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 23:32
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public PageResult queryPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 查询所有
        Page<Role> page =
                roleDao.findByCondition(queryPageBean.getQueryString());
        //3. 返回对象
        return new PageResult(page.getTotal(), page, queryPageBean.getCurrentPage());
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

    @Override
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    @Override
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.delRoleAndPermissionsByroleId(role.getId());
        roleDao.delRoleAndMenusByroleId(role.getId());
        setRoleAndMenus(role.getId(),menuIds);
        setRoleAndPermissions(role.getId(),permissionIds);
        roleDao.updateRole(role);
    }

    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.add(role);
        setRoleAndPermissions(role.getId(), permissionIds);
        setRoleAndMenus(role.getId(), menuIds);
    }

    private void setRoleAndMenus(Integer id, Integer[] menuIds) {

        if (menuIds != null && menuIds.length > 0) {
            for (Integer menuId : menuIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("role_id", id);
                map.put("menu_id", menuId);
                roleDao.setRoleAndMenus(map);
            }
        }
    }

    private void setRoleAndPermissions(Integer id, Integer[] permissionIds) {

        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("role_id", id);
                map.put("permission_id", permissionId);
                roleDao.setRoleAndPermissions(map);
            }
        }
    }

    @Override
    public void delRoleByroleId(Integer id) {
        long count = roleDao.getUserAndRoleCountByroleId(id);
        if(count > 0){
            throw new RuntimeException("该角色正在被用户关联，不能删除");
        }
        roleDao.delRoleAndMenusByroleId(id);
        roleDao.delRoleAndPermissionsByroleId(id);
        roleDao.delRoleByroleId(id);
    }
}
