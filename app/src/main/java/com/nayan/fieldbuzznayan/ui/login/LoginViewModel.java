package com.nayan.fieldbuzznayan.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nayan.fieldbuzznayan.ui.login.model.LoginResponse;

import java.util.HashMap;

public class LoginViewModel extends ViewModel {
    LoginRepo repo = new LoginRepo();

    public void userLogin(String userName, String password){
        HashMap<String, String> userCredentials = new HashMap<>();
        userCredentials.put("username", userName);
        userCredentials.put("password", password);
        repo.loginUser(userCredentials);
    }

    public LiveData<LoginResponse> observeLoginSuccessResponse(){
        return repo.loginResponseMutableLiveData;
    }

    public LiveData<String> observeLoginErrorLiveData(){
        return repo.loginResponseErrorMutableLiveData;
    }

}
