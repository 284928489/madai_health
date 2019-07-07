package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.MenuDao;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Menu;
import site.madai.service.MenuService;

import java.util.List;
import java.util.Map;

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

    @Override
    public PageResult queryPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 查询所有
        Page<Map<String,Object>> page =
                menuDao.findByCondition(queryPageBean.getQueryString());
        //3. 返回对象
        return new PageResult(page.getTotal(), page, queryPageBean.getCurrentPage());
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findAllParentmenu();
    }

    @Override
    public void add(Menu menu, Integer[] parentMenuIds) {
        if(menu.getLevel() == 2){
            menu.setParentMenuId(parentMenuIds[0]);
        }
        int count = menuDao.getMenuCountByLevelAndParentId(menu.getLevel(),
                menu.getParentMenuId());
        menu.setPriority(count+1);
        if(menu.getLevel() == 1){
            menu.setPath(menu.getPriority().toString());
        }else{
            Menu m = menuDao.getMenuById(menu.getParentMenuId());
            StringBuilder pathBuilder =
                    new StringBuilder("/"+m.getPath());
            menu.setPath(pathBuilder.append("-").append(menu.getPriority().toString()).toString());
        }
        menuDao.add(menu);
    }

    @Override
    public void edit(Menu menu, Integer[] parentMenuIds) throws Exception{
        int childcount = menuDao.getchildmenuCountBy(menu.getId());
        if (childcount > 0) {
            throw new RuntimeException("该菜单下含有子菜单，不能被编辑");
        }

        menuDao.delMenuAndRoleBymenuId(menu.getId());
        menuDao.delMenuById(menu.getId());
        if(menu.getLevel() == 2){
            menu.setParentMenuId(parentMenuIds[0]);
        }
        int count = menuDao.getMenuCountByLevelAndParentId(menu.getLevel(),
                menu.getParentMenuId());
        menu.setPriority(count+1);
        if(menu.getLevel() == 1){
            menu.setPath(menu.getPriority().toString());
        }else{
            Menu m = menuDao.getMenuById(menu.getParentMenuId());
            StringBuilder pathBuilder =
                    new StringBuilder("/"+m.getPath());
            menu.setPath(pathBuilder.append("-").append(menu.getPriority().toString()).toString());
        }
        menuDao.add(menu);
    }

    @Override
    public void delMenuById(Integer id) {
        int childcount = menuDao.getchildmenuCountBy(id);
        if (childcount > 0) {
            throw new RuntimeException("该菜单下含有子菜单，不能被删除");
        }

        menuDao.delMenuAndRoleBymenuId(id);
        menuDao.delMenuById(id);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuDao.getMenuById(id);
    }

    @Override
    public List<Integer> findparentMenuIdsById(Integer id) {
        return menuDao.findparentMenuIdsById(id);
    }
}
