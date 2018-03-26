package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mnkj on 2017/9/13.
 */
//出库记录
public class ChuKuRecordBean extends BaseMsg implements Serializable {
    private List<ChuKuRecordBean.DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    public class DataList implements Serializable{
        private String FStId;//主键Id

        private String FTid;

        private String FSm1;

        private String FCodeID;//票号

        private String FXsDate;//销售日期

        private String FBuyTel;//购买电话

        private String FBuyName;//购买人

        private String FPzwh;//批准文号

        private String FNmCode;//内码

        private String FDljg;//代理机构

        private String FProductEnterprise;//生产企业名称

        private String FTyName;//通用名称

        private String FGuige;//规格

        private String FProductName;//商品名称

        private String FYxqDate;//有效日期

        private String FXsNum;//销售数量

        private String FDw;//单位

        private String FDjprice;//单价

        private String FSumprice;//

        private String FStoreHJ;//核定价格

        private String Remark;//备注

        private String FGlid;

        private String FStatus;//销售类型

        private String mytId;

        private String IsUpload;

        private String Yplx;//药品类型

        private String FScph;//生产批号

        private String FScDate;//生产日期

        private String FGysmc;//供应商名称

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }

        public String getFTid() {
            return FTid;
        }

        public void setFTid(String FTid) {
            this.FTid = FTid;
        }

        public String getFSm1() {
            return FSm1;
        }

        public void setFSm1(String FSm1) {
            this.FSm1 = FSm1;
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

        public String getFBuyTel() {
            return FBuyTel;
        }

        public void setFBuyTel(String FBuyTel) {
            this.FBuyTel = FBuyTel;
        }

        public String getFBuyName() {
            return FBuyName;
        }

        public void setFBuyName(String FBuyName) {
            this.FBuyName = FBuyName;
        }

        public String getFPzwh() {
            return FPzwh;
        }

        public void setFPzwh(String FPzwh) {
            this.FPzwh = FPzwh;
        }

        public String getFNmCode() {
            return FNmCode;
        }

        public void setFNmCode(String FNmCode) {
            this.FNmCode = FNmCode;
        }

        public String getFDljg() {
            return FDljg;
        }

        public void setFDljg(String FDljg) {
            this.FDljg = FDljg;
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

        public String getFYxqDate() {
            return FYxqDate;
        }

        public void setFYxqDate(String FYxqDate) {
            this.FYxqDate = FYxqDate;
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

        public String getFDjprice() {
            return FDjprice;
        }

        public void setFDjprice(String FDjprice) {
            this.FDjprice = FDjprice;
        }

        public String getFSumprice() {
            return FSumprice;
        }

        public void setFSumprice(String FSumprice) {
            this.FSumprice = FSumprice;
        }

        public String getFStoreHJ() {
            return FStoreHJ;
        }

        public void setFStoreHJ(String FStoreHJ) {
            this.FStoreHJ = FStoreHJ;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }

        public String getFGlid() {
            return FGlid;
        }

        public void setFGlid(String FGlid) {
            this.FGlid = FGlid;
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

        public String getYplx() {
            return Yplx;
        }

        public void setYplx(String yplx) {
            Yplx = yplx;
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

        public String getFGysmc() {
            return FGysmc;
        }

        public void setFGysmc(String FGysmc) {
            this.FGysmc = FGysmc;
        }
    }
}
