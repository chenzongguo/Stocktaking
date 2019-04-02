package com.thl.stocktaking.model.request;

public class GetTokenRequest {

    private String userId;
    private String appId;
    private String appKey;
    private String perCode;
    private String compeCode;

    public GetTokenRequest(String userId, String appId, String appKey,
            String perCode, String compeCode) {
        this.userId = userId;
        this.appId = appId;
        this.appKey = appKey;
        this.perCode = perCode;
        this.compeCode = compeCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPerCode() {
        return perCode;
    }

    public void setPerCode(String perCode) {
        this.perCode = perCode;
    }

    public String getCompeCode() {
        return compeCode;
    }

    public void setCompeCode(String compeCode) {
        this.compeCode = compeCode;
    }

}
