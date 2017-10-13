package com.example.mnkj.newobject.Bean;

import java.io.Serializable;

/**
 * Created by wyw on 2016/10/20.
 * 扫描二维码得到的实体类
 */
public class ScanInputBean implements Serializable {
    private String rukutime;//入库时间
    private boolean bemodfiyed = false;//是否已完善数据 默认为false

    private String zhuiSuMa;//追溯码
    private String tongYongMing;//通用名
    private String wenHao;//批准文号
    private String name;//企业名称
    private String phone;//企业电话
    private String count = "1";//数量

    private String productionDate;//生产日期
    private String expiryDate;//有效期
    private String productNumber;//生产批号
    private String unit = "盒";//单位

    private String purchasePrice = "0";//购买价格
    private String presellPrice = "0";//核定售价
    private String FJesum = "0";//金额总计


    private String yplxname; //抗生素 隐藏字段 选填 商品类型
    private String tradeCodeList;//tradeCodeList 隐藏字段

    private String Cpname;//商品名称
    private String Specification;//规格
    private String FDljg;//代理机构
    private String YpArea;//存放区域
    private String FStoreHj;//存储环境
    private String nm;//内码

    private String FGrDate;//当前时间
    private String FCodeID;//订单编号  根据当前时间生成编号
    private String FStatus = "进货";//进货状态  "进货"
    private String mytId;//上传表的主键 为空
    private String IsUpload = "0";//上传状态  0

    private String FPzwh;//批准文号

    public String getFGrDate() {
        return FGrDate;
    }

    public String getFPzwh() {
        return FPzwh;
    }

    public void setFPzwh(String FPzwh) {
        this.FPzwh = FPzwh;
    }


    public void setFGrDate(String FGrDate) {
        this.FGrDate = FGrDate;
    }

    public String getFCodeID() {
        return FCodeID;
    }

    public void setFCodeID(String FCodeID) {
        this.FCodeID = FCodeID;
    }

    public String getFStatus() {
        return FStatus;
    }

    public void setFStatus(String FStatus) {
        this.FStatus = FStatus;
    }

    public String getMytId() {
        return mytId;
    }

    public void setMytId(String mytId) {
        this.mytId = mytId;
    }

    public String getIsUpload() {
        return IsUpload;
    }

    public void setIsUpload(String isUpload) {
        IsUpload = isUpload;
    }


    public boolean isBemodfiyed() {
        return bemodfiyed;
    }

    public void setBemodfiyed(boolean bemodfiyed) {
        this.bemodfiyed = bemodfiyed;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }


    public String getCpname() {
        return Cpname;
    }

    public void setCpname(String cpname) {
        Cpname = cpname;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getFJesum() {
        return FJesum;
    }

    public void setFJesum(String FJesum) {
        this.FJesum = FJesum;
    }


    public String getFDljg() {
        return FDljg;
    }

    public void setFDljg(String FDljg) {
        this.FDljg = FDljg;
    }

    public String getYpArea() {
        return YpArea;
    }

    public void setYpArea(String ypArea) {
        YpArea = ypArea;
    }

    public String getFStoreHj() {
        return FStoreHj;
    }

    public void setFStoreHj(String FStoreHj) {
        this.FStoreHj = FStoreHj;
    }


    private Boolean bechecked = false;//是否被选择

    public String getRukutime() {
        return rukutime;
    }

    public void setRukutime(String rukutime) {
        this.rukutime = rukutime;
    }

    public ScanInputBean() {
    }

    public ScanInputBean(String zhuiSuMa, String tongYongMing, String wenHao, String name, String phone) {
        this.zhuiSuMa = zhuiSuMa;
        this.tongYongMing = tongYongMing;
        this.wenHao = wenHao;
        this.name = name;
        this.phone = phone;
    }

    public ScanInputBean(String zhuiSuMa, String tongYongMing, String wenHao, String name, String phone, String productionDate, String expiryDate, String productNumber, String unit, String purchasePrice, String presellPrice) {
        this.zhuiSuMa = zhuiSuMa;
        this.tongYongMing = tongYongMing;
        this.wenHao = wenHao;
        this.name = name;
        this.phone = phone;
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
        this.productNumber = productNumber;
        this.unit = unit;
        this.purchasePrice = purchasePrice;
        this.presellPrice = presellPrice;
    }

    public Boolean getBechecked() {
        return bechecked;
    }

    public void setBechecked(Boolean bechecked) {
        this.bechecked = bechecked;
    }

    public String getYplxname() {
        return yplxname;
    }

    public void setYplxname(String yplxname) {
        this.yplxname = yplxname;
    }

    public String getTradeCodeList() {
        return tradeCodeList;
    }

    public void setTradeCodeList(String tradeCodeList) {
        this.tradeCodeList = tradeCodeList;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPresellPrice() {
        return presellPrice;
    }

    public void setPresellPrice(String presellPrice) {
        this.presellPrice = presellPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getZhuiSuMa() {
        return zhuiSuMa;
    }

    public void setZhuiSuMa(String zhuiSuMa) {
        this.zhuiSuMa = zhuiSuMa;
    }

    public String getTongYongMing() {
        return tongYongMing;
    }

    public void setTongYongMing(String tongYongMing) {
        this.tongYongMing = tongYongMing;
    }

    public String getWenHao() {
        return wenHao;
    }

    public void setWenHao(String wenHao) {
        this.wenHao = wenHao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
