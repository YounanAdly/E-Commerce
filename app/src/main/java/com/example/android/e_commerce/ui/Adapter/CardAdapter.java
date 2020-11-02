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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<Product> products;


    public CardAdapter(Context context, LayoutInflater mInflater, List<Product> products) {
        this.context = context;
        this.mInflater = mInflater;
        this.products = products;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter.ViewHolder holder, final int i) {

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productSize;
        ImageView productImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameCard);
            productPrice = itemView.findViewById(R.id.productPriceCard);
            productSize = itemView.findViewById(R.id.productSizeCard);
            productImage = itemView.findViewById(R.id.productImageCard);

        }

    }


}
