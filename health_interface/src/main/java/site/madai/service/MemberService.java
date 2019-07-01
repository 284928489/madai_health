package site.madai.service;

import site.madai.pojo.Member;

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
}
