package com.eduahihi.odershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseCategoryDao;
import com.eduahihi.odershop.databinding.ItemCategoryBinding;
import com.eduahihi.odershop.databinding.ItemCategoryaddBinding;
import com.eduahihi.odershop.model.category;
import com.example.flatdialoglibrary.dialog.FlatDialog;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class adapterAddCategory extends RecyclerView.Adapter<adapterAddCategory.ViewHolder>{
    private Context context;
    private List<category> listCategory;

    public adapterAddCategory(Context context, List<category> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoryadd, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        category category = listCategory.get(position);
        holder.binding.tvCategoryName.setText(category.getName());


        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlatDialog flatDialog = new FlatDialog(context);
                flatDialog.setTitle("Edit Category")
                        .setSubtitle("Enter new category name")
                        .setFirstTextField(category.getName())
                        .setFirstButtonText("Cancel")
                        .setSecondButtonText("Edit")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog.dismiss();
                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String name = flatDialog.getFirstTextField();
                                if(name.isEmpty()){
                                    Toast.makeText(context, "Please enter category name", Toast.LENGTH_SHORT).show();
                                }else{
                                    category.setName(name);
                                    new databaseCategoryDao(context).updateCategory(category);
                                    listCategory = new databaseCategoryDao(context).getAllCategory();
                                    notifyDataSetChanged();
                                    flatDialog.dismiss();
                                }
                            }
                        })
                        .show();

            }
        });

        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                new databaseCategoryDao(context).deleteCategory(category.getId());
                                listCategory = new databaseCategoryDao(context).getAllCategory();
                                notifyDataSetChanged();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("No,cancel plx!", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .showCancelButton(true)
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemCategoryaddBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategoryaddBinding.bind(itemView);
        }
    }
}
