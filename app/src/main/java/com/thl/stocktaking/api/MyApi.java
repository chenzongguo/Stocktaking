package com.thl.stocktaking.api;


import com.thl.stocktaking.model.response.GetTokenResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @创建者 CSDN_LQR
 * @描述 server端api
 */

public interface MyApi {

    public static final String BASE_URL = "http://192.168.20.156:8086/";

    //检查手机是否被注册
//    @POST("user/check_phone_available")
//    Observable<CheckPhoneResponse> checkPhoneAvailable(@Body RequestBody body);

    //获取 token 前置条件需要登录   502 坏的网关 测试环境用户已达上限
    @POST("YDJM/token/getTokens/")
    Observable<GetTokenResponse> getTokens(@Body RequestBody jsonString);

//    public static final String BASE_URL = "http://11.1.1.37/";
//
//    @POST("api/verify_ticket/")
//    Observable<GetTokenResponse> getTokens(@Body RequestBody jsonString);

}
