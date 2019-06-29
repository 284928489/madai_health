package site.madai.service;

import site.madai.pojo.OrderSetting;

import java.util.List;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-29 20:06
 * @Description:
 * @Version: 1.0
 */
public interface OrderSettingService {
    void addOrderSettingList(List<OrderSetting> orderSettingList);

    List<OrderSetting> findByMonth(String month);
}
