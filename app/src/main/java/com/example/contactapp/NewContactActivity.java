package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public class NewContactActivity extends AppCompatActivity {

    private TextInputEditText etFirstName, etLastName, etPhone, etEmail;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        // Khởi tạo database
        database = AppDatabase.getInstance(this);

        // Thiết lập toolbar
        setupToolbar();

        // Khởi tạo views
        initViews();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_close_24);
        }
    }

    private void initViews() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveContact();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveContact() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Tạo tên đầy đủ
        String fullName = (firstName + " " + lastName).trim();
        if (fullName.isEmpty()) {
            etFirstName.setError("Vui lòng nhập tên");
            return;
        }

        // Kiểm tra số điện thoại
        if (phone.isEmpty()) {
            etPhone.setError("Vui lòng nhập số điện thoại");
            return;
        }

        // Tạo contact mới
        Contact newContact = new Contact(fullName, phone, email);

        // Lưu vào database trong background thread
        new Thread(() -> {
            database.contactDao().insert(newContact);
            
            // Quay về main thread để hiển thị thông báo
            runOnUiThread(() -> {
                Toast.makeText(NewContactActivity.this, 
                    "Đã thêm liên hệ thành công!", Toast.LENGTH_SHORT).show();
                
                // Trả về kết quả cho MainActivity
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        }).start();
    }
}
