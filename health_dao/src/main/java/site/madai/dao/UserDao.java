package site.madai.dao;

import org.apache.ibatis.annotations.Param;
import site.madai.pojo.User;

/**
 * @Project: site.madai.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:08
 * @Description:
 * @Version: 1.0
 */
public interface UserDao {

    User findByUsername(@Param("username") String username);
}
