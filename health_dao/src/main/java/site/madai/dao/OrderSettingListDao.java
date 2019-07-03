package site.madai.dao;

import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-03 10:55
 * @Description:
 * @Version: 1.0
 */
public interface OrderSettingListDao {
    Page<Map<String, Object>> findByCondition(Map<String,Object> conditionMap);
}
