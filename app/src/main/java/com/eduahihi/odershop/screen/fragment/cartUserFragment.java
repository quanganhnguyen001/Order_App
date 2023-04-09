package com.eduahihi.odershop.screen.fragment;

import static com.eduahihi.odershop.screen.Login.userId;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.adapter.adapterCart;
import com.eduahihi.odershop.adapter.adapterUserCart;
import com.eduahihi.odershop.database.databaseCartDao;
import com.eduahihi.odershop.database.databaseProductDao;
import com.eduahihi.odershop.databinding.FragmentCartUserBinding;
import com.eduahihi.odershop.model.cart;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class cartUserFragment extends Fragment {

    int userId = -1;
    FragmentCartUserBinding binding;
    private List<cart> listCart = new ArrayList<>();

    public cartUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartUserBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Are you sure?").setContentText("Won't be able to recover this file!").setConfirmText("Yes,delete it!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        new databaseCartDao(getContext()).updateStatusCart(userId, 1,new databaseCartDao.onClickAddCard() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(), "Checkout success", Toast.LENGTH_SHORT).show();
                                loadCart();
                            }

                            @Override
                            public void onFail() {
                                Toast.makeText(getContext(), "Checkout fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                        sDialog.dismissWithAnimation();
                    }
                }).setCancelButton("No,cancel plx!", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                }).show();
            }
        });
        binding.btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Are you sure?").setContentText("Won't be able to recover this file!").setConfirmText("Yes,delete it!").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        for (cart c : listCart) {
                            new databaseCartDao(getContext()).getCartByIdUserAndIdProduct(userId, c.getId_product(), new databaseCartDao.onClickGetOne() {
                                @Override
                                public void onSuccess(cart cart) {
                                    new databaseProductDao(getContext()).updateQuantityProduct(String.valueOf(cart.getId_product()), cart.getQuantity(), new databaseProductDao.onClickSaveProduct() {
                                        @Override
                                        public void success() {
                                            new databaseCartDao(getContext()).updateStatusCart(userId, 2,new databaseCartDao.onClickAddCard() {
                                                @Override
                                                public void onSuccess() {
                                                    Toast.makeText(getContext(), "Cancel success", Toast.LENGTH_SHORT).show();
                                                    loadCart();
                                                }

                                                @Override
                                                public void onFail() {
                                                    Toast.makeText(getContext(), "Checkout fail", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void fail() {

                                        }
                                    });
                                }

                                @Override
                                public void onFail() {
                                    Toast.makeText(getContext(), "Delete fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        sDialog.dismissWithAnimation();
                    }
                }).setCancelButton("No,cancel plx!", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                }).show();
            }
        });
    }

    private void loadCart() {
        new databaseCartDao(getContext()).getCartsByIdUser(userId, new databaseCartDao.onClickGetAll() {
            @Override
            public void onSuccess(List<cart> list) {
                listCart = list;
                list.removeIf(cart -> cart.getStatus() == 1 );
                binding.cartRecyclerView.setAdapter(new adapterUserCart(getContext(), list));
                binding.cartRecyclerView.setHasFixedSize(true);
                binding.cartRecyclerView.setItemViewCacheSize(20);
                binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                double total = 0;
                for (cart c : list) {
                    if (c.isStatus() == 0) {
                        double price = Double.parseDouble(new databaseProductDao(getContext()).getProductById(c.getId_product()).getPrice());
                        total += price * c.getQuantity();
                    }
                }
                binding.tvTotalPrice.setText("Total Price : " + total + "VND");
                double vat = (total * 0.1);
                //làm tròn 2 số sau dấu phẩy
                vat = Math.round(vat * 100.0) / 100.0;
                binding.tvSubTotal.setText("VAT 10% : " + vat + "VND");
                binding.tvTotal.setText("Total : " + (total + vat) + "VND");

            }

            @Override
            public void onFail() {
                binding.tvTotalPrice.setText("Total Price : 0 VND");
                binding.tvSubTotal.setText("VAT 10% : 0 VND");
                binding.tvTotal.setText("Total : 0 VND");
                binding.cartRecyclerView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCart();
    }
}