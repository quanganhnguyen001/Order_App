package com.eduahihi.odershop.screen.fragment;

import static com.eduahihi.odershop.screen.Login.userId;
import static com.eduahihi.odershop.screen.homeActivity.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseCartDao;
import com.eduahihi.odershop.database.databaseOrderDao;
import com.eduahihi.odershop.database.databaseProductDao;
import com.eduahihi.odershop.database.databaseUserDao;
import com.eduahihi.odershop.databinding.FragmentDetailProductBinding;
import com.eduahihi.odershop.model.cart;
import com.eduahihi.odershop.model.order;
import com.eduahihi.odershop.model.product;
import com.eduahihi.odershop.model.user;

import java.util.List;


public class detailProductFragment extends Fragment {


    private product mProduct;

    FragmentDetailProductBinding binding;

    private user mUser;

    public detailProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = (product) getArguments().getSerializable("product");
        mUser = new databaseUserDao(getContext()).getUserById(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvProductName.setText("Name : " + mProduct.getName());
        binding.tvProductPrice.setText("Price : " + mProduct.getPrice());
        binding.tvProductDescription.setText("Description : " + mProduct.getDescription());
        binding.tvQuantity.setText("Quatity : " + mProduct.getQuantity());
        binding.ivProductImage.setImageBitmap(getImage(mProduct.getImage()));

        binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(binding.tvQuantityC.getText().toString());
                if (quantity >= 1) {
                    quantity--;
                    binding.tvQuantityC.setText(String.valueOf(quantity));
                }
            }
        });

        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(binding.tvQuantityC.getText().toString());
                if (quantity < Integer.parseInt(mProduct.getQuantity())) {
                    quantity++;
                    binding.tvQuantityC.setText(String.valueOf(quantity));
                } else {
                    Toast.makeText(getContext(), "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class fragmentClass = homeFragment.class;
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

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProduct.getQuantity().equals("0")) {
                    Toast.makeText(getContext(), "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(binding.tvQuantityC.getText().toString() )== 0) {
                    Toast.makeText(getContext(), "Vui lòng chọn số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mUser != null) {
                    String date = System.currentTimeMillis() + "";
                    new databaseCartDao(getContext()).getCartByIdUserAndIdProduct(mUser.getId(), Integer.parseInt(mProduct.getId()), new databaseCartDao.onClickGetOne() {
                        @Override
                        public void onSuccess(cart cart) {
                            if (cart.isStatus() == 0) {
                                if (cart != null) {
                                    cart.setQuantity(cart.getQuantity() + Integer.parseInt(binding.tvQuantityC.getText().toString()));
                                    new databaseCartDao(getContext()).updateCart(cart, new databaseCartDao.onClickAddCard() {
                                        @Override
                                        public void onSuccess() {
                                            mProduct.setQuantity(String.valueOf(Integer.parseInt(mProduct.getQuantity()) - Integer.parseInt(binding.tvQuantityC.getText().toString())));
                                            new databaseProductDao(getContext()).updateProduct(mProduct, new databaseProductDao.onClickSaveProduct() {
                                                @Override
                                                public void success() {
                                                    Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                                    binding.tvQuantity.setText("Quatity : " + mProduct.getQuantity());
                                                    binding.tvQuantityC.setText("0");
                                                }

                                                @Override
                                                public void fail() {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onFail() {
                                            Toast.makeText(getContext(), "Thêm vào giỏ hàng thất bại 1", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                new databaseCartDao(getContext()).insertCart(new cart(Integer.parseInt(mProduct.getId()),
                                        mUser.getId(), Integer.parseInt(binding.tvQuantityC.getText().toString()), Long.parseLong(date)), new databaseCartDao.onClickAddCard() {
                                    @Override
                                    public void onSuccess() {
                                        mProduct.setQuantity(String.valueOf(Integer.parseInt(mProduct.getQuantity()) - Integer.parseInt(binding.tvQuantityC.getText().toString())));
                                        new databaseProductDao(getContext()).updateProduct(mProduct, new databaseProductDao.onClickSaveProduct() {
                                            @Override
                                            public void success() {
                                                Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                                binding.tvQuantity.setText("Quatity : " + mProduct.getQuantity());
                                                binding.tvQuantityC.setText("0");
                                            }

                                            @Override
                                            public void fail() {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onFail() {
                                        Toast.makeText(getContext(), "Thêm vào giỏ hàng thất bại 2", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFail() {
                            new databaseCartDao(getContext()).insertCart(new cart(Integer.parseInt(mProduct.getId()),
                                    mUser.getId(), Integer.parseInt(binding.tvQuantityC.getText().toString()), Long.parseLong(date)), new databaseCartDao.onClickAddCard() {
                                @Override
                                public void onSuccess() {
                                    mProduct.setQuantity(String.valueOf(Integer.parseInt(mProduct.getQuantity()) - Integer.parseInt(binding.tvQuantityC.getText().toString())));
                                    new databaseProductDao(getContext()).updateProduct(mProduct, new databaseProductDao.onClickSaveProduct() {
                                        @Override
                                        public void success() {
                                            Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                            binding.tvQuantity.setText("Quatity : " + mProduct.getQuantity());
                                            binding.tvQuantityC.setText("0");
                                        }

                                        @Override
                                        public void fail() {

                                        }
                                    });

                                }

                                @Override
                                public void onFail() {
                                    Toast.makeText(getContext(), "Thêm vào giỏ hàng thất bại 2", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //convert byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}