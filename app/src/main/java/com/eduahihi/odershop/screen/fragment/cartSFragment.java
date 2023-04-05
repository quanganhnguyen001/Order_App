package com.eduahihi.odershop.screen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.adapter.adapterListUser;
import com.eduahihi.odershop.database.databaseCartDao;
import com.eduahihi.odershop.databinding.FragmentCartS2Binding;

import java.util.List;


public class cartSFragment extends Fragment {


    FragmentCartS2Binding binding;

    public cartSFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartS2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        new databaseCartDao(getContext()).getAllIdUser(new databaseCartDao.onClickGetAllIdUser() {
            @Override
            public void onSuccess(List<Integer> list) {
                if (list.size() > 0) {
                    binding.recyclerViewCartS.setAdapter(new adapterListUser(list, getContext()));
                    binding.recyclerViewCartS.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }

            @Override
            public void onFail() {

            }
        });
    }
}