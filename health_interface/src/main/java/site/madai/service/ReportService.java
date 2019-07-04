package site.madai.service;

import java.util.Map;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-01 17:53
 * @Description:
 * @Version: 1.0
 */
public interface ReportService {

    /**
     * 获得运营统计数据
     * Map数据格式：
     * todayNewMember -> number
     * totalMember -> number
     * thisWeekNewMember -> number
     * thisMonthNewMember -> number
     * todayOrderNumber -> number
     * todayVisitsNumber -> number
     * thisWeekOrderNumber -> number
     * thisWeekVisitsNumber -> number
     * thisMonthOrderNumber -> number
     * thisMonthVisitsNumber -> number
     * hotSetmeals -> List<Setmeal>
     */
    Map<String, Object> getBusinessReport() throws Exception;
}
