package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Base.BaseObjectEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mnkj on 2017/9/27.
 */
//客户bean
public class KHBean extends BaseObjectEntity<KHBean> {
    private List<DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    /**
     * "dataList": [
     * {
     * "FStId": "16",
     * "FBuyName": "柳州",
     * "FBuyTel": "135624921",
     * "FBuyAddress": "上海",
     * "FGmyzc": "上海",
     * "FGmyzcd": "上海"
     * },
     */
    public class DataList implements Serializable {
        private String FBuyName;//购买人名
        private String FBuyAddress;//地址
        private String FGmyzc;//规模养殖场
        private String FBuyTel;//电话
        private String FGmyzcd;//规模养殖场地
        private String FStId;//唯一id


        public String getFBuyName() {
            return FBuyName;
        }

        public void setFBuyName(String FBuyName) {
            this.FBuyName = FBuyName;
        }

        public String getFBuyAddress() {
            return FBuyAddress;
        }

        public void setFBuyAddress(String FBuyAddress) {
            this.FBuyAddress = FBuyAddress;
        }

        public String getFGmyzc() {
            return FGmyzc;
        }

        public void setFGmyzc(String FGmyzc) {
            this.FGmyzc = FGmyzc;
        }

        public String getFBuyTel() {
            return FBuyTel;
        }

        public void setFBuyTel(String FBuyTel) {
            this.FBuyTel = FBuyTel;
        }

        public String getFGmyzcd() {
            return FGmyzcd;
        }

        public void setFGmyzcd(String FGmyzcd) {
            this.FGmyzcd = FGmyzcd;
        }

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }
    }
}
