package site.madai.dao;

import org.apache.ibatis.annotations.Param;
import site.madai.pojo.Menu;

import java.util.List;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 10:25
 * @Description:
 * @Version: 1.0
 */
public interface MenuDao {
    List<Menu> getMenusByUsername(@Param("username") String username);
}
