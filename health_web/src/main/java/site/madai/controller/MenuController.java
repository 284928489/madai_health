package site.madai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.madai.constant.MessageConstant;
import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.entity.Result;
import site.madai.pojo.Menu;
import site.madai.service.MenuService;

import java.util.List;

/**
 * @Project: site.madai.controller
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-07-07 10:37
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = menuService.queryPage(queryPageBean);
        if (pageResult.getRows().size() == 0) {
            if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0) {
                queryPageBean.setCurrentPage(1);
                pageResult = menuService.queryPage(queryPageBean);
            }
        }
        return pageResult;
    }

    @RequestMapping("findAllParentmenu")
    public Result findAllParentmenu(){
        List<Menu> parentMenus = menuService.findAll();
        if (parentMenus != null && parentMenus.size() > 0) {
            Result result = new Result(true,
                    MessageConstant.QUERY_PARENTMENU_SUCCESS);
            result.setData(parentMenus);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_PARENTMENU_FAIL);
    }


    @RequestMapping("add")
    public Result add(@RequestBody Menu menu,Integer[] parentMenuIds){
        try {
            if(menu.getLevel() == 2){
                if(parentMenuIds.length == 0){
                    return new Result(false, "二级菜单需要有一个父级菜单");
                }
                if(parentMenuIds.length > 1){
                    return new Result(false, "二级菜单只能有一个父级菜单");
                }
            }
            menuService.add(menu, parentMenuIds);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            //新增失败
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
    }

    @RequestMapping("edit")
    public Result edit(@RequestBody Menu menu,Integer[] parentMenuIds){
        try {
            if (menu.getLevel() == 2) {
                if (parentMenuIds.length == 0) {
                    return new Result(false, "二级菜单需要有一个父级菜单");
                }
                if (parentMenuIds.length > 1) {
                    return new Result(false, "二级菜单只能有一个父级菜单");
                }
            }
            menuService.edit(menu, parentMenuIds);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        }catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            //新增失败
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
    }

    @RequestMapping("getMenuById")
    public Result getMenuById(Integer id){
        Menu menu = menuService.getMenuById(id);
        if (menu != null) {
            Result result = new Result(true,
                    MessageConstant.QUERY_MENU_SUCCESS);
            result.setData(menu);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_MENU_FAIL);
    }

    @RequestMapping("findparentMenuIdsById")
    public List<Integer> findparentMenuIdsById(Integer id) {
        return menuService.findparentMenuIdsById(id);
    }

    @RequestMapping("delMenuById")
    public Result delMenuById(Integer id) {
        try {
            menuService.delMenuById(id);
            return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
    }
}
