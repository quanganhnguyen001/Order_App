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
import android.widget.Toast;

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
import com.eduahihi.odershop.screen.fragment.homeFragment;
import com.eduahihi.odershop.screen.homeActivity;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class adapterCart extends RecyclerView.Adapter<adapterCart.ViewHolder> {
    private Context context;
    private List<cart> list;
    private List<product> listProduct;
    private databaseProductDao mdatabaseProductDao;
    private boolean isCart = false;

    public adapterCart(Context context, List<cart> list, boolean isCart) {
        this.context = context;
        this.list = list;
        this.isCart = isCart;
        mdatabaseProductDao = new databaseProductDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        cart cart = list.get(position);
        product mProduct = mdatabaseProductDao.getProductById(cart.getId_product());
        holder.binding.cartProductName.setText(mProduct.getName());
        double price = Double.parseDouble(mProduct.getPrice()) * cart.getQuantity();
        String priceString = String.format("%.2f", price);
        holder.binding.cartProductPrice.setText(priceString + " VND");
        holder.binding.cartProductQuantity.setText("Quantity: " + cart.getQuantity());
        holder.binding.cartProductImage.setImageBitmap(getBitmapFromByte(mProduct.getImage()));
        if (cart.isStatus() == 1) {
            holder.binding.status.setText("Đã thanh toán");
            holder.binding.status.setTextColor(context.getResources().getColor(R.color.green));
        } else if (cart.isStatus() == 2){
            holder.binding.status.setText("Đơn đã huỷ");
            holder.binding.status.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            holder.binding.status.setText("Chưa thanh toán");
            holder.binding.status.setTextColor(context.getResources().getColor(R.color.red));
        }
        if (isCart) {
            holder.binding.myOrderProductDelete.setVisibility(View.VISIBLE);
            holder.binding.status.setVisibility(View.GONE);
            holder.binding.cartProductTime.setVisibility(View.GONE);
        } else {
            holder.binding.myOrderProductDelete.setVisibility(View.GONE);
            holder.binding.status.setVisibility(View.VISIBLE);
            holder.binding.cartProductTime.setVisibility(View.VISIBLE);
            holder.itemView.setEnabled(false);
        }
        String date = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date);
        holder.binding.cartProductTime.setText(simpleDateFormat.format(cart.getDate()));
        holder.binding.myOrderProductDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                new databaseCartDao(context).deleteCart(cart.getId(), new databaseCartDao.onClickDeleteCart() {
                                    @Override
                                    public void onSuccess() {
                                        mProduct.setQuantity(mProduct.getQuantity() + cart.getQuantity());
                                        mdatabaseProductDao.updateProduct(mProduct, new databaseProductDao.onClickSaveProduct() {
                                            @Override
                                            public void success() {
                                                list.remove(position);
                                                notifyDataSetChanged();
                                                //dismess
                                                sDialog.dismissWithAnimation();
                                                // Create new fragment and transaction
                                                Class fragmentClass = cartFragment.class;
                                                try {
                                                    fragment = (Fragment) fragmentClass.newInstance();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                // Insert the fragment by replacing any existing fragment
                                                if (fragment != null) {
                                                    FragmentManager fragmentManager = ((homeActivity) context).getSupportFragmentManager();
                                                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                                                }
                                            }

                                            @Override
                                            public void fail() {
                                                Log.d("TAGProduct", "fail: ");
                                            }
                                        });

                                    }

                                    @Override
                                    public void onFail() {
                                        Log.d("TAGCart", "onFail: ");
                                    }
                                });

                            }
                        })
                        .setCancelButton("No,cancel plx!", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                new databaseCartDao(context).updateCartById(cart.getId(), 1, new databaseCartDao.onClickDeleteCart() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(context, "cancel success", Toast.LENGTH_SHORT).show();
                                        Class fragmentClass = cartFragment.class;
                                        try {
                                            fragment = (Fragment) fragmentClass.newInstance();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        // Insert the fragment by replacing any existing fragment
                                        if (fragment != null) {
                                            FragmentManager fragmentManager = ((homeActivity) context).getSupportFragmentManager();
                                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                                        }
                                    }

                                    @Override
                                    public void onFail() {

                                    }
                                });
                            }
                        })
                        .setCancelButton("No,cancel plx!", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();

            }
        });

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
