package com.nayan.fieldbuzznayan.ui.informationupload;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.nayan.fieldbuzznayan.network.RetrofitClient;
import com.nayan.fieldbuzznayan.ui.informationupload.model.CvFile;
import com.nayan.fieldbuzznayan.ui.informationupload.model.ErrorResponse;
import com.nayan.fieldbuzznayan.ui.informationupload.model.InformationAddedResponse;

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
                    informationAddedResponseSuccessMutableLiveData.postValue(response.body());
                } else {
                    ErrorResponse errorData = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    errorResponseLiveData.postValue(errorData);
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
                } else {
                    ErrorResponse errorData = new Gson().fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    errorResponseLiveData.postValue(errorData);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CvFile> call, @NotNull Throwable t) {
                Log.e("SMD", "onFailure1: " + t.getLocalizedMessage() );
            }
        });
    }

}
