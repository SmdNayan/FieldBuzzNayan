package com.khusiexpress.fieldbuzznayan.ui.informationupload;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.khusiexpress.fieldbuzznayan.network.RetrofitClient;
import com.khusiexpress.fieldbuzznayan.ui.informationupload.model.CvFile;
import com.khusiexpress.fieldbuzznayan.ui.informationupload.model.ErrorResponse;
import com.khusiexpress.fieldbuzznayan.ui.informationupload.model.InformationAddedResponse;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationUploadRepo {
    public MutableLiveData<InformationAddedResponse> informationAddedResponseSuccessMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ErrorResponse> errorResponseLiveData = new MutableLiveData<>();
    public void uploadUserInformation(String token, HashMap<String, Object> fields){
        RetrofitClient.getInstance().getApi().uploadUserInformation(token, fields).enqueue(new Callback<InformationAddedResponse>() {
            @Override
            public void onResponse(@NotNull Call<InformationAddedResponse> call, @NotNull Response<InformationAddedResponse> response) {
                if (response.isSuccessful()){
                    Log.e("SMD", "onResponse: 1" );
                    informationAddedResponseSuccessMutableLiveData.postValue(response.body());
                } else {
                    ErrorResponse errorData = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    errorResponseLiveData.postValue(errorData);
                    Log.e("SMD", "onResponse: 0" );
                }
            }

            @Override
            public void onFailure(@NotNull Call<InformationAddedResponse> call, @NotNull Throwable t) {
                Log.e("SMD", "onFailure: " + t.getLocalizedMessage() );
            }
        });
    }

    public MutableLiveData<CvFile> cvFileMutableLiveData = new MutableLiveData<>();
    public void uploadCvFile(String token, int fileId, MultipartBody.Part file){
        RetrofitClient.getInstance().getApi().uploadUserCvFile(token, String.valueOf(fileId), file).enqueue(new Callback<CvFile>() {
            @Override
            public void onResponse(@NotNull Call<CvFile> call, @NotNull Response<CvFile> response) {
                if (response.isSuccessful()){
                    cvFileMutableLiveData.postValue(response.body());
                    Log.e("SMD", "onResponse: 2" );
                } else {
                    ErrorResponse errorData = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    errorResponseLiveData.postValue(errorData);
                    Log.e("SMD", "onResponse: 3" );
                }
            }

            @Override
            public void onFailure(@NotNull Call<CvFile> call, @NotNull Throwable t) {
                Log.e("SMD", "onFailure1: " + t.getLocalizedMessage() );
            }
        });
    }

}
