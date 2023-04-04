package com.eduahihi.odershop.adapter;

import static com.eduahihi.odershop.screen.Login.isAdmin;
import static com.eduahihi.odershop.screen.homeActivity.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.databinding.ItemProductBinding;
import com.eduahihi.odershop.model.product;
import com.eduahihi.odershop.screen.fragment.addProductFragment;
import com.eduahihi.odershop.screen.fragment.detailProductFragment;
import com.eduahihi.odershop.screen.fragment.editProductFragment;
import com.eduahihi.odershop.screen.homeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterProduct extends RecyclerView.Adapter<adapterProduct.ViewHolder>{
    Context context;
    List<product> list;

    public adapterProduct(Context context, List<product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product product = list.get(position);
        holder.binding.tvProductName.setText(product.getName());
        holder.binding.tvProductPrice.setText(product.getPrice()+" VND");
        holder.binding.ivProductImage.setImageBitmap(getBitmapFromByte(product.getImage()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class fragmentClass = null;
                if(isAdmin){
                    fragmentClass = editProductFragment.class;
                }else{
                    fragmentClass = detailProductFragment.class;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product",product);
                    fragment.setArguments(bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                if (fragment != null) {
                    FragmentManager fragmentManager = ((homeActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                }
            }
        });
    }

    //conver byte to bitmap
    public Bitmap getBitmapFromByte(byte[] image){
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemProductBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemProductBinding.bind(itemView);
        }
    }
}
