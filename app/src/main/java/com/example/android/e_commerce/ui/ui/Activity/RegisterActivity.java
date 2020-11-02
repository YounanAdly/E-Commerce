package com.example.android.e_commerce.ui.ui.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.databinding.ActivityRegisterBinding;
import com.example.android.e_commerce.ui.ViewModel.FirebaseAuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding mBinding;
    FirebaseAuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        getSupportActionBar().hide();

        viewModel = ViewModelProviders.of(this).get(FirebaseAuthViewModel.class);

        mBinding.backToLogin.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        mBinding.createAccount.setOnClickListener(view -> {
                    if (!validateFields()) {
                        viewModel.create(
                                mBinding.createEmail.getText().toString().trim(),
                                mBinding.createPassword.getText().toString().trim());
                    }
                });

        viewModel.setAccountCreate().observe(this, aBoolean -> {
            finishAffinity();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
        viewModel.error().observe(this, this::alertDialog);


    }

    private void alertDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        builder.setTitle("Error");
        builder.setMessage(s);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
    private boolean validateFields() {
        boolean error = false;
        if (mBinding.createEmail.getText().toString().isEmpty()) {
            error = true;
            mBinding.createEmail.setError("Please enter your email");
            mBinding.createEmail.requestFocus();
        }

        if (mBinding.createPassword.getText().toString().isEmpty()) {
            error = true;
            mBinding.createPassword.setError("Please enter your Password");
            mBinding.createPassword.requestFocus();
        }
        return error;
    }

}
