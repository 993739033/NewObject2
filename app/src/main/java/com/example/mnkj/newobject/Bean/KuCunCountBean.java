package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseObjectEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mnkj on 2017/10/13.
 */

public class KuCunCountBean extends BaseObjectEntity<List<KuCunCountBean.data>> implements Serializable {

    //    {"errorCode" : 0,"errorMsg" : "success","dataList" :[{"FSuserId":"1",
// "FProductEnterprise":"南京日升昌生物技术有限公司","FTyName":"恩诺沙星粉（水产用）"
// ,"FGuige":"5%","FProductName":"","FSm1":"201607120049100007470711",
// "Yplx":"处方药","YpArea":"尽可能","FGysmc":"22",
// "FScph":"2016101801","FStoreHj":"吞没","FDljg":"不",
// "FNmcode":"56885","FPzwh":"兽药字（2012）100589106","FKcsl":"1",
// "FYxqDate":"2018/10/18 0:00:00","FDw":"盒",
// "FDjPrice":"56.00","FSjPrice":"56.00","FDljg1":"不"}]}
    private List<KuCunCountBean.data> dataList;

    public List<KuCunCountBean.data> getDataList() {
        return dataList;
    }

    public void setDataList(List<KuCunCountBean.data> dataList) {
        this.dataList = dataList;
    }

    public class data {
        private String FSuserId;
        private String FProductEnterprise;
        private String FTyName;
        private String FGuige;
        private String FProductName;
        private String FSm1;
        private String Yplx;
        private String YpArea;
        private String FGysmc;
        private String FScph;
        private String FStoreHj;
        private String FDljg;
        private String FNmcode;
        private String FPzwh;
        private String FKcsl;
        private String FYxqDate;
        private String FDw;
        private String FDjPrice;
        private String FSjPrice;
        private String FDljg1;

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
