package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.service.OrderSettingListService;

import java.util.Date;
import java.util.List;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-03 09:43
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("ordersettinglist")
public class OrderSettingListController {

    @Reference
    private OrderSettingListService orderSettingListService;



    @RequestMapping("findByPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = orderSettingListService.queryPage(queryPageBean);
        if(pageResult.getRows().size() == 0){
            if ((queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0)||(queryPageBean.getDateRangeList() != null && queryPageBean.getDateRangeList().size() > 0)){
                queryPageBean.setCurrentPage(1);
                pageResult = orderSettingListService.queryPage(queryPageBean);
            }
        }
        return pageResult;
    }

    @RequestMapping("delOrderSettingById")
    public Result delOrderSettingById(Integer id){
        try {
            orderSettingListService.delOrderSettingById(id);
            return new Result(true, MessageConstant.DELETE_ORDER_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_ORDER_FAIL);
        }
    }
}
