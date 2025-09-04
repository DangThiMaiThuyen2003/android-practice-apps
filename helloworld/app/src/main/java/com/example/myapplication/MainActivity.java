package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Method 1: FloatingActionButton
    private TextView tvCount;
    private FloatingActionButton btnAdd, btnMinus;

    //Method 2: android:onClick in XML
    private TextView tvCount2;

    // Method 3: Implement View.OnClickListener
    private TextView tvCount3;
    private FloatingActionButton btnAdd3, btnMinus3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ===== Method 1 =====
        tvCount = findViewById(R.id.tv_count);
        btnAdd = findViewById(R.id.btn_add);
        btnMinus = findViewById(R.id.btn_minus);

        //Count Up
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvCount.getText().toString());
                tvCount.setText("" + ++count);
            }
        });
        //Count Down
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvCount.getText().toString());
                tvCount.setText("" + --count);
            }
        });

        // ===== Method 2 =====
        tvCount2 = findViewById(R.id.tv_count2);
        // Nút add2 và minus2 đã gắn trực tiếp onClick trong XML


        // ===== Method 3 =====
        tvCount3 = findViewById(R.id.tv_count3);
        btnAdd3 = findViewById(R.id.btn_add3);
        btnMinus3 = findViewById(R.id.btn_minus3);

        btnAdd3.setOnClickListener(this);
        btnMinus3.setOnClickListener(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // ===== Method 2 - XML onClick =====
    public void increaseCount2(View view) {
        int count = Integer.parseInt(tvCount2.getText().toString());
        tvCount2.setText("" + ++count);
    }

    public void decreaseCount2(View view) {
        int count = Integer.parseInt(tvCount2.getText().toString());
        tvCount2.setText("" + --count);
    }


    // ===== Method 3 - Implement OnClickListener =====
    @Override
    public void onClick(View v) {
        int count = Integer.parseInt(tvCount3.getText().toString());
        if (v.getId() == R.id.btn_add3) {
            tvCount3.setText("" + ++count);
        } else if (v.getId() == R.id.btn_minus3) {
            tvCount3.setText("" + --count);
        }
    }
}