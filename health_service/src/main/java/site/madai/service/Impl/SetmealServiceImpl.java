package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import site.madai.constant.RedisConstant;
import site.madai.dao.SetmealDao;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.CheckItem;
import site.madai.pojo.Setmeal;
import site.madai.service.SetmealService;

import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-27 20:16
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    @Qualifier("jedisPool")
    private JedisPool jedisPool;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> findAllImg() {
        return setmealDao.findAllImg();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Setmeal findSetmealByIdformobile(Integer id) {
        return setmealDao.findSetmealByIdformobile(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Setmeal> getAllSetmeal() {
        return setmealDao.getAllSetmeal();
    }

    @Override
    public void delSetmealById(Integer id) {
        long count =
                setmealDao.getSetmealidCountFrom_t_order(id);
        if (count > 0)
            throw new RuntimeException("该套餐正在被订单使用，不能删除");
        count = setmealDao.getSetmealidCountFrom_t_setmeal_checkgroup(id);
        if (count > 0)
            // 有外键先删除外键约束
            setmealDao.deleteAssociationFrom_t_setmeal_checkgroupBySetmealId(id);
        // 根据id删除检查组
        Setmeal setmeal = setmealDao.findById(id);
        setmealDao.deleteSetmealById(id);
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,
                setmeal.getImg());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2. 查询所有
        Page<CheckItem> page =
                setmealDao.findByCondition(queryPageBean.getQueryString());
        //3. 返回对象
        return new PageResult(page.getTotal(), page, queryPageBean.getCurrentPage());
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //根据检查组id删除中间表数据（清理原有关联关系）
        setmealDao.deleteAssociationFrom_t_setmeal_checkgroupBySetmealId(setmeal.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndSetmeal(setmeal.getId(), checkgroupIds);
        //更新检查组基本信息
        setmealDao.edit(setmeal);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Integer> findCheckGroupIdsBySetmealId(Integer id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.add(setmeal);
        setCheckGroupAndSetmeal(setmeal.getId(), checkGroupIds);
        //新增成功
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    private void setCheckGroupAndSetmeal(Integer setmealId,
                                         Integer[] checkGroupIds) {
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            for (Integer checkGroupId : checkGroupIds) {
                setmealDao.setCheckGroupAndSetmeal(setmealId, checkGroupId);
            }
        }
    }
}
