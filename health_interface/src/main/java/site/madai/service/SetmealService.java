package site.madai.service;

import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.Setmeal;

import java.util.List;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-27 20:15
 * @Description:
 * @Version: 1.0
 */
public interface SetmealService {
    PageResult findByPage(QueryPageBean queryPageBean);

    void add(Setmeal setmeal, Integer[] checkGroupIds);

    Setmeal findById(Integer id);

    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    void delSetmealById(Integer id);

    List<String> findAllImg();

    List<Setmeal> getAllSetmeal();
}
