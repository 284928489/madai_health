package site.madai.service;

import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Permission;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-07 08:38
 * @Description:
 * @Version: 1.0
 */
public interface PermissionService {
    PageResult queryPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    Permission findById(Integer id);

    void edit(Permission permission);

    void delPermissionById(Integer id);
}
