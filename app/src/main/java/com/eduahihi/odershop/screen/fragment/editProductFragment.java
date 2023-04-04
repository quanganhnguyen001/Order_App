package com.eduahihi.odershop.screen.fragment;

import static android.app.Activity.RESULT_OK;
import static com.eduahihi.odershop.screen.homeActivity.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.eduahihi.odershop.R;
import com.eduahihi.odershop.database.databaseCategoryDao;
import com.eduahihi.odershop.database.databaseProductDao;

import com.eduahihi.odershop.databinding.FragmentEditProductBinding;
import com.eduahihi.odershop.model.category;
import com.eduahihi.odershop.model.product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class editProductFragment extends Fragment {


    FragmentEditProductBinding binding;
    private product mProduct;
    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_GALLERY = 103;
    private List<category> listCategory= new ArrayList<>();
    public editProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = (product) getArguments().getSerializable("product");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> list = new ArrayList<>();
        listCategory = new databaseCategoryDao(getContext()).getAllCategory();
        if(listCategory.size() == 0){
            Toast.makeText(getContext(), "Chưa có danh mục sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < listCategory.size(); i++) {
            list.add(listCategory.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spProductCategory.setAdapter(adapter);
        int position = 0;
        for (int i = 0; i < listCategory.size(); i++) {
            if(listCategory.get(i).getId() == Integer.parseInt(mProduct.getId_category())){
                position = i;
                break;
            }
        }
        binding.spProductCategory.setSelection(position);

        binding.etProductName.setText(mProduct.getName());
        binding.etProductPrice.setText(mProduct.getPrice());
        binding.etProductDescription.setText(mProduct.getDescription());
        binding.etProductQuantity.setText(mProduct.getQuantity());
        binding.ivProductImage.setVisibility(View.VISIBLE);
        binding.ivProductImage.setImageBitmap(getBitmapFromByteArray(mProduct.getImage()));

        binding.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this product!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setTitleText("Deleted!")
                                        .setContentText("Your product has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                new databaseProductDao(getContext()).deleteProduct(mProduct.getId(), new databaseProductDao.onClickSaveProduct() {
                                    @Override
                                    public void success() {
                                        Toast.makeText(getContext(), "Delete product successfully", Toast.LENGTH_SHORT).show();
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

                                    @Override
                                    public void fail() {

                                    }
                                });
                            }
                        })
                        .show();


            }
        });

        binding.btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lựa chọn ảnh từ thư viện hoặc chụp ảnh
                String[] menu = {"Chụp ảnh", "Thư viện ảnh"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Chọn ảnh");
                builder.setItems(menu, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            //chụp ảnh
                            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                            }else {
                                //chụp ảnh
                                askCameraPermission();
                            }
                            break;
                        case 1:
                            //thư viện ảnh
                            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                            }else {
                                //thư viện ảnh
                                askGalleryPermission();
                            }
                            break;
                    }
                });
                builder.show();
            }
        });

        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etProductName.getText().toString();
                String price = binding.etProductPrice.getText().toString();
                String description = binding.etProductDescription.getText().toString();
                String quatity = binding.etProductQuantity.getText().toString();
                int position = binding.spProductCategory.getSelectedItemPosition();
                String id_category = String.valueOf(listCategory.get(position).getId());
                if(name.isEmpty()){
                    binding.etProductName.setError("Tên sản phẩm không được để trống");
                    return;
                }
                if(price.isEmpty()){
                    binding.etProductPrice.setError("Giá sản phẩm không được để trống");
                    return;
                }
                if(description.isEmpty()){
                    binding.etProductDescription.setError("Mô tả sản phẩm không được để trống");
                    return;
                }
                if(quatity.isEmpty()){
                    binding.etProductQuantity.setError("Số lượng sản phẩm không được để trống");
                    return;
                }
                byte[] image = converBitmaptoByte(binding.ivProductImage);
                product product = new product(mProduct.getId(),name, price, image, quatity, description, id_category);
                new databaseProductDao(getActivity()).updateProduct(product, new databaseProductDao.onClickSaveProduct() {
                    @Override
                    public void success() {

                        Toast.makeText(getActivity(), "sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail() {
                        Toast.makeText(getActivity(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
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

    }

    //byte array to bitmap
    private Bitmap getBitmapFromByteArray(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private void askCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
    }

    private void askGalleryPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_CAMERA){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }else {
                Toast.makeText(getActivity(), "Bạn không cho phép mở camera", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else {
                Toast.makeText(getActivity(), "Bạn không cho phép mở thư viện ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            //chụp ảnh
            binding.ivProductImage.setVisibility(View.VISIBLE);
            binding.ivProductImage.setImageBitmap((Bitmap) data.getExtras().get("data"));
        }else if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            //thư viện ảnh
            binding.ivProductImage.setVisibility(View.VISIBLE);
            binding.ivProductImage.setImageURI(data.getData());
        }
    }

    private byte[] converBitmaptoByte(ImageView bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap1 = ((BitmapDrawable) bitmap.getDrawable()).getBitmap();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}