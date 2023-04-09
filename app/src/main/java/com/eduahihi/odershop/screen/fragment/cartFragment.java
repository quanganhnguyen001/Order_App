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
import com.eduahihi.odershop.database.databaseCartDao;
import com.eduahihi.odershop.database.databaseProductDao;
import com.eduahihi.odershop.databinding.FragmentCartBinding;
import com.eduahihi.odershop.model.cart;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class cartFragment extends Fragment {

    FragmentCartBinding binding;

    public cartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCheckout.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        new databaseCartDao(getContext()).getCartByIdUser(userId, new databaseCartDao.onClickGetAll() {
            @Override
            public void onSuccess(List<cart> list) {
                list.removeIf(cart ->cart.getIsCart() == 1);
                binding.cartRecyclerView.setAdapter(new adapterCart(getContext(), list,true));
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
                binding.tvTotalPrice.setText("Total Price : "+total + "VND");
                double vat = (total * 0.1);
                //làm tròn 2 số sau dấu phẩy
                vat = Math.round(vat * 100.0) / 100.0;
                binding.tvSubTotal.setText("VAT 10% : "+vat + "VND");
                binding.tvTotal.setText("Total : "+(total + vat) + "VND");

            }

            @Override
            public void onFail() {
                Toast.makeText(getContext(), "No product", Toast.LENGTH_SHORT).show();
            }
        });
    }
}