package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;

import java.util.List;

/**
 * Created by mnkj on 2017/9/15.
 */
//商品信息
public class GoodsInfoBean extends BaseMsg {
    private List<DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    public class DataList {
        private String Dljg;
        private String ProductEnterPrise;
        private String FTyName;
        private String Guige;
        private String ProductName;
        private String pzwh;
        private String PzDate;
        private String Yplx;
        private String tId;

        public String getDljg() {
            return Dljg;
        }

        public void setDljg(String dljg) {
            Dljg = dljg;
        }

        public String getProductEnterPrise() {
            return ProductEnterPrise;
        }

        public void setProductEnterPrise(String productEnterPrise) {
            ProductEnterPrise = productEnterPrise;
        }

        public String getFTyName() {
            return FTyName;
        }

        public void setFTyName(String FTyName) {
            this.FTyName = FTyName;
        }

        public String getGuige() {
            return Guige;
        }

        public void setGuige(String guige) {
            Guige = guige;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public String getPzwh() {
            return pzwh;
        }

        public void setPzwh(String pzwh) {
            this.pzwh = pzwh;
        }

        public String getPzDate() {
            return PzDate;
        }

        public void setPzDate(String pzDate) {
            PzDate = pzDate;
        }

        public String getYplx() {
            return Yplx;
        }

        public void setYplx(String yplx) {
            Yplx = yplx;
        }

        public String gettId() {
            return tId;
        }

        public void settId(String tId) {
            this.tId = tId;
        }
    }
}
