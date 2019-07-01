package site.madai.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.madai.dao.OrderSettingDao;
import site.madai.pojo.OrderSetting;
import site.madai.service.OrderSettingService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Project: site.madai.service.Impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-29 20:06
 * @Description:
 * @Version: 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    /**
     * 根据月份查询预约设置信息
     * @param month
     * @return
     */
    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public List<OrderSetting> findByMonth(String month) {
        //拼接当月第一天日期
        //month = 2019-6-1
        String startDate = month + "-1";
        //拼接当月最后一天日期
        //month = 2019-6-31
        String endDate = month + "-31";
        return orderSettingDao.findByMonth(startDate,endDate);
    }

    /**
     * 批量导入预约设置信息
     *  1.判断该日期是否进行过预约设置
     *  2. 如果该日期已经预约设置，根据该日期进行修改预约设置
     *        在修改之前，需要判断已预约人数是否小于可预约人数，
     *        如果大于可预约人数，提示客户不能修改设置[抛出异常]
     *  3. 如果该日期没有预约设置 ，直接添加
     *
     * @param orderSettingList
     */
    @Override
    public void addOrderSettingList(List<OrderSetting> orderSettingList) {
        //遍历集合
        if(orderSettingList != null && orderSettingList.size() > 0){
            for (OrderSetting orderSetting : orderSettingList) {
                //1.判断该日期是否进行过预约设置
                //根据日期查询预约设置对象
                OrderSetting orderSettingDB =
                        orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                if(orderSettingDB != null){
                    // 2. 如果该日期已经预约设置，根据该日期进行修改预约设置
                    //orderSettingDB.getReservations() 数据库中查询出来的已预约人数
                    //orderSetting.getNumber():要设置的可预约人数
                    //在修改之前，需要判断已预约人数是否小于可预约人数，
                    if(orderSettingDB.getReservations() > orderSetting.getNumber()){
                        //如果大于可预约人数，提示客户不能修改设置[抛出异常]
                        throw  new RuntimeException("已预约人数超过了可预约人数，不可修改!!");
                    }else{
                        orderSettingDao.update(orderSetting);
                    }
                }else{
                    // 3. 如果该日期没有预约设置 ，直接添加
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }
}
