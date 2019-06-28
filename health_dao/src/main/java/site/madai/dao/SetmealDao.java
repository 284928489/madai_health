package site.madai.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import site.madai.pojo.CheckItem;
import site.madai.pojo.Setmeal;

import java.util.List;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-27 20:20
 * @Description:
 * @Version: 1.0
 */
public interface SetmealDao {
    Page<CheckItem> findByCondition(String queryString);

    void add(Setmeal setmeal);

    void setCheckGroupAndSetmeal(@Param("setmealId") Integer setmealId,
                                 @Param("checkGroupId") Integer checkGroupId);

    Setmeal findById(Integer id);

    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    void deleteAssociationFrom_t_setmeal_checkgroupBySetmealId(Integer id);

    void edit(Setmeal setmeal);

    long getSetmealidCountFrom_t_order(Integer id);

    long getSetmealidCountFrom_t_setmeal_checkgroup(Integer id);

    void deleteSetmealById(Integer id);
}
