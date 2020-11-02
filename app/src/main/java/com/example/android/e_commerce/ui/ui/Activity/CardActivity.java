package com.example.android.e_commerce.ui.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.databinding.ActivityCardBinding;
import com.example.android.e_commerce.ui.Adapter.CardAdapter;
import com.example.android.e_commerce.ui.ViewModel.ProductViewModel;
import com.example.android.e_commerce.ui.data.Product;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {
    ActivityCardBinding   mBinding;
    private List<Product> productList;
    private CardAdapter   mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_card);
        getSupportActionBar().hide();

        ProductViewModel viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productList = new ArrayList<>();

        viewModel.getCard().observe(this, products -> {
            productList.addAll(products);
            mAdapter.notifyDataSetChanged();
        });

        viewModel.getTotal().observe(this, integer -> mBinding.totalPrice.setText("$" + String.valueOf(integer)));

        viewModel.getCardSize().observe(this, integer -> {
            if (integer > 0) {
                mBinding.buttonCard.setText(String.valueOf(integer));
                mBinding.buttonCard.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

            }
        });

        mBinding.backButtonCard.setOnClickListener(view -> onBackPressed());


        setupRecyclerView();
    }


    private void setupRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new CardAdapter(this, LayoutInflater.from(this), productList);
            mBinding.cardRecycler.setLayoutManager(new LinearLayoutManager(this));
            mBinding.cardRecycler.setAdapter(mAdapter);
        }
    }
}
