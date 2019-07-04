package site.madai.dao;

import org.apache.ibatis.annotations.Param;
import site.madai.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-29 20:15
 * @Description:
 * @Version: 1.0
 */
public interface OrderSettingDao {
    /**
     * @param orderDate formate : yyyy-MM-dd
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    void update(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findByMonth(@Param("startDate") String startDate,
                                   @Param("endDate") String endDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
