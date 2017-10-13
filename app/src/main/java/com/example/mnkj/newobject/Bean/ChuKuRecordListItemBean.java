package com.example.mnkj.newobject.Bean;

/**
 * Created by mnkj on 2017/9/13.
 */
//出库item包含的记录
public class ChuKuRecordListItemBean {
    private String name;
    private String farmname;
    private String normalname;
    private String salecount;
    private Boolean ischecked;

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarmname() {
        return farmname;
    }

    public void setFarmname(String farmname) {
        this.farmname = farmname;
    }

    public String getNormalname() {
        return normalname;
    }

    public void setNormalname(String normalname) {
        this.normalname = normalname;
    }

    public String getSalecount() {
        return salecount;
    }

    public void setSalecount(String salecount) {
        this.salecount = salecount;
    }
}
