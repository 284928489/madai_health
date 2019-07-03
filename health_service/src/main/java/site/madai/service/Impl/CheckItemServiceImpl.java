package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.CheckItemDao;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.CheckItem;
import site.madai.service.CheckItemService;

import java.util.List;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-24 21:44
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageResult queryPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //2. 查询所有
        Page<CheckItem> page  = checkItemDao.findByCondition(queryPageBean.getQueryString());
        //3. 返回对象
        return new PageResult(page.getTotal(), page,queryPageBean.getCurrentPage());
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public void delById(Integer id) {
        long count =
                checkItemDao.getCheckItemIdCountFrom_t_checkgroup_checkitemByCheckitemId(id);
        if(count > 0)
            throw new RuntimeException("该检查项被检查组关联，不能被删除");
        checkItemDao.delById(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
