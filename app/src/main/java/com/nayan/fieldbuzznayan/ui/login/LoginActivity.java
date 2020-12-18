package com.nayan.fieldbuzznayan.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nayan.fieldbuzznayan.databinding.ActivityLoginBinding;
import com.nayan.fieldbuzznayan.ui.informationupload.InformationUploadActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.observeLoginSuccessResponse().observe(this, loginResponse->{
            Intent intent = new Intent(LoginActivity.this, InformationUploadActivity.class);
            intent.putExtra("token", loginResponse.getToken());
            startActivity(intent);
        });

        viewModel.observeLoginErrorLiveData().observe(this, error-> Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show());

        initClickListener();
    }

    private void initClickListener(){
        binding.btnLogin.setOnClickListener(v-> loginUser());
    }

    private void loginUser(){
        String userName = Objects.requireNonNull(binding.userNameEt.getText()).toString();
        String password = Objects.requireNonNull(binding.userPasswordEt.getText()).toString();

        if (userName.equals("")){
            binding.userNameTl.setError("Username is required");
            return;
        }

        if (password.equals("")){
            binding.userPasswordTl.setError("Password is required");
            return;
        }
        viewModel.userLogin(userName, password);
    }
}