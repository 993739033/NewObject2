package com.example.mnkj.newobject.Bean;

/**
 * Created by mnkj on 2017/9/13.
 */
//库存
public class KuCunBean {
    private String normalname;
    private String count;
    private Boolean isSelectd;

    public Boolean getSelectd() {
        return isSelectd;
    }

    public void setSelectd(Boolean selectd) {
        isSelectd = selectd;
    }

    public String getNormalname() {
        return normalname;
    }

    public void setNormalname(String normalname) {
        this.normalname = normalname;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
