package com.crypto.api;

import com.crypto.model.CryptoResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {
    @GET("24hr")
    Call<List<CryptoResponse>> cryptoList();

    @FormUrlEncoded
    @POST("24hr")
    Call<CryptoResponse> getDetails(@FieldMap Map<String, String> param);

}
