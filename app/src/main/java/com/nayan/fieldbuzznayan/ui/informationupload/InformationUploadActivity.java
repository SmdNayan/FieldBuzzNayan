package com.nayan.fieldbuzznayan.ui.informationupload;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nayan.fieldbuzznayan.R;
import com.nayan.fieldbuzznayan.databinding.ActivityInformationUploadBinding;
import com.nayan.fieldbuzznayan.helper.FileHelper;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class InformationUploadActivity extends AppCompatActivity {
    private ActivityInformationUploadBinding binding;
    private InformationUploadViewModel viewModel;
    private File cvFile = null;
    private String token = "";
    private int cvFileSize =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        token = "Token " + getIntent().getStringExtra("token");
        viewModel = new ViewModelProvider(this).get(InformationUploadViewModel.class);
        clickListener();
        setRoleData();
        viewModel.informationAddedResponseLiveDataObserve().observe(this, observeData->{
            Toast.makeText(this, ""+observeData.getMessage(), Toast.LENGTH_SHORT).show();
            viewModel.uploadCvFile(token, observeData.getCvFile().getId(), cvFile);
        });

        viewModel.errorResponseLiveObserve().observe(this, errorResponse -> Toast.makeText(this, ""+errorResponse.getMessage(), Toast.LENGTH_SHORT).show());

        viewModel.cvFileUploadLiveDataObserve().observe(this, cvFileData -> Toast.makeText(this, ""+cvFileData.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void clickListener(){
        binding.btnUploadCv.setOnClickListener(v-> requestFileReadWritePermission());

        binding.btnSubmit.setOnClickListener(v-> getUserProvidedData());
    }

    private void setRoleData(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.applyingInTl.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data.getData()!=null) {
            Uri uri = data.getData();
            cvFile = new File(FileHelper.getPath(this, uri));
            cvFileSize = (int) ((cvFile.length()/1024));
        }
    }

    private void getUserProvidedData(){
        String name = Objects.requireNonNull(binding.nameEt.getText()).toString();
        String email = Objects.requireNonNull(binding.emailEt.getText()).toString();
        String phone = Objects.requireNonNull(binding.phoneEt.getText()).toString();
        String fullAddress = Objects.requireNonNull(binding.fullAddressEt.getText()).toString();
        String nameOfUniversity = Objects.requireNonNull(binding.universityNameEt.getText()).toString();
        String graduationYear = Objects.requireNonNull(binding.graduationYearEt.getText()).toString();
        String cGpa = Objects.requireNonNull(binding.cgpaEt.getText()).toString();
        String experienceInMonth = Objects.requireNonNull(binding.workingExpEt.getText()).toString();
        String currentWorkPlaceName = Objects.requireNonNull(binding.currentWorkplaceNameEt.getText()).toString();
        String applyingIn = binding.applyingInTl.getSelectedItem().toString();
        String expectedSalary = Objects.requireNonNull(binding.expectedSalaryInEt.getText()).toString();
        String fieldBuzzReference = Objects.requireNonNull(binding.referenceNameEt.getText()).toString();
        String githubProjectUrl = Objects.requireNonNull(binding.githubProjectUrlEt.getText()).toString();

        // Name check
        if (!emptyStringCheck(name, binding.nameTl)){
            return;
        }

        if (!commonInputLengthCheck(name, binding.nameTl)){
            return;
        }

        // email check
        if (!emptyStringCheck(email, binding.emailTl)){
            return;
        }

        if (!commonInputLengthCheck(email, binding.emailTl)){
            return;
        }

        if (!emailValidation(email)){
            binding.emailTl.setError(getString(R.string.email_not_valid));
            return;
        }

        // Phone check
        if (!emptyStringCheck(phone, binding.phoneTl)){
            return;
        }

        if (phone.length()>14){
            binding.phoneEt.setText(R.string.phone_not_valid);
            return;
        }

        // Address check
        if (!emptyStringCheck(fullAddress, binding.fullAddressTl)){
            return;
        }

        if (fullAddress.length()>512){
            binding.fullAddressTl.setError(getString(R.string.address_exit));
            return;
        }

        // University check
        if (!emptyStringCheck(nameOfUniversity, binding.universityNameTl)){
            return;
        }

        if (!commonInputLengthCheck(nameOfUniversity, binding.universityNameTl)){
            return;
        }

        // Graduation year check
        if (!emptyStringCheck(graduationYear, binding.graduationYearTl)){
            return;
        }

        if(!(Integer.parseInt(graduationYear)>2015 && Integer.parseInt(graduationYear)<=2020)){
            binding.graduationYearTl.setError(getString(R.string.grad_year_warning));
            return;
        }

        // Cgpa check
        if (!emptyStringCheck(cGpa, binding.cgpaTl)){
            return;
        }

        if (!(Float.parseFloat(cGpa)>=2.0 && Float.parseFloat(cGpa)<=4.0)){
            binding.cgpaTl.setError(getString(R.string.cgpa_warning));
            return;
        }

        // experience check
        if (!emptyStringCheck(experienceInMonth, binding.workingExpTl)){
            return;
        }

        if (!(Integer.parseInt(experienceInMonth)>=0 && Integer.parseInt(experienceInMonth)<=100)){
            binding.workingExpTl.setError(getString(R.string.experience_warning));
            return;
        }

        // workplace check
        if (!emptyStringCheck(currentWorkPlaceName, binding.currentWorkplaceNameTl)){
            return;
        }

        if (!commonInputLengthCheck(currentWorkPlaceName, binding.currentWorkplaceNameTl)){
            return;
        }

        // Role check
        if (applyingIn.equals("Applying In")){
            Toast.makeText(this, getString(R.string.role_txt_warn), Toast.LENGTH_SHORT).show();
            return;
        }

        // Expected salary check
        if (!emptyStringCheck(expectedSalary, binding.expectedSalaryInTl)){
            return;
        }

        if (!(Integer.parseInt(expectedSalary)>=15000 && Integer.parseInt(expectedSalary)<=60000)){
            binding.expectedSalaryInTl.setError(getString(R.string.expected_salary_error));
            return;
        }

        // reference check
        if (!emptyStringCheck(fieldBuzzReference, binding.referenceNameTl)){
            return;
        }

        if (!commonInputLengthCheck(fieldBuzzReference, binding.referenceNameTl)){
            return;
        }

        // Project URL Check
        if (!emptyStringCheck(githubProjectUrl, binding.githubProjectUrlTl)){
            return;
        }

        if (githubProjectUrl.length()>512){
            binding.githubProjectUrlTl.setError(getString(R.string.project_warning));
            return;
        }

        // CV File check
        if (cvFile==null){
            binding.btnUploadCv.setText(R.string.select_a_cv);
            Toast.makeText(this, getString(R.string.select_cv), Toast.LENGTH_SHORT).show();
            return;
        }

        if (cvFileSize>4096){
            Toast.makeText(this, getString(R.string.cv_limit_txt), Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.uploadUserData(token, name, email, phone, fullAddress, nameOfUniversity, Integer.parseInt(graduationYear), Float.parseFloat(cGpa),
                Integer.parseInt(experienceInMonth), currentWorkPlaceName, applyingIn, Integer.parseInt(expectedSalary), fieldBuzzReference, githubProjectUrl);
    }

    private boolean emptyStringCheck(String checkString, TextInputLayout textInputLayout){
        if (checkString.equals("")){
            textInputLayout.setError(getString(R.string.select_cv_warning));
            return false;
        }
        return true;
    }

    private boolean commonInputLengthCheck(String checkString, TextInputLayout textInputLayout){
        if (checkString.length()>256){
            textInputLayout.setError(getString(R.string.input_warning));
            return false;
        }
        return true;
    }

    private boolean emailValidation(String emailText){
        return Patterns.EMAIL_ADDRESS.matcher(emailText).matches();
    }

    private void getCvFileFromDevice(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent,1);
    }

    private void requestFileReadWritePermission(){
        Dexter.withContext(InformationUploadActivity.this)
            .withPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.areAllPermissionsGranted()){
                        getCvFileFromDevice();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).
            withErrorListener(error -> Toast.makeText(getApplicationContext(), "Permission Error! " + error.toString(), Toast.LENGTH_SHORT).show())
            .check();
    }
}