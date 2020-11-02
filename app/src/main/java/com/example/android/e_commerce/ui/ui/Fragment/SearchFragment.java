package com.example.android.e_commerce.ui.ui.Fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.e_commerce.databinding.FragmentSearchBinding;
import com.example.android.e_commerce.ui.Adapter.SearchAdapter;
import com.example.android.e_commerce.ui.ViewModel.ProductViewModel;
import com.example.android.e_commerce.ui.data.Product;
import com.example.android.e_commerce.ui.ui.Activity.ProductActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SearchFragment extends Fragment implements SearchAdapter.OnSearchProductListener {
    private FragmentSearchBinding mBinding;
    private List<Product> productList;
    private SearchAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSearchBinding.inflate(inflater, container, false);

        productList = new ArrayList<>();

        ProductViewModel viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

//        viewModel.getSearchResult(mBinding.searchEd.getText().toString().toUpperCase().trim());
//        mBinding.btnSearch.setOnClickListener(view -> {
////            viewModel.getSearchResult(mBinding.searchEd.getText().toString().toUpperCase()).observe(getActivity(), products ->
////                    productList.addAll(products));
////                    mAdapter.notifyDataSetChanged();
//            viewModel.getSearchResult(mBinding.searchEd.getText().toString().toUpperCase().trim());
//            productList.clear();
//            mAdapter.notifyDataSetChanged();
//
//            viewModel.getSearchMutableLiveData().observe(requireActivity(),products ->
//                    productList.addAll(products));
//            mAdapter.notifyDataSetChanged();
//        });

        viewModel.getSearchMutableLiveData()
                .observe(requireActivity(), products ->{
                    productList.clear();
                    productList.addAll(products);
                    mAdapter.notifyDataSetChanged();
                } );

//        mBinding.btnSearch.setOnClickListener(view -> viewModel.getSearchResult(mBinding.searchEd.getText().toString().trim().toUpperCase()));

        mBinding.searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.getSearchResult(charSequence.toString().toUpperCase().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setupRecyclerView();

        return mBinding.getRoot();
    }

    private void setupRecyclerView() {
        if (mAdapter == null) {
            productList.clear();
            mAdapter = new SearchAdapter(getActivity(), LayoutInflater.from(getActivity()), productList, this);
            mBinding.searchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBinding.searchRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            productList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSearchProductClick(int position) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("productName", productList.get(position).getName());
        intent.putExtra("productPrice", productList.get(position).getPrice());
        intent.putExtra("productImage", productList.get(position).getImage());
        intent.putExtra("productTitle", productList.get(position).getTitle());
        startActivity(intent);
    }


}
