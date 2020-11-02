package com.example.android.e_commerce.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.ui.data.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<Product> products;
    private OnProductListener onProductListener;
    private boolean hasMore = true;


    public ProductAdapter(Context context, LayoutInflater mInflater, List<Product> products, OnProductListener onProductListener) {
        this.context = context;
        this.mInflater = mInflater;
        this.products = products;
        this.onProductListener = onProductListener;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, onProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder holder, int i) {
        holder.progressBar.setVisibility(View.INVISIBLE);

        if (i == (getItemCount() - 1) && hasMore) {
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        holder.productName.setText(products.get(i).getName());
        holder.productPrice.setText(products.get(i).getPrice());

        String image = products.get(i).getImage();
        Picasso.with(context)
                .load(image)
                .into(holder.productImage);

    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView productName;
        TextView productPrice;
        ImageView productImage;
        ProgressBar progressBar;

        OnProductListener onProductListener;

        public ViewHolder(@NonNull View itemView, OnProductListener onProductListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameList);
            productPrice = itemView.findViewById(R.id.productPriceList);
            productImage = itemView.findViewById(R.id.productImageList);
            progressBar = itemView.findViewById(R.id.progressBar);

            this.onProductListener = onProductListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onProductListener.onProductClick(getAdapterPosition());
        }
    }


    public interface OnProductListener {
        void onProductClick(int position);
    }

}
