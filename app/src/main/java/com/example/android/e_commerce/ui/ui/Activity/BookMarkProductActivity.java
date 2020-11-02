package com.example.android.e_commerce.ui.ui.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.databinding.ActivityBookmarkProductBinding;
import com.example.android.e_commerce.ui.ViewModel.ProductViewModel;
import com.squareup.picasso.Picasso;

public class BookMarkProductActivity extends AppCompatActivity {
    ActivityBookmarkProductBinding mBinding;
    ProductViewModel viewModel;
    public  String productName;
    public  String productPrice;
    public  String productImage;
    public  String productTitle;
    public  String productSize;
    boolean checked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bookmark_product);
        getSupportActionBar().hide();

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        Intent i = getIntent();
        productName = i.getStringExtra("productName");
        productPrice = i.getStringExtra("productPrice");
        productImage = i.getStringExtra("productImage");
        productTitle = i.getStringExtra("productTitle");


        mBinding.productNameBookMark.setText(productName);
        mBinding.productPriceBookMark.setText(productPrice);
        mBinding.productTitleBookMark.setText(productTitle);
        Picasso.with(this)
                .load(productImage)
                .into(mBinding.productImageBookMark);

        mBinding.backButtonBookMark.setOnClickListener(view -> onBackPressed());

        checkSize(i);
        mBinding.productSizeSmallBookMark.setOnClickListener(view -> {
            checked = true;
            i.putExtra("productSize", "Small");
            productSize = i.getStringExtra("productSize");

            mBinding.productSizeSmallBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeSmallBookMark.setTextColor(ColorStateList.valueOf(Color.WHITE));

            mBinding.productSizeMediumBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMediumBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));

            mBinding.productSizeLargeBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeLargeBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));


        });
        mBinding.productSizeMediumBookMark.setOnClickListener(view -> {
            checked = true;
            i.putExtra("productSize", "Medium");
            productSize = i.getStringExtra("productSize");

            mBinding.productSizeSmallBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeSmallBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));

            mBinding.productSizeMediumBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeMediumBookMark.setTextColor(ColorStateList.valueOf(Color.WHITE));

            mBinding.productSizeLargeBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeLargeBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));
        });
        mBinding.productSizeLargeBookMark.setOnClickListener(view -> {
            checked = true;
            i.putExtra("productSize", "Large");
            productSize = i.getStringExtra("productSize");

            mBinding.productSizeSmallBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeSmallBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));

            mBinding.productSizeMediumBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMediumBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));

            mBinding.productSizeLargeBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeLargeBookMark.setTextColor(ColorStateList.valueOf(Color.WHITE));

        });

        mBinding.addToCardBookMark.setOnClickListener(view -> { alertDialog();  });

        mBinding.cardButtonBookMark.setOnClickListener(view -> startActivity(new Intent(this, CardActivity.class)));

        viewModel.getCardSize().observe(this,integer -> mBinding.cardButtonBookMark.setText(String.valueOf(integer)));

        viewModel.getCardSize().observe(this, integer -> {
            if (integer > 0){
                mBinding.cardButtonBookMark.setText(String.valueOf(integer));
                mBinding.cardButtonBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

            }
        });

    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        builder.setTitle("Add Card ");
        builder.setMessage("Are You Sure You Want Add it To Card?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {startActivity(new Intent(this,CardActivity.class)) ;  viewModel.setCardV();});
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void checkSize(Intent i){
        if (!checked) {
            i.putExtra("productSize", "small");
            productSize = i.getStringExtra("productSize");

            mBinding.productSizeSmallBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeSmallBookMark.setTextColor(ColorStateList.valueOf(Color.WHITE));

            mBinding.productSizeMediumBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMediumBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));

            mBinding.productSizeLargeBookMark.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeLargeBookMark.setTextColor(ColorStateList.valueOf(Color.BLACK));
        }
    }

}
