package com.example.mnkj.newobject.Bean;

import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Base.BaseObjectEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mnkj on 2017/9/27.
 */

public class GYSBean extends BaseObjectEntity<GYSBean> {
    private List<DataList> dataList;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    /**
     * "Gysmc": "四川巴尔动物药业有限公司",
     * "Xgzsh": "（2016）22134",
     * "LxrName": "张三",
     * "Lxrdh": "132555555555",
     * "SyGmp": "92016）22008"
     */
    public class DataList implements Serializable {
        private String Gysmc;//供应商名
        private String Xgzsh;//许可证
        private String LxrName;//姓名
        private String Lxrdh;//电话
        private String SyGmp;//gmp证书号
        private String FStId;//唯一id

        public String getFStId() {
            return FStId;
        }

        public void setFStId(String FStId) {
            this.FStId = FStId;
        }

        public String getSyGmp() {
            return SyGmp;
        }

        public void setSyGmp(String syGmp) {
            SyGmp = syGmp;
        }

        public String getGysmc() {
            return Gysmc;
        }

        public void setGysmc(String gysmc) {
            Gysmc = gysmc;
        }

        public String getXgzsh() {
            return Xgzsh;
        }

        public void setXgzsh(String xgzsh) {
            Xgzsh = xgzsh;
        }

        public String getLxrName() {
            return LxrName;
        }

        public void setLxrName(String lxrName) {
            LxrName = lxrName;
        }

        public String getLxrdh() {
            return Lxrdh;
        }

        public void setLxrdh(String lxrdh) {
            Lxrdh = lxrdh;
        }


    }
}
