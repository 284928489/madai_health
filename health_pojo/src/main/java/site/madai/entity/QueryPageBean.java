package site.madai.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 封装查询条件
 */
public class QueryPageBean implements Serializable {
    //页码
    private Integer currentPage;
    //每页记录数
    private Integer pageSize;
    //查询条件
    private String queryString;
    private List<Date> dateRangeList;

    public List<Date> getDateRangeList() {
        return dateRangeList;
    }

    public void setDateRangeList(List<Date> dateRangeList) {
        this.dateRangeList = dateRangeList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}