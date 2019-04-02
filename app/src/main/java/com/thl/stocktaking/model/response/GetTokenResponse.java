package com.thl.stocktaking.model.response;

public class  GetTokenResponse {

    private String statue;

    private String msg;

    private  ResultData resultData;

    public String getStatue() {
        return statue;
    }

    public String getMsg() {
        return msg;
    }

    public ResultData getResultData() {
        return resultData;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }


    public static class ResultData{
        private String sessinId;
        private String socketIp;
        private String socketPort;

        public String getSessinId() {
            return sessinId;
        }

        public void setSessinId(String sessinId) {
            this.sessinId = sessinId;
        }

        public String getSocketIp() {
            return socketIp;
        }

        public void setSocketIp(String socketIp) {
            this.socketIp = socketIp;
        }

        public String getSocketPort() {
            return socketPort;
        }

        public void setSocketPort(String socketPort) {
            this.socketPort = socketPort;
        }

    }

}
