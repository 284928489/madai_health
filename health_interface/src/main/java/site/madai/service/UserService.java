package site.madai.service;

import site.madai.pojo.User;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-05 21:06
 * @Description:
 * @Version: 1.0
 */
public interface UserService {

    User findByUsername(String username);
}
