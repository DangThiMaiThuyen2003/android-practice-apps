package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private MyViewModel model;
    private EditText etNumber;
    private Button btnSave;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Khởi tạo ViewModel - sử dụng shared ViewModel từ Application
        MyApplication app = (MyApplication) getApplication();
        model = app.getSharedViewModel();

        // Lấy các view
        etNumber = findViewById(R.id.et_number);
        btnSave = findViewById(R.id.btn_save);

        // Kiểm tra view
        if (etNumber == null || btnSave == null) {
            Toast.makeText(this, "Error: Layout not loaded correctly", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Lấy dữ liệu từ Intent
        position = getIntent().getIntExtra("position", -1);
        String numberStr = getIntent().getStringExtra("number");
        if (numberStr != null) {
            etNumber.setText(numberStr);
        } else {
            etNumber.setText("0"); // Giá trị mặc định
        }

        // Nút lưu giá trị mới
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValueStr = etNumber.getText().toString().trim();
                if (newValueStr.isEmpty()) {
                    etNumber.setError("Please enter a number");
                    return;
                }

                try {
                    int newValue = Integer.parseInt(newValueStr);
                    if (model != null && position >= 0) {
                        model.setNumberAtPosition(position, newValue); // Cập nhật giá trị tại vị trí cụ thể
                        Toast.makeText(DetailsActivity.this, "Value saved: " + newValue, Toast.LENGTH_SHORT).show();
                        finish(); // Đóng activity, quay lại MainActivity
                    } else {
                        Toast.makeText(DetailsActivity.this, "Error: ViewModel issue or invalid position", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    etNumber.setError("Invalid number format");
                }
            }
        });
    }
}