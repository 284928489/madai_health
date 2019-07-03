package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.OrderDao;
import site.madai.dao.OrderSettingListDao;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.service.OrderSettingListService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-03 10:45
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = OrderSettingListService.class)
@Transactional
public class OrderSettingListServiceImpl implements OrderSettingListService {

    @Autowired
    private OrderSettingListDao orderSettingListDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public void delOrderSettingById(Integer id) {
        orderDao.delOrderById(id);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageResult queryPage(QueryPageBean queryPageBean) {
        //1. 开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //2. 查询所有
        Map<String,Object> conditionMap = new HashMap<>();
        conditionMap.put("queryString",queryPageBean.getQueryString());
        if(queryPageBean.getDateRangeList() != null){
            if (queryPageBean.getDateRangeList().size() > 0){
                conditionMap.put("startDate",queryPageBean.getDateRangeList().get(0));
                if (queryPageBean.getDateRangeList().size() > 1){
                    conditionMap.put("endDate",queryPageBean.getDateRangeList().get(1));
                }
            }
        }
        Page<Map<String,Object>> page  =
                orderSettingListDao.findByCondition(conditionMap);
        //3. 返回对象
        return new PageResult(page.getTotal(), page,queryPageBean.getCurrentPage());
    }
}
