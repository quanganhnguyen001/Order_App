package com.eduahihi.odershop.adapter;

import static com.eduahihi.odershop.screen.homeActivity.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseCartDao;
import com.eduahihi.odershop.database.databaseProductDao;
import com.eduahihi.odershop.databinding.ItemCartBinding;
import com.eduahihi.odershop.model.cart;
import com.eduahihi.odershop.model.product;
import com.eduahihi.odershop.screen.fragment.cartFragment;
import com.eduahihi.odershop.screen.homeActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class adapterUserCart extends RecyclerView.Adapter<adapterUserCart.ViewHolder> {
    private Context context;
    private List<cart> list;
    private List<product> listProduct;
    private databaseProductDao mdatabaseProductDao;

    public adapterUserCart(Context context, List<cart> list) {
        this.context = context;
        this.list = list;

        mdatabaseProductDao = new

                databaseProductDao(context);
    }

    @NonNull
    @Override
    public adapterUserCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);

        return new adapterUserCart.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterUserCart.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        cart cart = list.get(position);
        if (!cart.isStatus()) {
            product mProduct = mdatabaseProductDao.getProductById(cart.getId_product());
            holder.binding.cartProductName.setText(mProduct.getName());
            double price = Double.parseDouble(mProduct.getPrice()) * cart.getQuantity();
            String priceString = String.format("%.2f", price);
            holder.binding.cartProductPrice.setText(priceString + " VND");
            holder.binding.cartProductQuantity.setText("Quantity: " + cart.getQuantity());
            holder.binding.cartProductImage.setImageBitmap(getBitmapFromByte(mProduct.getImage()));
        }

        holder.binding.myOrderProductDelete.setVisibility(View.GONE);
        holder.binding.status.setVisibility(View.GONE);
    }

    //convert byte to bitmap
    public Bitmap getBitmapFromByte(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCartBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
