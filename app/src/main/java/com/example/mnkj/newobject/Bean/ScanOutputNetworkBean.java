package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseObjectEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mnkj on 2017/10/11.
 */

public class ScanOutputNetworkBean extends BaseObjectEntity<List<ScanOutputNetworkBean.data>> implements Serializable {
    //  "FStId": "614",
//          "FSm1": "201607120049100007470711",
//          "FJbr": "",
//          "FTid": "",
//          "FGrDate": "2017/10/11 11:15:32",
//          "FCodeID": "2017101111153284",
//          "FDljg": "",
//          "FProductEnterprise": "南京日升昌生物技术有限公司",
//          "FTyName": "恩诺沙星粉（水产用）",
//          "FGuige": "5%",
//          "FProductName": "",
//          "FPzwh": "",
//          "FYxqDate": "2018/10/18 0:00:00",
//          "FBuyNum": "0",
//          "FDw": "盒",
//          "FCgdj": "0.00",
//          "FHdjg": "0.00",
//          "FJesum": "0.00",
//          "FNmcode": "",
//          "FStoreHj": "",
//          "Remark": "",
//          "FStatus": "进货",
//          "mytId": "0",
//          "IsUpload": "0",
//          "Yplx": "处方药",
//          "FGysmc": "11",
//          "FXgzsh": "",
//          "FLxr": "11",
//          "FLxrdh": "11",
//          "FScph": "2016101801",
//          "FScDate": "2016/10/18 0:00:00",
//          "YpArea": "",
//          "SyGmp": "11"


    private List<data> dataList;

    public List<ScanOutputNetworkBean.data> getDataList() {
        return dataList;
    }

    public void setDataList(List<ScanOutputNetworkBean.data> dataList) {
        this.dataList = dataList;
    }


    public class data implements Serializable {

        private Boolean beChecked = false;//cb
        private Boolean bemodfiyed = false;//判断item折叠

        private String mytId;
        private String FProductEnterprise;//生产企业名称
        private String FBuyNum;//销售数量
        private String FTyName;//通用名称
        private String FGuige;//规格
        private String FProductName;//商品名称
        private String FPzwh;//批准文号
        private String FYxqDate;//有效期
        private String FDw;//单位
        private String FHdjg;//销售单价
        private String mytId1;
        private String Yplx;//商品类型
        private String FGysmc;//供应商名称
        private String FXgzsh;//许可证
        private String FDljg;//代理机构
        private String FScph;//生产批号
        private String FScDate;//生产日期
        private String FSm1;//追溯码

        private String FJesum;//金额总计

        private String FNmcode;//内码

        private String FStoreHj;//存储环境

        private String FStId;//关联id

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }


        public String getFStoreHj() {
            return FStoreHj;
        }

        public void setFStoreHj(String FStoreHj) {
            this.FStoreHj = FStoreHj;
        }

        public String getFNmcode() {
            return FNmcode;
        }

        public void setFNmcode(String FNmcode) {
            this.FNmcode = FNmcode;
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

        public Boolean getBeChecked() {
            return beChecked;
        }

        public void setBeChecked(Boolean beChecked) {
            this.beChecked = beChecked;
        }

        public Boolean getBemodfiyed() {
            return bemodfiyed;
        }

        public void setBemodfiyed(Boolean bemodfiyed) {
            this.bemodfiyed = bemodfiyed;
        }

        public String getMytId() {
            return mytId;
        }

        public void setMytId(String mytId) {
            this.mytId = mytId;
        }

        public String getFProductEnterprise() {
            return FProductEnterprise;
        }

        public void setFProductEnterprise(String FProductEnterprise) {
            this.FProductEnterprise = FProductEnterprise;
        }

        public String getFBuyNum() {
            return FBuyNum;
        }

        public void setFBuyNum(String FBuyNum) {
            this.FBuyNum = FBuyNum;
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

        public String getFDw() {
            return FDw;
        }

        public void setFDw(String FDw) {
            this.FDw = FDw;
        }

        public String getFHdjg() {
            return FHdjg;
        }

        public void setFHdjg(String FHdjg) {
            this.FHdjg = FHdjg;
        }

        public String getMytId1() {
            return mytId1;
        }

        public void setMytId1(String mytId1) {
            this.mytId1 = mytId1;
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

        public String getFSm1() {
            return FSm1;
        }

        public void setFSm1(String FSm1) {
            this.FSm1 = FSm1;
        }

    }

}
