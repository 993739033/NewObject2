package com.example.mnkj.newobject.Bean;

/**
 * Created by mnkj on 2017/9/12.
 */
//入库
public class RuKuBean {
    private String sptype;//商品名
    private String qiyename;//生产企业名
    private String normalname;//通用名
    private Boolean bechecked;

    public String getSptype() {
        return sptype;
    }

    public void setSptype(String sptype) {
        this.sptype = sptype;
    }

    public String getQiyename() {
        return qiyename;
    }

    public void setQiyename(String qiyename) {
        this.qiyename = qiyename;
    }

    public String getNormalname() {
        return normalname;
    }

    public void setNormalname(String normalname) {
        this.normalname = normalname;
    }

    public Boolean getBechecked() {
        return bechecked;
    }

    public void setBechecked(Boolean bechecked) {
        this.bechecked = bechecked;
    }
}
