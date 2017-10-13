package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseObjectEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mnkj on 2017/10/10.
 */
//通过批准文号扫描返回的bean
public class PZWHScanBean extends BaseObjectEntity<List<PZWHScanBean.DataList>> {
    List<DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> DataList) {
        this.dataList = DataList;
    }
//    {
//        "errorCode": 0,
//            "errorMsg": "success",
//            "DataList": [
//        {
//            "Dljg": "",
//                "ProductEnterPrise": "南京日升昌生物技术有限公司",
//                "FTyName": "恩诺沙星粉（水产用）",
//                "Guige": "5%",
//                "ProductName": "溶威",
//                "pzwh": "兽药字（2012）100589106",
//                "PzDate": "2012/08/21",
//                "Yplx": "处方药",
//                "tId": "63133"
//        }
//    ]

//    }

    public static class DataList implements Serializable {
        private String Dljg;//无用
        private String ProductEnterPrise;//生产企业名称
        private String FTyName;//通用名称
        private String Guige;//规格
        private String ProductName;//商品名称
        private String PzDate;//生产日期
        private String Yplx;//商品类型
        private String pzwh;//批准文号
        private String tId;//无用

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

