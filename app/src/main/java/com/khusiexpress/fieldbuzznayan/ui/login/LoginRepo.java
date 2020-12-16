package com.khusiexpress.fieldbuzznayan.ui.login;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.khusiexpress.fieldbuzznayan.network.RetrofitClient;
import com.khusiexpress.fieldbuzznayan.ui.login.model.LoginResponse;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {
    public MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> loginResponseErrorMutableLiveData = new MutableLiveData<>();
    public void loginUser(HashMap<String, String> userCredentials){
        RetrofitClient.getInstance().getApi().loginUser(userCredentials).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    loginResponseMutableLiveData.postValue(response.body());
                } else {
                    loginResponseErrorMutableLiveData.postValue("Something went wrong!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {

            }
        });
    }
}
