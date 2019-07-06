package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.MenuDao;
import site.madai.pojo.Menu;
import site.madai.service.MenuService;

import java.util.List;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 11:39
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<Menu> getMenuListByUsername(String username) {
        return menuDao.getMenusByUsername(username);
    }
}
