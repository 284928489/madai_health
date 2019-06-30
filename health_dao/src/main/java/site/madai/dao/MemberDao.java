package site.madai.dao;

import site.madai.pojo.Member;

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
}
