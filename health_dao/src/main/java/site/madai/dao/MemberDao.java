package site.madai.dao;

import org.apache.ibatis.annotations.Param;
import site.madai.pojo.Member;

import java.util.Date;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-30 21:52
 * @Description:
 * @Version: 1.0
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountBeforeDate(@Param("regTime") String m);

    Integer findMemberCountByDate(@Param("regTime") String today);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(@Param("regTime") String thisWeekMonday);
}
