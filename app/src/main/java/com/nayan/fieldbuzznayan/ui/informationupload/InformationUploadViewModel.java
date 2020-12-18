package com.nayan.fieldbuzznayan.ui.informationupload;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nayan.fieldbuzznayan.ui.informationupload.model.CvFile;
import com.nayan.fieldbuzznayan.ui.informationupload.model.ErrorResponse;
import com.nayan.fieldbuzznayan.ui.informationupload.model.InformationAddedResponse;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InformationUploadViewModel extends ViewModel {
    InformationUploadRepo repo = new InformationUploadRepo();

    public void uploadUserData(String token, String name, String email, String phone, String fullAddress, String universityName,
                               int gradYear, float cgpa, int experienceInMonth, String currentWorkPlaceName, String applyingIn,
                               int expectedSalary, String fieldBuzzReference, String projectUrl){
        HashMap<String, Object> userDataMap = new HashMap<>();
        HashMap<String, String> cvMap = new HashMap<>();
        userDataMap.put("tsync_id", UUID.randomUUID().toString());
        userDataMap.put("name", name);
        userDataMap.put("email", email);
        userDataMap.put("phone", phone);
        userDataMap.put("full_address", fullAddress);
        userDataMap.put("name_of_university", universityName);
        userDataMap.put("graduation_year", gradYear);
        userDataMap.put("cgpa", cgpa);
        userDataMap.put("experience_in_months", experienceInMonth);
        userDataMap.put("current_work_place_name", currentWorkPlaceName);
        userDataMap.put("applying_in", applyingIn);
        userDataMap.put("expected_salary", expectedSalary);
        userDataMap.put("field_buzz_reference", fieldBuzzReference);
        userDataMap.put("github_project_url", projectUrl);
        cvMap.put("tsync_id", UUID.randomUUID().toString());
        userDataMap.put("cv_file", cvMap);
        repo.uploadUserInformation(token, userDataMap);
    }

    public LiveData<InformationAddedResponse> informationAddedResponseLiveDataObserve(){
        return repo.informationAddedResponseSuccessMutableLiveData;
    }

    public LiveData<ErrorResponse> errorResponseLiveObserve (){
        return repo.errorResponseLiveData;
    }

    public void uploadCvFile(String token, int fileId, File file){
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("*/*"));
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        repo.uploadCvFile(token, fileId, fileToUpload);
    }

    public  LiveData<CvFile> cvFileUploadLiveDataObserve (){
        return repo.cvFileMutableLiveData;
    }
}
