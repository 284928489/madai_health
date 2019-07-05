package site.madai.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 检查组
 */
public class CheckGroup implements Serializable {
    //主键
    private Integer id;
    //编码
    private String code;
    //名称
    private String name;
    //助记
    private String helpCode;
    //适用性别
    private String sex;
    //介绍
    private String remark;
    //注意事项
    private String attention;
    //一个检查组合包含多个检查项
    private List<CheckItem> checkItems;

    public List<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}
