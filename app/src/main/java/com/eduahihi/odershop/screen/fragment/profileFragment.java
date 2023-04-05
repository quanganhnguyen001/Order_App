package com.eduahihi.odershop.screen.fragment;

import static com.eduahihi.odershop.screen.Login.userId;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseUserDao;
import com.eduahihi.odershop.databinding.FragmentProfileBinding;
import com.eduahihi.odershop.model.user;


public class profileFragment extends Fragment {



    FragmentProfileBinding binding;
    private user mUser;
    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mUser = new databaseUserDao(getContext()).getUserById(userId);

        binding.username.setText(mUser.getName());
        binding.phoneNo.setText(mUser.getPhone());

        binding.sigupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = binding.username.getText().toString();
                String phone = binding.phoneNo.getText().toString();
                String password = binding.password.getText().toString();

                if(binding.sigupBtn.getText().toString().equals("Thay đổi")){
                    binding.password.setVisibility(View.VISIBLE);
                    binding.password.requestFocus();
                    binding.sigupBtn.setText("Xác nhận");
                    return;
                }
                if(binding.sigupBtn.getText().toString().equals("Xác nhận")){
                    if(password.isEmpty()){
                        binding.password.setError("Password is required");
                        binding.password.requestFocus();
                        return;
                    }
                    if(!password.equals(mUser.getPassword())){
                        binding.password.setError("Password is incorrect");
                        binding.password.requestFocus();
                        return;
                    }
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
                    mUser.setName(fullName);
                    mUser.setPhone(phone);
                    new databaseUserDao(getContext()).updateUser(mUser, new databaseUserDao.onClickUpdate() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getContext(), "Update success", Toast.LENGTH_SHORT).show();

                            binding.password.setVisibility(View.GONE);
                            binding.sigupBtn.setText("Thay đổi");
                        }

                        @Override
                        public void onFail() {

                        }
                    });

                }
            }
        });

        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.changePassword.getText().toString().equals("Đổi mật khẩu")){
                    binding.password.setVisibility(View.VISIBLE);
                    binding.password.requestFocus();
                    binding.passwordNew.setVisibility(View.VISIBLE);
                    binding.rePassNew.setVisibility(View.VISIBLE);
                    binding.changePassword.setText("Xác nhận");
                }else{
                    String password = binding.password.getText().toString();
                    String passwordNew = binding.passwordNew.getText().toString();
                    String rePassNew = binding.rePassNew.getText().toString();
                    if(password.isEmpty()){
                        binding.password.setError("Password is required");
                        binding.password.requestFocus();
                        return;
                    }
                    if(!password.equals(mUser.getPassword())){
                        binding.password.setError("Password is incorrect");
                        binding.password.requestFocus();
                        return;
                    }
                    if(passwordNew.isEmpty()){
                        binding.passwordNew.setError("Password is required");
                        binding.passwordNew.requestFocus();
                        return;
                    }
                    if(rePassNew.isEmpty()){
                        binding.rePassNew.setError("Password is required");
                        binding.rePassNew.requestFocus();
                        return;
                    }
                    if(!passwordNew.equals(rePassNew)){
                        binding.rePassNew.setError("Password is incorrect");
                        binding.rePassNew.requestFocus();
                        return;
                    }
                    mUser.setPassword(passwordNew);
                    new databaseUserDao(getContext()).updateUser(mUser, new databaseUserDao.onClickUpdate() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getContext(), "Update success", Toast.LENGTH_SHORT).show();

                            binding.password.setVisibility(View.GONE);
                            binding.passwordNew.setVisibility(View.GONE);
                            binding.rePassNew.setVisibility(View.GONE);
                            binding.changePassword.setText("Đổi mật khẩu");
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }

            }
        });
    }
}