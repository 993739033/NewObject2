package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;

import java.util.List;

/**
 * Created by mnkj on 2017/9/14.
 */
//利润列表bean
public class ProfitBean extends BaseMsg {
    private List<DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    public class DataList {
        private String FSuserId;

        private String FStId;

        private String FCodeID;

        private String FXsDate;//销售日期

        private String FProductEnterprise;//生产企业名称

        private String FTyName;//通用名称

        private String FGuige;//规格

        private String FProductName;//商品名称

        private String FPzwh;//批准文号

        private String FXsNum;//销售数量

        private String FDw;//单位

        private String FXsdjPrice;//销售单价(元)

        private String FJhprice;//进货价格(元)

        private String FProfitSum;//销售毛利

        private String FStatus;

        private String Yplx;

        private String FGysmc;//供应商名称

        private String FScph;//生产批号

        private String FScDate;//生产日期

        public String getFSuserId() {
            return FSuserId;
        }

        public void setFSuserId(String FSuserId) {
            this.FSuserId = FSuserId;
        }

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }

        public String getFCodeID() {
            return FCodeID;
        }

        public void setFCodeID(String FCodeID) {
            this.FCodeID = FCodeID;
        }

        public String getFXsDate() {
            return FXsDate;
        }

        public void setFXsDate(String FXsDate) {
            this.FXsDate = FXsDate;
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

        public String getFPzwh() {
            return FPzwh;
        }

        public void setFPzwh(String FPzwh) {
            this.FPzwh = FPzwh;
        }

        public String getFXsNum() {
            return FXsNum;
        }

        public void setFXsNum(String FXsNum) {
            this.FXsNum = FXsNum;
        }

        public String getFDw() {
            return FDw;
        }

        public void setFDw(String FDw) {
            this.FDw = FDw;
        }

        public String getFXsdjPrice() {
            return FXsdjPrice;
        }

        public void setFXsdjPrice(String FXsdjPrice) {
            this.FXsdjPrice = FXsdjPrice;
        }

        public String getFJhprice() {
            return FJhprice;
        }

        public void setFJhprice(String FJhprice) {
            this.FJhprice = FJhprice;
        }

        public String getFProfitSum() {
            return FProfitSum;
        }

        public void setFProfitSum(String FProfitSum) {
            this.FProfitSum = FProfitSum;
        }

        public String getFStatus() {
            return FStatus;
        }

        public void setFStatus(String FStatus) {
            this.FStatus = FStatus;
        }

        public String getYplx() {
            return Yplx;
        }

        public void setYplx(String yplx) {
            Yplx = yplx;
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

        public String getFScDate() {
            return FScDate;
        }

        public void setFScDate(String FScDate) {
            this.FScDate = FScDate;
        }
    }
}
