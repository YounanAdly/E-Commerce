package com.example.android.e_commerce.ui.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.databinding.FragmentLogoutBinding;
import com.example.android.e_commerce.ui.ui.Activity.LoginActivity;
import com.example.android.e_commerce.ui.ui.Activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class LogoutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLogoutBinding mBinding = FragmentLogoutBinding.inflate(inflater, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder builder =
                new AlertDialog.Builder(requireActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        builder.setTitle("Logout");
        builder.setMessage("Are You Sure You Want To Logout");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            mAuth.signOut();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
        });

        builder.setNegativeButton
                ("Cancel", (dialogInterface, i) -> startActivity(new Intent(requireActivity(), MainActivity.class)));
        builder.show();

        return mBinding.getRoot();
    }

}
