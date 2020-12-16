package com.khusiexpress.fieldbuzznayan.network;

import com.khusiexpress.fieldbuzznayan.ui.informationupload.model.CvFile;
import com.khusiexpress.fieldbuzznayan.ui.informationupload.model.InformationAddedResponse;
import com.khusiexpress.fieldbuzznayan.ui.login.model.LoginResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("api/login/")
    Call<LoginResponse> loginUser(@Body HashMap<String, String> body);

    @POST("api/v0/recruiting-entities/")
    Call<InformationAddedResponse> uploadUserInformation(@Header("Authorization") String token, @Body HashMap<String, Object> fields);

    @Multipart
    @PUT("api/file-object/{FILE_TOKEN_ID}/")
    Call<CvFile> uploadUserCvFile(@Header("Authorization") String token, @Path("FILE_TOKEN_ID") String fileId, @Part MultipartBody.Part files);

}
