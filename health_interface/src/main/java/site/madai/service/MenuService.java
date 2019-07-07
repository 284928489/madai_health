package site.madai.service;

import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Menu;

import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 11:38
 * @Description:
 * @Version: 1.0
 */
public interface MenuService {

    List<Menu> getMenuListByUsername(String username);

    PageResult queryPage(QueryPageBean queryPageBean);

    List<Menu> findAll();

    void add(Menu menu, Integer[] parentMenuIds);

    Menu getMenuById(Integer id);

    List<Integer> findparentMenuIdsById(Integer id);

    void edit(Menu menu, Integer[] parentMenuIds) throws Exception;

    void delMenuById(Integer id);

    List<Map<String,Object>> findAllMenu();
}
