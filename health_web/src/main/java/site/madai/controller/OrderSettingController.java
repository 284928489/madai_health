package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.madai.constant.MessageConstant;
import site.madai.entity.Result;
import site.madai.pojo.OrderSetting;
import site.madai.service.OrderSettingService;
import site.madai.utils.POIUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-29 19:59
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("saveOrEdit")
    public Result saveOrEdit(@RequestBody OrderSetting orderSetting){
        try {
            List<OrderSetting > orderSettingList = new ArrayList<>();
            orderSettingList.add(orderSetting);
            orderSettingService.addOrderSettingList(orderSettingList);
            return  new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return  new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("findByMonth")
    public Result findByMonth(String month){
        try {
            List<OrderSetting> orderSettingList = orderSettingService.findByMonth(month);
            /**
             * 获取到数据结构
             * [
             *  { orderDate:2019-06-29, number:300, reservations:200 },
             *  { orderDate:2019-06-30, number:200, reservations:0 }
             * ]
             *
             * 需要的数据结构
             * [
             { date: 1, number: 120, reservations: 1 },
             { date: 3, number: 120, reservations: 1 },
             { date: 4, number: 120, reservations: 120 },
             { date: 6, number: 120, reservations: 1 },
             { date: 8, number: 120, reservations: 1 }
             ]
             */
            //将查询到数据结构转换为需要的数据结构
            List<Map<String,Object>> leftobj = new ArrayList<>();
            //一个orderSetting对象对应一个Map集合
            for (OrderSetting orderSetting : orderSettingList) {
                Map<String ,Object> map = new HashMap<>();
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                //把 map集合存储到list集合中
                leftobj.add(map);
            }
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS, leftobj);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("upload")
    public Result upload(@RequestBody MultipartFile excelFile){
        try {
            List<String[]> stringsList = POIUtils.readExcel(excelFile);
            //把stringsList集合中的数据 转换到OrderSettingList对象中
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] strings : stringsList) {
                //有一个string数组，就需要有一个预约设置对象
                OrderSetting orderSetting = new OrderSetting();
                //把数组中的数据转移到orderSetting中
                String orderDateStr = strings[0];
                SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
                Date orderDate = format.parse(orderDateStr);
                //设置预约时间
                orderSetting.setOrderDate(orderDate);
                String numberStr = strings[1];
                //设置可预约人数
                orderSetting.setNumber(Integer.parseInt(numberStr));
                //把预约设置对象添加到集合中
                orderSettingList.add(orderSetting);
            }
            orderSettingService.addOrderSettingList(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
}
