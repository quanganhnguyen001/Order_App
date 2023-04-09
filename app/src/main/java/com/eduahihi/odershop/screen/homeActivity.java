package com.eduahihi.odershop.screen;

import static com.eduahihi.odershop.screen.Login.isAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.databinding.ActivityHomeBinding;
import com.eduahihi.odershop.screen.fragment.addcategoryFragment;
import com.eduahihi.odershop.screen.fragment.cartFragment;
import com.eduahihi.odershop.screen.fragment.cartSFragment;
import com.eduahihi.odershop.screen.fragment.hoadonUserFragment;
import com.eduahihi.odershop.screen.fragment.homeFragment;
import com.eduahihi.odershop.screen.fragment.profileFragment;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;


    public static Fragment fragment;
    Class fragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        List<MenuItem> menuItems = new ArrayList<>();

        if (isAdmin) {
            menuItems.add(new MenuItem("Sản phẩm", R.drawable.bg1));
            menuItems.add(new MenuItem("Thể loại", R.drawable.bg2));
            menuItems.add(new MenuItem("Hoá Đơn", R.drawable.bg3));
            menuItems.add(new MenuItem("Đăng xuất", R.drawable.bg1));
        } else {
            menuItems.add(new MenuItem("Sản phẩm", R.drawable.bg3));
            menuItems.add(new MenuItem("Giỏ hàng", R.drawable.bg2));
            menuItems.add(new MenuItem("Hoá Đơn", R.drawable.bg3));
            menuItems.add(new MenuItem("Trang cá Nhân", R.drawable.bg3));
            menuItems.add(new MenuItem("Đăng xuất", R.drawable.bg1));
        }

        binding.navigationDrawer.setMenuItemList(menuItems);

        fragmentClass = homeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        binding.navigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {

                if (!isAdmin) {
                    switch (position) {
                        case 0:
                            fragmentClass = homeFragment.class;
                            break;
                        case 1:
                            fragmentClass = cartFragment.class;
                            break;
                        case 2:
                            fragmentClass = hoadonUserFragment.class;
                            break;
                        case 3:
                            fragmentClass = profileFragment.class;
                            break;
                        case 4:
                            startActivity(new Intent(homeActivity.this, MainActivity.class));
                            finish();
                            break;
                    }
                } else {
                    switch (position) {
                        case 0:
                            fragmentClass = homeFragment.class;
                            break;
                        case 1:
                            fragmentClass = addcategoryFragment.class;
                            break;
                        case 2:
                            fragmentClass = cartSFragment.class;
                            break;
                        case 3:
                            startActivity(new Intent(homeActivity.this, MainActivity.class));
                            finish();
                            break;
                    }
                }
            }
        });

        binding.navigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

            @Override
            public void onDrawerOpened() {

            }

            @Override
            public void onDrawerOpening() {

            }

            @Override
            public void onDrawerClosing() {
                System.out.println("Drawer closed");

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                }
            }

            @Override
            public void onDrawerClosed() {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                System.out.println("State " + newState);
            }
        });

        binding.navigationDrawer.setOnHamMenuClickListener(new SNavigationDrawer.OnHamMenuClickListener() {
            @Override
            public void onHamMenuClicked() {
                System.out.println("Ham menu clicked");
            }
        });
    }


}