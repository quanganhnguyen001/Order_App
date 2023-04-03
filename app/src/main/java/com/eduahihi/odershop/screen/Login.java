package com.eduahihi.odershop.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseUserDao;
import com.eduahihi.odershop.databinding.FragmentLoginBinding;
import com.eduahihi.odershop.model.user;


public class Login extends Fragment {

    FragmentLoginBinding binding;

    private databaseUserDao databaseuserDao;

    public static boolean isAdmin = false;

    public static int  userId = -1;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.username.setTranslationX(300);
        binding.username.setAlpha(0);
        binding.username.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();


        binding.password.setTranslationX(300);
        binding.password.setAlpha(0);
        binding.password.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();


        binding.loginBtn.setTranslationX(300);
        binding.loginBtn.setAlpha(0);
        binding.loginBtn.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseuserDao = new databaseUserDao(getContext());

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.username.getText().toString();
                String password = binding.password.getText().toString();

                if(phone.isEmpty()){
                    binding.username.setError("Phone number is required");
                    binding.username.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    binding.password.setError("Password is required");
                    binding.password.requestFocus();
                    return;
                }
                if(phone.equals("0988888888") && password.equals("admin")){
                    isAdmin = true;
                    Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), homeActivity.class));
                    return;
                }
                databaseuserDao.getUserByPhone(phone, new databaseUserDao.onClickLogin() {
                    @Override
                    public void onSuccess(user user) {
                        if(user.getPassword().equals(password)){
                            isAdmin = false;
                            Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), homeActivity.class);
                            userId = user.getId();
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), "Login fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(getContext(), "Login fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}