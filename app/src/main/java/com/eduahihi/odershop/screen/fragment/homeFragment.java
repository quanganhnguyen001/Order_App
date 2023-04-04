package com.eduahihi.odershop.screen.fragment;

import static com.eduahihi.odershop.screen.Login.isAdmin;
import static com.eduahihi.odershop.screen.homeActivity.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.adapter.adapterCategory;
import com.eduahihi.odershop.adapter.adapterProduct;
import com.eduahihi.odershop.database.databaseCategoryDao;
import com.eduahihi.odershop.database.databaseProductDao;
import com.eduahihi.odershop.databinding.FragmentHomeBinding;
import com.eduahihi.odershop.model.category;
import com.eduahihi.odershop.model.product;

import java.util.ArrayList;
import java.util.List;


public class homeFragment extends Fragment {

    FragmentHomeBinding binding;
    private int idCategory= -1;
    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        idCategory =  getArguments().getInt("idCategory");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isAdmin){
            binding.fab.setVisibility(View.VISIBLE);
        }else{
            binding.fab.setVisibility(View.GONE);
        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class fragmentClass = addProductFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                if (fragment != null) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        List<category> category =  new databaseCategoryDao(getContext()).getAllCategory();
        adapterCategory adapter = new adapterCategory(getContext(), category);
        binding.recyclerView1.setAdapter(adapter);
        binding.recyclerView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView1.setLayoutManager(layoutManager);
        if(idCategory == -1 && category.size() > 0){
            idCategory = category.get(0).getId();
        }
        new databaseProductDao(getContext()).getAllProduct(idCategory,new databaseProductDao.onClickGetAll() {
            @Override
            public void success(List<product> product) {
                adapterProduct adapter = new adapterProduct(getContext(), product);
                binding.recyclerView.setAdapter(adapter);
                binding.recyclerView.setHasFixedSize(true);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void fail() {

            }
        });
    }
}