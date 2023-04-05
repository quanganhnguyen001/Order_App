package com.eduahihi.odershop.screen.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.adapter.adapterAddCategory;
import com.eduahihi.odershop.database.databaseCategoryDao;
import com.eduahihi.odershop.databinding.FragmentCartSBinding;
import com.eduahihi.odershop.model.category;

import java.util.ArrayList;
import java.util.List;


public class addcategoryFragment extends Fragment {

    FragmentCartSBinding binding;
    public addcategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartSBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapterAddCategory adapterAddCategory = new adapterAddCategory(getContext(),new databaseCategoryDao(getContext()).getAllCategory() );
        binding.rvCategoryList.setAdapter(adapterAddCategory);
        binding.rvCategoryList.setHasFixedSize(true);
        binding.rvCategoryList.setItemViewCacheSize(20);
        binding.rvCategoryList.setDrawingCacheEnabled(true);
        binding.rvCategoryList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etCategoryName.getText().toString();
                if (name.isEmpty()) {
                    binding.etCategoryName.setError("Please enter category name");
                    return;
                }
                category category = new category(name);
                databaseCategoryDao databaseCategoryDao = new databaseCategoryDao(getContext());
                databaseCategoryDao.insertCategory(category);
                Toast.makeText(getContext(), "Add category successfully", Toast.LENGTH_SHORT).show();
                binding.etCategoryName.setText("");
                adapterAddCategory adapterAddCategory = new adapterAddCategory(getContext(), databaseCategoryDao.getAllCategory());
                binding.rvCategoryList.setAdapter(adapterAddCategory);
                binding.rvCategoryList.setHasFixedSize(true);
                binding.rvCategoryList.setItemViewCacheSize(20);
                binding.rvCategoryList.setDrawingCacheEnabled(true);
                binding.rvCategoryList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            }
        });
    }
}