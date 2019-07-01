package site.madai.service;

import site.madai.pojo.Member;

import java.text.ParseException;
import java.util.List;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-01 15:03
 * @Description:
 * @Version: 1.0
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    List<Integer> findMemberCountByMonth(List<String> list);
}
