package com.eduahihi.odershop.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseUserDao;
import com.eduahihi.odershop.databinding.FragmentSignUpBinding;
import com.eduahihi.odershop.model.user;


public class SignUp extends Fragment {

    FragmentSignUpBinding binding;

    private databaseUserDao databaseuserDao;
    public SignUp() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseuserDao = new databaseUserDao(getContext());
        binding.sigupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = binding.username.getText().toString();
                String phone = binding.phoneNo.getText().toString();
                String password = binding.password.getText().toString();
                String rePassword = binding.repassword.getText().toString();

                if(fullName.isEmpty()){
                    binding.username.setError("Full name is required");
                    binding.username.requestFocus();
                    return;
                }
                if(phone.isEmpty()){
                    binding.phoneNo.setError("Phone number is required");
                    binding.phoneNo.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    binding.password.setError("Password is required");
                    binding.password.requestFocus();
                    return;
                }
                if(rePassword.isEmpty()){
                    binding.repassword.setError("Re-password is required");
                    binding.repassword.requestFocus();
                    return;
                }
                if(!password.equals(rePassword)){
                    binding.repassword.setError("Password is not match");
                    binding.repassword.requestFocus();
                    return;
                }

                user user = new user(fullName, phone, password);

                databaseuserDao.getUserByPhone(phone, new databaseUserDao.onClickLogin() {
                    @Override
                    public void onSuccess(user user) {
                        Toast.makeText(getContext(), "Phone number is already exist", Toast.LENGTH_SHORT).show();
                        binding.phoneNo.requestFocus();
                        binding.phoneNo.setText("");
                    }

                    @Override
                    public void onFail() {
                        databaseuserDao.insertUser(user, new databaseUserDao.onClickSignUp() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(), "Sign up successfully", Toast.LENGTH_SHORT).show();
                                binding.username.setText("");
                                binding.phoneNo.setText("");
                                binding.password.setText("");
                                binding.repassword.setText("");
                            }

                            @Override
                            public void onFail() {
                                Toast.makeText(getContext(), "Sign up fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}