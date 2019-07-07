package site.madai.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import site.madai.pojo.Menu;

import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 10:25
 * @Description:
 * @Version: 1.0
 */
public interface MenuDao {
    List<Menu> getMenusByUsername(@Param("username") String username);

    Page<Map<String, Object>> findByCondition(String queryString);

    List<Menu> findAllParentmenu();

    void add(Menu menu);


    int getMenuCountByLevelAndParentId(@Param("level") Integer level,
                                       @Param("parentMenuId") Integer parentMenuId);

    Menu getMenuById(@Param("parentMenuId") Integer parentMenuId);

    List<Integer> findparentMenuIdsById(@Param("id") Integer id);

    void delMenuAndRoleBymenuId(@Param("menu_id") Integer menu_id);

    void delMenuById(Integer id);

    int getchildmenuCountBy(Integer id);
}
