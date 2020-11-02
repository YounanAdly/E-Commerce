package com.example.android.e_commerce.ui.ui.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.databinding.ActivityLoginBinding;
import com.example.android.e_commerce.ui.ViewModel.FirebaseAuthViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding mBinding;
    FirebaseAuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getSupportActionBar().hide();

        viewModel = ViewModelProviders.of(this).get(FirebaseAuthViewModel.class);

        mBinding.loginAccount.setOnClickListener(view -> {
            if (!validateFields()) {
                viewModel.login(
                        mBinding.loginEmail.getText().toString().trim(),
                        mBinding.loginPassword.getText().toString().trim());
            }
        });

        viewModel.getLogin().observe(this, aBoolean -> {
            finishAffinity();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });

        viewModel.error().observe(this, this::alertDialog);

        mBinding.createNewAccount.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
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
        if (mBinding.loginEmail.getText().toString().isEmpty()) {
            error = true;
            mBinding.loginEmail.setError("Please enter your email");
            mBinding.loginEmail.requestFocus();
        }

        if (mBinding.loginPassword.getText().toString().isEmpty()) {
            error = true;
            mBinding.loginPassword.setError("Please enter your Password");
            mBinding.loginPassword.requestFocus();
        }
        return error;
    }
}
