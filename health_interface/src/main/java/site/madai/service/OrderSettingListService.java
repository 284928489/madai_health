package site.madai.service;

import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-03 10:45
 * @Description:
 * @Version: 1.0
 */
public interface OrderSettingListService {
    PageResult queryPage(QueryPageBean queryPageBean);

    void delOrderSettingById(Integer id);
}
