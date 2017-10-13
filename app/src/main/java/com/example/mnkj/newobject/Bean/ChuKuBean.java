package com.example.mnkj.newobject.Bean;

/**
 * Created by mnkj on 2017/9/12.
 */
//出库
public class ChuKuBean {
    private String sptype;//商品名
    private String gysname;//供应商名
    private String jgname;//机构名

    public String getGysname() {
        return gysname;
    }

    public void setGysname(String gysname) {
        this.gysname = gysname;
    }

    public String getJgname() {
        return jgname;
    }

    public void setJgname(String jgname) {
        this.jgname = jgname;
    }

    public String getSptype() {
        return sptype;
    }

    public void setSptype(String sptype) {
        this.sptype = sptype;
    }
}
