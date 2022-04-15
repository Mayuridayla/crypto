package com.crypto.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.crypto.api.Onclick;
import com.crypto.R;
import com.crypto.model.CryptoResponse;

import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.ViewHolder> {

    ArrayList<CryptoResponse> lst_product;
    Context mContext;
    Onclick itemClick;



    public CryptoAdapter(ArrayList<CryptoResponse> lst_product, Context applicationContext, Onclick itemClick) {
        this.lst_product = lst_product;
        this.mContext = applicationContext;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public CryptoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_productlist_item, parent, false);
        mContext = parent.getContext();
        return new CryptoAdapter.ViewHolder(view);
    }

    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull CryptoAdapter.ViewHolder holder, int position) {


        holder.symbolName.setText(lst_product.get(position).getBaseAsset());
        holder.PriceTxt.setText("OPEN PRICE : " + lst_product.get(position).getOpenPrice());
        holder.bidPriceTxt.setText(lst_product.get(position).getBidPrice() + " " +lst_product.get(position).getQuoteAsset());
        String Symbol = lst_product.get(position).getSymbol();
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onItemClicks(view, position, 10, Symbol,lst_product.get(position).getBaseAsset(), lst_product.get(position).getBidPrice(), lst_product.get(position).getHighPrice(),lst_product.get(position).getLowPrice(), lst_product.get(position).getQuoteAsset());
            }
        });


    }


    @Override
    public int getItemCount() {
        return lst_product.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView symbolName, bidPriceTxt, descTxt, PriceTxt;

        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descTxt = itemView.findViewById(R.id.descTxt);
            bidPriceTxt = itemView.findViewById(R.id.bidPriceTxt);
            symbolName = itemView.findViewById(R.id.symbolNameTxt);
            PriceTxt = itemView.findViewById(R.id.PriceTxt);
            cardview = itemView.findViewById(R.id.cardview);

        }
    }


}