package com.eduahihi.odershop.adapter;

import static com.eduahihi.odershop.screen.homeActivity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseCartDao;
import com.eduahihi.odershop.database.databaseUserDao;
import com.eduahihi.odershop.databinding.ItemUserBinding;
import com.eduahihi.odershop.model.cart;
import com.eduahihi.odershop.model.user;
import com.eduahihi.odershop.screen.fragment.cartFragment;
import com.eduahihi.odershop.screen.fragment.cartUserFragment;
import com.eduahihi.odershop.screen.homeActivity;

import java.util.List;

public class adapterListUser extends RecyclerView.Adapter<adapterListUser.ViewHolder> {
    private List<Integer> listUser;
    private Context context;
    private databaseUserDao mdatabaseUserDao;

    public adapterListUser(List<Integer> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
        mdatabaseUserDao = new databaseUserDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int idUser = listUser.get(position);
        user muser = mdatabaseUserDao.getUserById(idUser);
        holder.binding.tvUserName.setText(muser.getName());
        holder.binding.tvUserPhone.setText(muser.getPhone());
        new databaseCartDao(context).getCartByIdUser(idUser, new databaseCartDao.onClickGetAll() {
            @Override
            public void onSuccess(List<cart> list) {

            }
            @Override
            public void onFail() {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class fragmentClass = cartUserFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", idUser);
                    fragment.setArguments(bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                if (fragment != null) {
                    FragmentManager fragmentManager = ((homeActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemUserBinding.bind(itemView);
        }
    }
}
