package com.example.android.e_commerce.ui.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e_commerce.databinding.FragmentBookmarkBinding;
import com.example.android.e_commerce.databinding.FragmentSearchBinding;
import com.example.android.e_commerce.ui.Adapter.ProductAdapter;
import com.example.android.e_commerce.ui.ViewModel.ProductViewModel;
import com.example.android.e_commerce.ui.data.Product;
import com.example.android.e_commerce.ui.ui.Activity.BookMarkProductActivity;
import com.example.android.e_commerce.ui.ui.Activity.ProductActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class BookMarkFragment extends Fragment implements ProductAdapter.OnProductListener {
    private FragmentBookmarkBinding mBinding;
    private ProductAdapter mAdapter;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentBookmarkBinding.inflate(inflater, container, false);

        productList = new ArrayList<>();

        ProductViewModel viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        viewModel.getBookMark().observe(requireActivity(), products -> {
            if (products.size() == 0 || products.size() < ProductViewModel.productsPageSize) {
                mAdapter.setHasMore(false);
            }
            productList.addAll(products);
            mAdapter.notifyDataSetChanged();
        });

        setupRecyclerView();
        return mBinding.getRoot();
    }

    private void setupRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new ProductAdapter(getActivity(), LayoutInflater.from(getActivity()), productList, this);
            mBinding.bookMarkRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBinding.bookMarkRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void onProductClick(int position) {
        Intent intent = new Intent(getActivity(), BookMarkProductActivity.class);
        intent.putExtra("productName", productList.get(position).getName());
        intent.putExtra("productPrice", productList.get(position).getPrice());
        intent.putExtra("productImage", productList.get(position).getImage());
        intent.putExtra("productTitle", productList.get(position).getTitle());
        startActivity(intent);
    }
}
