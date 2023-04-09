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
import com.eduahihi.odershop.databinding.FragmentHoadonUserBinding;
import com.eduahihi.odershop.model.cart;

import java.util.List;


public class hoadonUserFragment extends Fragment {

    FragmentHoadonUserBinding binding;
    public hoadonUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHoadonUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        new databaseCartDao(getContext()).getCartByIdUser(userId, new databaseCartDao.onClickGetAll() {
            @Override
            public void onSuccess(List<cart> list) {
                list.removeIf(cart ->cart.getIsCart() == 0);
                list.sort((o1, o2) -> o2.getId() - o1.getId());
                binding.hoadonRecyclerView.setAdapter(new adapterCart(getContext(), list,false));
                binding.hoadonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFail() {
                Toast.makeText(getContext(), "Không có sản phẩm nào trong hoá đơn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}