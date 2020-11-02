package com.example.android.e_commerce.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.e_commerce.R;
import com.example.android.e_commerce.ui.data.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<Product> products;
    private OnSearchProductListener onSearchProductListener;


    public SearchAdapter(Context context, LayoutInflater mInflater, List<Product> products,OnSearchProductListener onSearchProductListener) {
        this.context = context;
        this.mInflater = mInflater;
        this.products = products;
        this.onSearchProductListener = onSearchProductListener;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.list_card, parent, false);
        return new ViewHolder(view,onSearchProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapter.ViewHolder holder, final int i) {

        holder.productName.setText(products.get(i).getName());
        holder.productPrice.setText(products.get(i).getPrice());
        holder.productSize.setText(products.get(i).getSize());
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
        TextView productSize;
        ImageView productImage;
        OnSearchProductListener onSearchProductListener;

        public ViewHolder(@NonNull View itemView,OnSearchProductListener onSearchProductListener) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameCard);
            productPrice = itemView.findViewById(R.id.productPriceCard);
            productSize = itemView.findViewById(R.id.productSizeCard);
            productImage = itemView.findViewById(R.id.productImageCard);

            this.onSearchProductListener = onSearchProductListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSearchProductListener.onSearchProductClick(getAdapterPosition());
        }
    }
    public interface OnSearchProductListener {
        void onSearchProductClick(int position);
    }

}
