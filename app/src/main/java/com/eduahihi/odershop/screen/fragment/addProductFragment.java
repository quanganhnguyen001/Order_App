package com.eduahihi.odershop.screen.fragment;

import static android.app.Activity.RESULT_OK;
import static com.eduahihi.odershop.screen.homeActivity.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.eduahihi.odershop.databinding.FragmentAddProductBinding;
import com.eduahihi.odershop.model.category;
import com.eduahihi.odershop.model.product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class addProductFragment extends Fragment {


    FragmentAddProductBinding binding;

    private List<category> listCategory=new ArrayList<>();
    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_GALLERY = 103;
    public addProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
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
        binding.spProductCategory.setSelection(0);

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
                int category = listCategory.get(binding.spProductCategory.getSelectedItemPosition()).getId();
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

                product product = new product(name, price, image, quatity, description, String.valueOf(category));
                new databaseProductDao(getActivity()).insertProduct(product, new databaseProductDao.onClickSaveProduct() {
                    @Override
                    public void success() {
                        binding.etProductName.setText("");
                        binding.etProductPrice.setText("");
                        binding.etProductDescription.setText("");
                        binding.etProductQuantity.setText("");
                        Toast.makeText(getActivity(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail() {
                        Toast.makeText(getActivity(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
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