package site.madai.service;

import site.madai.entity.PageResult;
import site.madai.entity.QueryPageBean;
import site.madai.pojo.CheckItem;

import java.util.List;

/**
 * @Project: site.madai.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-06-24 21:43
 * @Description:
 * @Version: 1.0
 */
public interface CheckItemService {

    PageResult queryPage(QueryPageBean queryPageBean);

    void add(CheckItem checkItem);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);

    void delById(Integer id);

    List<CheckItem> findAll();
}
