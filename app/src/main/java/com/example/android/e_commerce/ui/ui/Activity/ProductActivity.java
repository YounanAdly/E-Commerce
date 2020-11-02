package com.example.android.e_commerce.ui.ui.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.databinding.ActivityProductBinding;
import com.example.android.e_commerce.ui.Adapter.SliderImageAdapter;
import com.example.android.e_commerce.ui.ViewModel.ProductViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class ProductActivity extends AppCompatActivity {
    ActivityProductBinding mBinding;
    ProductViewModel viewModel;
    SliderImageAdapter adapter;
    SliderView sliderView;
    public static String productName;
    public static String productPrice;
    public static String productImage;
    public static String productTitle;
    public static String productSize;
    public static double productPriceCard;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        mBinding            = DataBindingUtil.setContentView(this, R.layout.activity_product);
        viewModel           = ViewModelProviders.of(this).get(ProductViewModel.class);

        Intent i            = getIntent();
        productName         = i.getStringExtra("productName");
        productPrice        = i.getStringExtra("productPrice");
        productImage        = i.getStringExtra("productImage");
        productTitle        = i.getStringExtra("productTitle");
        productPriceCard    = i.getDoubleExtra("productPriceCard", 0);


        mBinding.productName .setText(productName);
        mBinding.productPrice.setText(productPrice);
        mBinding.productTitle.setText(productTitle);
        imageSlider();


        mBinding.addToBookmark.setOnClickListener(view -> alertDialog("BookMark", MainActivity.class));
        mBinding.backButton.setOnClickListener   (view -> onBackPressed());

        checkSize(i);
        mBinding.productSizeSmall.setOnClickListener(view -> {
            checked = true;
            i.putExtra("productSize", "Small");
            productSize = i.getStringExtra("productSize");
            Log.d("TEST", "onCreate: " + productSize);

            mBinding.productSizeSmall .setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeSmall .setTextColor(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMedium.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMedium.setTextColor(ColorStateList.valueOf(Color.BLACK));
            mBinding.productSizeLarge .setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeLarge .setTextColor(ColorStateList.valueOf(Color.BLACK));


        });
        mBinding.productSizeMedium.setOnClickListener(view -> {
            checked = true;
            i.putExtra("productSize", "Medium");
            productSize = i.getStringExtra("productSize");

            mBinding.productSizeSmall .setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeSmall .setTextColor(ColorStateList.valueOf(Color.BLACK));
            mBinding.productSizeMedium.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeMedium.setTextColor(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeLarge .setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeLarge .setTextColor(ColorStateList.valueOf(Color.BLACK));
        });
        mBinding.productSizeLarge.setOnClickListener(view -> {
            checked = true;
            i.putExtra("productSize", "Large");
            productSize = i.getStringExtra("productSize");

            mBinding.productSizeSmall .setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeSmall .setTextColor(ColorStateList.valueOf(Color.BLACK));
            mBinding.productSizeMedium.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMedium.setTextColor(ColorStateList.valueOf(Color.BLACK));
            mBinding.productSizeLarge .setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeLarge .setTextColor(ColorStateList.valueOf(Color.WHITE));

        });

        mBinding.addToCard.setOnClickListener(view -> alertDialog("Card", CardActivity.class));

        mBinding.cardButton.setOnClickListener(view -> startActivity(new Intent(this, CardActivity.class)));

        viewModel.getCardSize().observe(this, integer -> {
            if (integer > 0) {
                mBinding.cardButton.setText(String.valueOf(integer));
                mBinding.cardButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

            }
        });
    }

    private void alertDialog(String action, Class c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.Theme_MaterialComponents_Light_Dialog_Alert);
        builder.setTitle("Add To " + action);
        builder.setMessage("Are You Sure You Want Add it To " + action + "?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            startActivity(new Intent(this, c));
            if (action.equals("BookMark")) {
                viewModel.setBookMarkV();
            }
            if (action.equals("Card")) {
                viewModel.setCardV();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void checkSize(Intent i) {
        if (!checked) {
            i.putExtra("productSize", "small");
            productSize = i.getStringExtra("productSize");

            mBinding.productSizeSmall .setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            mBinding.productSizeSmall .setTextColor(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMedium.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeMedium.setTextColor(ColorStateList.valueOf(Color.BLACK));
            mBinding.productSizeLarge .setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            mBinding.productSizeLarge .setTextColor(ColorStateList.valueOf(Color.BLACK));
        }
    }


    private void imageSlider(){
        sliderView = findViewById(R.id.productImage);
        adapter = new SliderImageAdapter(this);
        adapter.addItem(productImage);
        adapter.addItem(productImage);
        adapter.addItem(productImage);
        adapter.addItem(productImage);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.BLACK);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }


}
