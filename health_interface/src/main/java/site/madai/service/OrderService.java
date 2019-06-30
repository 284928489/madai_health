package site.madai.service;

import com.alibaba.dubbo.config.annotation.Service;
import site.madai.entity.Result;

import java.util.Map;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-30 21:33
 * @Description:
 * @Version: 1.0
 */
public interface OrderService {

    Result order(Map map) throws Exception;

    Result findById4Detail(Integer id) throws Exception;
}
