package com.example.twonumbers;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etA;
    private EditText etB;
    private Button btnAdd;
    private Button btnSub;
    private Button btnMul;
    private Button btnDiv;
    private ListView lvHistory;

    private final List<String> historyItems = new ArrayList<>();
    private ArrayAdapter<String> historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindViews();
        setupList();
        setupClicks();
    }

    private void bindViews() {
        etA = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        lvHistory = findViewById(R.id.lvHistory);
    }

    private void setupList() {
        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyItems);
        lvHistory.setAdapter(historyAdapter);
    }

    private void setupClicks() {
        btnAdd.setOnClickListener(v -> performOperation("+"));
        btnSub.setOnClickListener(v -> performOperation("-"));
        btnMul.setOnClickListener(v -> performOperation("x"));
        btnDiv.setOnClickListener(v -> performOperation("/"));
    }

    private void performOperation(String operator) {
        Double a = parseNumber(etA.getText().toString().trim());
        Double b = parseNumber(etB.getText().toString().trim());

        if (a == null || b == null) {
            Toast.makeText(this, "Vui lòng nhập số hợp lệ cho a và b", Toast.LENGTH_SHORT).show();
            return;
        }

        double result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "x":
                result = a * b;
                break;
            case "/":
                if (b == 0.0) {
                    Toast.makeText(this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                result = a / b;
                break;
            default:
                Toast.makeText(this, "Phép toán không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
        }

        String record = a + " " + operator + " " + b + " = " + result;
        historyItems.add(0, record);
        historyAdapter.notifyDataSetChanged();
    }

    private Double parseNumber(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}