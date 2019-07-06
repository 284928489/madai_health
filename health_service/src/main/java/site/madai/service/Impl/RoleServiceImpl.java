package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import site.madai.dao.RoleDao;
import site.madai.pojo.Role;
import site.madai.service.RoleService;

import java.util.List;

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
}
