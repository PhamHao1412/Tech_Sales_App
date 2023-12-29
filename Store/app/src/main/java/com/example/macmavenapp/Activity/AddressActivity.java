package com.example.macmavenapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.macmavenapp.Model.Address;
import com.example.macmavenapp.R;
import com.example.macmavenapp.Somethings.ObjectSharedPreferences;

public class AddressActivity extends AppCompatActivity {
    ImageView ivBack;
    EditText etFullName, etPhoneNumber, etAddress;

    Button saveAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        AnhXa();
        ivBackClick();
        LoadInformation();
        saveAddressClick();
    }

    private void saveAddressClick() {
        saveAddress.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etFullName.getText().toString())){
                Toast.makeText(AddressActivity.this.getApplicationContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                etFullName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(etPhoneNumber.getText().toString())){
                Toast.makeText(AddressActivity.this.getApplicationContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                etPhoneNumber.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(etAddress.getText().toString())){
                Toast.makeText(AddressActivity.this.getApplicationContext(), "Please enter your address", Toast.LENGTH_SHORT).show();
                etAddress.requestFocus();
                return;
            }
            Address address = new Address(etFullName.getText().toString(), etPhoneNumber.getText().toString(), etAddress.getText().toString());
            ObjectSharedPreferences.saveObjectToSharedPreference(AddressActivity.this, "address", "MODE_PRIVATE", address);
            startActivity(new Intent(AddressActivity.this, CheckOutActivity.class));
            finish();
        });
    }

    private void LoadInformation() {
        Address address = ObjectSharedPreferences.getSavedObjectFromPreference(AddressActivity.this, "address", "MODE_PRIVATE", Address.class);
        if(address != null){
            etFullName.setText(address.getFullName());
            etPhoneNumber.setText(address.getPhoneNumber());
            etAddress.setText(address.getAddress());
        }
    }

    private void ivBackClick() {
        ivBack.setOnClickListener(v -> {
            startActivity(new Intent(AddressActivity.this, CheckOutActivity.class));
        });

    }

    private void AnhXa() {
        ivBack = findViewById(R.id.ivBack);
        etFullName = findViewById(R.id.etNewPass);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        saveAddress = findViewById(R.id.saveAddress);
    }
}
