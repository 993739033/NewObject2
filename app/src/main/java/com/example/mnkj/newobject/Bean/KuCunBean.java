package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mnkj on 2017/10/13.
 */

public class KuCunBean extends BaseMsg implements Serializable {

    //    {"errorCode" : 0,"errorMsg" : "success","dataList" :[{"FSuserId":"1",
// "FProductEnterprise":"南京日升昌生物技术有限公司","FTyName":"恩诺沙星粉（水产用）"
// ,"FGuige":"5%","FProductName":"","FSm1":"201607120049100007470711",
// "Yplx":"处方药","YpArea":"尽可能","FGysmc":"22",
// "FScph":"2016101801","FStoreHj":"吞没","FDljg":"不",
// "FNmcode":"56885","FPzwh":"兽药字（2012）100589106","FKcsl":"1",
// "FYxqDate":"2018/10/18 0:00:00","FDw":"盒",
// "FDjPrice":"56.00","FSjPrice":"56.00","FDljg1":"不"}]}
    private List<DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    public class DataList implements Serializable {
        private boolean Selected = false;//是否被选择

        private boolean isNotEnough = true;//存储量是否不足  true 为充足

        private boolean beModfiy = false;//是否完善啦信息

        private String FSuserId;

        private String FStId;//唯一id
        private String FProductEnterprise;//生产企业名称
        private String FTyName;//通用名称
        private String FGuige;//规格
        private String FProductName;//商品名称
        private String FSm1;//追溯码
        private String Yplx;//药品类型
        private String YpArea;
        private String FGysmc;//供应商名称
        private String FScph;//生产批号
        private String FStoreHj;//存储环境
        private String FDljg;//代理机构
        private String FNmcode;//内码
        private String FPzwh;//批准文号
        private String FKcsl;//库存数量
        private String FYxqDate;//有效日期
        private String FDw;//单位
        private String FDjPrice;//单价
        private String FSjPrice;//库存金额
        private String FDljg1;

        private String FJesum = "";//损失金额
        private String FBuyNum = "";//损失数量

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }

        public boolean isBeModfiy() {
            return beModfiy;
        }

        public void setBeModfiy(boolean beModfiy) {
            this.beModfiy = beModfiy;
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

        public boolean isNotEnough() {
            return isNotEnough;
        }

        public void setNotEnough(boolean notEnough) {
            isNotEnough = notEnough;
        }


        private String FScDate;//生产日期

        public String getFScDate() {
            return FScDate;
        }

        public void setFScDate(String FScDate) {
            this.FScDate = FScDate;
        }

        public boolean isSelected() {
            return Selected;
        }

        public void setSelected(boolean selected) {
            this.Selected = selected;
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

        public String getFDljg1() {
            return FDljg1;
        }

        public void setFDljg1(String FDljg1) {
            this.FDljg1 = FDljg1;
        }
    }

}
