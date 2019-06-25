package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.CheckGroupDao;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.CheckGroup;
import site.madai.pojo.CheckItem;
import site.madai.service.CheckGroupService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-25 13:14
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {


    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void delCheckgroupById(Integer id) {
        // 有外键先删除外键约束
        checkGroupDao.deleteAssociation(id);
        // 根据id删除检查组
        checkGroupDao.deleteCheckgroupById(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }
    //设置检查组合和检查项的关联关系
    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds){
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageResult queryPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //2. 查询所有
        Page<CheckItem> page  =
                checkGroupDao.findByCondition(queryPageBean.getQueryString());
        //3. 返回对象
        return new PageResult(page.getTotal(), page);
    }
}