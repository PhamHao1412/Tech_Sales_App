package com.example.macmavenapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.macmavenapp.Model.User;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Retrofit.UserAPI;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;
import com.example.macmavenapp.Somethings.RealPathUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    ImageView ivAvatar, ivBack;
    EditText etFullName, etEmail, etPhoneNumber, etAddress;
    Button btnUpdate;
    User user;
    TextView tvChangePicture, tvError;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        AnhXa();
        LoadData();
        ivBackClick();
        tvChangePictureClick();
        btnUpdateClick();
    }

    private void btnUpdateClick() {
        btnUpdate.setOnClickListener(v -> {

            if(etFullName.getText().toString().isEmpty()){
                tvError.setText("Please enter your name!");
                return;
            }
            if (etEmail.getText().toString().isEmpty()){
                tvError.setText("Please enter your email!");
                return;
            }
            if(etPhoneNumber.getText().toString().isEmpty()){
                tvError.setText("Please enter your phone number!");
                return;
            }
            if (etAddress.getText().toString().isEmpty()){
                tvError.setText("Please enter your address!");
                return;
            }
            user = ObjectSharedPreferences.getSavedObjectFromPreference(EditProfileActivity.this, "User", "MODE_PRIVATE", User.class);
            RequestBody userId = RequestBody.create(user.getId(),MediaType.parse("multipart/form-data"));
            RequestBody fullName = RequestBody.create(etFullName.getText().toString(), MediaType.parse("multipart/form-data"));
            RequestBody email = RequestBody.create(etEmail.getText().toString(), MediaType.parse("multipart/form-data"));
            RequestBody phoneNumber = RequestBody.create(etPhoneNumber.getText().toString(), MediaType.parse("multipart/form-data"));
            RequestBody address = RequestBody.create(etAddress.getText().toString(), MediaType.parse("multipart/form-data"));
            MultipartBody.Part avatar = null;
            if(mUri!=null){
                String IMAGE_PATH = RealPathUtil.getRealPath(this, mUri);
                Log.e("ffff", IMAGE_PATH);
                File file = new File(IMAGE_PATH);
                RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                avatar=MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            }
            progressDialog = new ProgressDialog(EditProfileActivity.this);
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setTitle("Update Profile"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            UserAPI.userApi.update(userId,avatar, fullName, email, phoneNumber, address).enqueue(new Callback<User>() {
                public void onResponse(Call<User> call, Response<User> response) {
                    User userUpdate = response.body();
                    if(userUpdate!=null){
                        progressDialog.dismiss();
                        ObjectSharedPreferences.saveObjectToSharedPreference(EditProfileActivity.this, "User", "MODE_PRIVATE", userUpdate);
                        startActivity(new Intent(EditProfileActivity.this, UserActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("====", "call fail + " + t.getMessage());
                }
            });
        });
    }

    private void tvChangePictureClick() {
        tvChangePicture.setOnClickListener(v -> {
            CheckPermissions();
        });
    }


    private void ivBackClick() {
        ivBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }

    private void LoadData() {
        user = ObjectSharedPreferences.getSavedObjectFromPreference(EditProfileActivity.this, "User", "MODE_PRIVATE", User.class);
        etFullName.setText(user.getUser_Name());
        etEmail.setText(user.getEmail());
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(ivAvatar);
        if (user.getPhone_Number()!=null){
            etPhoneNumber.setText(user.getPhone_Number());
        }
        if(user.getAddress()!=null){
            etAddress.setText(user.getAddress());
        }
    }

    private void AnhXa() {
        ivAvatar = findViewById(R.id.ivAvatar);
        ivBack = findViewById(R.id.ivBack);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvChangePicture = findViewById(R.id.tvChangePicture);
        tvError = findViewById(R.id.tvError);
    }

    //Upload Image
    private Uri mUri;
    private ProgressDialog mProgessDialog;
    public static final int MY_REQUEST_CODE = 100;
    public static final String TAG = EditProfileActivity.class.getName();
    public static String[] storge_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }

    private void CheckPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
        } else {
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLaucher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private ActivityResultLauncher<Intent> mActivityResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            ivAvatar.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
}
