package site.madai.service;

import site.madai.pojo.Menu;

import java.util.List;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 11:38
 * @Description:
 * @Version: 1.0
 */
public interface MenuService {

    List<Menu> getMenuListByUsername(String username);
}
