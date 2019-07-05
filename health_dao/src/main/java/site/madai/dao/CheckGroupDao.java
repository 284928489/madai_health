package site.madai.dao;

import com.github.pagehelper.Page;
import site.madai.pojo.CheckGroup;
import site.madai.pojo.CheckItem;
import site.madai.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-25 13:17
 * @Description:
 * @Version: 1.0
 */
public interface CheckGroupDao {

    /**
     * 分页条件查询方法
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 根据checkgroup添加
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void deleteAssociationFrom_T_checkgroup_checkitemByCheckgroupid(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteCheckgroupById(Integer id);

    long getCheckgroupidCountFrom_T_setmeal_checkgroup(Integer id);

    long getCheckgroupidCountFrom_T_checkgroup_checkitem(Integer id);

    List<Setmeal> findAll();
}
