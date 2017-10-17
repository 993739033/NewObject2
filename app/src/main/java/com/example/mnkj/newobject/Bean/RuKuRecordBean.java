package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;

import java.util.List;

/**
 * Created by mnkj on 2017/9/13.
 */
//入库记录
public class RuKuRecordBean extends BaseMsg{
    private List<RuKuRecordBean.DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }
    public class DataList{
        private String FStId;

        private String FSm1;

        private String FJbr;

        private String FTid;

        private String FGrDate;//购入日期

        private String FCodeID;

        private String FDljg;//代理机构

        private String FProductEnterprise;//生产企业名称

        private String FTyName;//通用名称

        private String FGuige;//规格

        private String FProductName;//商品名称

        private String FPzwh;//批准文号

        private String FYxqDate;//有效期

        private String FBuyNum;//进货数量

        private String FDw;//单位

        private String FCgdj;//采购价格（元）

        private String FHdjg;//核定价格（元）

        private String FJesum;//金额总计

        private String FNmcode;//企业内码

        private String FStoreHj;//存储环境

        private String Remark;

        private String FStatus;

        private String mytId;

        private String IsUpload;

        private String Yplx;//商品类型

        private String FGysmc;//供应商名称

        private String FXgzsh;//相关证书号

        private String FLxr;//联系人

        private String FLxrdh;//联系人电话

        private String FScph;//生产批号

        private String FScDate;//生产日期

        private String YpArea;//存放区域

        private String SyGmp;

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }

        public String getFSm1() {
            return FSm1;
        }

        public void setFSm1(String FSm1) {
            this.FSm1 = FSm1;
        }

        public String getFJbr() {
            return FJbr;
        }

        public void setFJbr(String FJbr) {
            this.FJbr = FJbr;
        }

        public String getFTid() {
            return FTid;
        }

        public void setFTid(String FTid) {
            this.FTid = FTid;
        }

        public String getFGrDate() {
            return FGrDate;
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

        public String getFPzwh() {
            return FPzwh;
        }

        public void setFPzwh(String FPzwh) {
            this.FPzwh = FPzwh;
        }

        public String getFYxqDate() {
            return FYxqDate;
        }

        public void setFYxqDate(String FYxqDate) {
            this.FYxqDate = FYxqDate;
        }

        public String getFBuyNum() {
            return FBuyNum;
        }

        public void setFBuyNum(String FBuyNum) {
            this.FBuyNum = FBuyNum;
        }

        public String getFDw() {
            return FDw;
        }

        public void setFDw(String FDw) {
            this.FDw = FDw;
        }

        public String getFCgdj() {
            return FCgdj;
        }

        public void setFCgdj(String FCgdj) {
            this.FCgdj = FCgdj;
        }

        public String getFHdjg() {
            return FHdjg;
        }

        public void setFHdjg(String FHdjg) {
            this.FHdjg = FHdjg;
        }

        public String getFJesum() {
            return FJesum;
        }

        public void setFJesum(String FJesum) {
            this.FJesum = FJesum;
        }

        public String getFNmcode() {
            return FNmcode;
        }

        public void setFNmcode(String FNmcode) {
            this.FNmcode = FNmcode;
        }

        public String getFStoreHj() {
            return FStoreHj;
        }

        public void setFStoreHj(String FStoreHj) {
            this.FStoreHj = FStoreHj;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
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

        public String getFGysmc() {
            return FGysmc;
        }

        public void setFGysmc(String FGysmc) {
            this.FGysmc = FGysmc;
        }

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

        public String getYpArea() {
            return YpArea;
        }

        public void setYpArea(String ypArea) {
            YpArea = ypArea;
        }

        public String getSyGmp() {
            return SyGmp;
        }

        public void setSyGmp(String syGmp) {
            SyGmp = syGmp;
        }
    }
}
