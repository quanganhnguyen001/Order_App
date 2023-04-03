package com.eduahihi.odershop.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eduahihi.odershop.screen.Login;
import com.eduahihi.odershop.screen.SignUp;

public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Login login = new Login();
                return login;
            case 1:
                SignUp signUp = new SignUp();
                return signUp;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0){
            title="Đăng Nhập";
        }
        else {
            title = "Đăng Ký";
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
