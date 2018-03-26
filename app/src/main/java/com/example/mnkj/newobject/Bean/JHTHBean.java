package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;

import java.io.Serializable;
import java.util.List;

/**
 * 进货退货扫描bean
 */

public class JHTHBean extends BaseMsg {
    private List<DataList> data;

    public List<DataList> getDataList() {
        return data;
    }

    public void setDataList(List<DataList> dataList) {
        this.data = dataList;
    }

    public class DataList implements Serializable {
        private Boolean bemodfiyed = false;

        private Boolean bechecked = false;

        private String FStId;//唯一id
        private String FSuserId;//用户id
        private String FProductEnterprise;//企业名称
        private String FTyName;//通用名称
        private String FGuige;//规格
        private String FProductName;//商品名称
        private String FSm1;//追溯码
        private String Yplx;//药品类型
        private String YpArea;//存放区域
        private String FGysmc;//供应商名称
        private String FScph;//生产批号
        private String FStoreHj;//存储环境
        private String FDljg;//代理机构
        private String FNmcode;//内码
        private String FPzwh;//批准文号
        private String FKcsl;//库存数量
        private String FYxqDate;//有效期
        private String FDw;//单位
        private String FDjPrice;//购买价格
        private String FSjPrice;//销售价格
        private String FScDate;//生产日期

        private String FJesum;//金额总计

        private String FBuyNum;//退货数量


        private String FXgzsh;//相关证书号
        private String FLxr;//联系人
        private String FLxrdh;//联系人电话
        private String SyGmp;//gmp证书号

        public String getFXgzsh() {
            return FXgzsh;
        }

        public void setFXgzsh(String FXgzsh) {
            this.FXgzsh = FXgzsh;
        }

        public String getFLxr() {
            return FLxr;
        }

        public void setFLxr(String FLxr) {
            this.FLxr = FLxr;
        }

        public String getFLxrdh() {
            return FLxrdh;
        }

        public void setFLxrdh(String FLxrdh) {
            this.FLxrdh = FLxrdh;
        }

        public String getSyGmp() {
            return SyGmp;
        }

        public void setSyGmp(String syGmp) {
            SyGmp = syGmp;
        }

        public String getFBuyNum() {
            return FBuyNum;
        }

        public void setFBuyNum(String FBuyNum) {
            this.FBuyNum = FBuyNum;
        }

        public String getFJesum() {
            return FJesum;
        }

        public void setFJesum(String FJesum) {
            this.FJesum = FJesum;
        }

        public Boolean getBechecked() {
            return bechecked;
        }

        public void setBechecked(Boolean bechecked) {
            this.bechecked = bechecked;
        }

        public Boolean getBemodfiyed() {
            return bemodfiyed;
        }

        public void setBemodfiyed(Boolean bemodfiyed) {
            this.bemodfiyed = bemodfiyed;
        }

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }

        public String getFSuserId() {
            return FSuserId;
        }

        public void setFSuserId(String FSuserId) {
            this.FSuserId = FSuserId;
        }

        public String getFProductEnterprise() {
            return FProductEnterprise;
        }

        public void setFProductEnterprise(String FProductEnterprise) {
            this.FProductEnterprise = FProductEnterprise;
        }

        public String getFTyName() {
            return FTyName;
        }

        public void setFTyName(String FTyName) {
            this.FTyName = FTyName;
        }

        public String getFGuige() {
            return FGuige;
        }

        public void setFGuige(String FGuige) {
            this.FGuige = FGuige;
        }

        public String getFProductName() {
            return FProductName;
        }

        public void setFProductName(String FProductName) {
            this.FProductName = FProductName;
        }

        public String getFSm1() {
            return FSm1;
        }

        public void setFSm1(String FSm1) {
            this.FSm1 = FSm1;
        }

        public String getYplx() {
            return Yplx;
        }

        public void setYplx(String yplx) {
            Yplx = yplx;
        }

        public String getYpArea() {
            return YpArea;
        }

        public void setYpArea(String ypArea) {
            YpArea = ypArea;
        }

        public String getFGysmc() {
            return FGysmc;
        }

        public void setFGysmc(String FGysmc) {
            this.FGysmc = FGysmc;
        }

        public String getFScph() {
            return FScph;
        }

        public void setFScph(String FScph) {
            this.FScph = FScph;
        }

        public String getFStoreHj() {
            return FStoreHj;
        }

        public void setFStoreHj(String FStoreHj) {
            this.FStoreHj = FStoreHj;
        }

        public String getFDljg() {
            return FDljg;
        }

        public void setFDljg(String FDljg) {
            this.FDljg = FDljg;
        }

        public String getFNmcode() {
            return FNmcode;
        }

        public void setFNmcode(String FNmcode) {
            this.FNmcode = FNmcode;
        }

        public String getFPzwh() {
            return FPzwh;
        }

        public void setFPzwh(String FPzwh) {
            this.FPzwh = FPzwh;
        }

        public String getFKcsl() {
            return FKcsl;
        }

        public void setFKcsl(String FKcsl) {
            this.FKcsl = FKcsl;
        }

        public String getFYxqDate() {
            return FYxqDate;
        }

        public void setFYxqDate(String FYxqDate) {
            this.FYxqDate = FYxqDate;
        }

        public String getFDw() {
            return FDw;
        }

        public void setFDw(String FDw) {
            this.FDw = FDw;
        }

        public String getFDjPrice() {
            return FDjPrice;
        }

        public void setFDjPrice(String FDjPrice) {
            this.FDjPrice = FDjPrice;
        }

        public String getFSjPrice() {
            return FSjPrice;
        }

        public void setFSjPrice(String FSjPrice) {
            this.FSjPrice = FSjPrice;
        }

        public String getFScDate() {
            return FScDate;
        }

        public void setFScDate(String FScDate) {
            this.FScDate = FScDate;
        }
    }
}
