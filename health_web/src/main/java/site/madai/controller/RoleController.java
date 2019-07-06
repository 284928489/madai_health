package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.Result;
import site.madai.pojo.Role;
import site.madai.service.RoleService;

import java.util.List;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-06 23:28
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("findAll")
    public Result findAll(){
        List<Role> roleList = roleService.findAll();
        if (roleList != null && roleList.size() > 0) {
            Result result = new Result(true,
                    MessageConstant.QUERY_ROLE_SUCCESS);
            result.setData(roleList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_ROLE_FILE);
    }
}
