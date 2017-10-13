package com.example.mnkj.newobject.Bean;

/**
 * Created by mnkj on 2017/9/13.
 */
//入库记录
public class RuKuRecordBean {
    private String buydate;
    private String normalname;
    private String gysname;
    private String jhcount;//进货数量
    private String price;//价格

    public String getBuydate() {
        return buydate;
    }

    public void setBuydate(String buydate) {
        this.buydate = buydate;
    }

    public String getNormalname() {
        return normalname;
    }

    public void setNormalname(String normalname) {
        this.normalname = normalname;
    }

    public String getGysname() {
        return gysname;
    }

    public void setGysname(String gysname) {
        this.gysname = gysname;
    }

    public String getJhcount() {
        return jhcount;
    }

    public void setJhcount(String jhcount) {
        this.jhcount = jhcount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
