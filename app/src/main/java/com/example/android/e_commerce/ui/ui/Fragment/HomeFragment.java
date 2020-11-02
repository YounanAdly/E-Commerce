package com.example.android.e_commerce.ui.ui.Fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e_commerce.databinding.FragmentHomeBinding;
import com.example.android.e_commerce.ui.Adapter.PaginationScrollListener;
import com.example.android.e_commerce.ui.Adapter.ProductAdapter;
import com.example.android.e_commerce.ui.FirebaseClient.FireStoreClient;
import com.example.android.e_commerce.ui.ViewModel.ProductViewModel;
import com.example.android.e_commerce.ui.data.Product;
import com.example.android.e_commerce.ui.ui.Activity.CardActivity;
import com.example.android.e_commerce.ui.ui.Activity.ProductActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductListener {
    private ProductAdapter mAdapter;
    private List<Product> productList;
    private FragmentHomeBinding mBinding;
    private ProductViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        productList = new ArrayList<>();
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        FireStoreClient fireStoreClient = new FireStoreClient();
        fireStoreClient.setFirebaseFirestore();

        viewModel.getProducts2().observe(requireActivity(), products -> {
            if (products.size() == 0 || products.size() < ProductViewModel.productsPageSize) {
                mAdapter.setHasMore(false);
            }
            productList.addAll(products);
            mAdapter.notifyDataSetChanged();

        });

        mBinding.cardHomeButton.setOnClickListener(view -> startActivity(new Intent(getActivity(), CardActivity.class)));

        getCardSize();
        getProduct();
        setupRecyclerView();
        return mBinding.getRoot();
    }

    private void setupRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new ProductAdapter(getActivity(), LayoutInflater.from(getActivity()), productList, this);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

            mBinding.itemsRecycler.setItemAnimator(new DefaultItemAnimator());
            mBinding.itemsRecycler.setLayoutManager(mLayoutManager);


            mBinding.itemsRecycler.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore() {
                    // pagination. fetch new data
                    getProduct();
                }
            });
            mBinding.itemsRecycler.setAdapter(mAdapter);
        }
    }

    private void getProduct() {
        viewModel.getProducts();
    }

    private void getCardSize() {
        viewModel.getCardSize().observe(requireActivity(), integer -> {
            if (integer > 0) {
                mBinding.cardHomeButton.setText(String.valueOf(integer));
                mBinding.cardHomeButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            }
        });
    }


    @Override
    public void onProductClick(int position) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("productName", productList.get(position).getName());
        intent.putExtra("productPrice", productList.get(position).getPrice());
        intent.putExtra("productImage", productList.get(position).getImage());
        intent.putExtra("productTitle", productList.get(position).getTitle());
        intent.putExtra("productPriceCard", productList.get(position).getPriceCard());
        startActivity(intent);
    }

}
