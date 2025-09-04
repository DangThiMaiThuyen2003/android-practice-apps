package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculator.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ScreenModel screenModel;
    private HistoryModel historyModel;


    private boolean dot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ViewModel
        historyModel = new ViewModelProvider(this).get(HistoryModel.class);
        screenModel = new ViewModelProvider(this).get(ScreenModel.class);

        // Quan sát thay đổi text
        screenModel.getString().observe(this, string ->
                binding.tvScreen.setText(string));

        // Quan sát lịch sử
        historyModel.getHistory().observe(this, strings -> {
            String temp = "";
            int n = strings.size();
            if (n == 1) {
                temp = strings.get(0);
            } else if (n > 1) {
                temp = strings.get(n - 2) + " " + strings.get(n - 1);
            }
            binding.tvHistory.setText(temp);
        });
    }

    public void ButtonHandler(View v) {
        String btText = ((Button) v).getText().toString();
        String str = screenModel.getString().getValue();
        char last = (str != null && str.length() > 0) ? str.charAt(str.length() - 1) : '0';

        switch (btText) {
            // ====== Số ======
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                screenModel.addString(btText);
                break;

            // ====== Toán tử ======
            case "+": case "-": case "*": case "/":
                dot = false;
                if (last == '+' || last == '-' || last == '*' || last == '/') {
                    screenModel.removeLast();
                }
                screenModel.addString(btText);
                break;

            // ====== Xóa 1 ký tự ======
            case "+/-":
                // TODO: Implement sign change functionality
                break;
            case "DEL":
                if (last == '.') {
                    dot = false;
                }
                screenModel.removeLast();
                break;

            // ====== Clear ======
            case "C":
                screenModel.clear();
                historyModel.clearHistory();
                break;

            // ====== Kết quả ======
            case "=":
                if (last == '+' || last == '-' || last == '*' || last == '/') {
                    calculatorWarning("Lỗi: Toán tử " + last + " ở cuối");
                } else if (str != null && str.length() > 0) {
                    dot = false;
                    solve();
                    screenModel.clear();
                } else {
                    historyModel.addHistory("0");
                    screenModel.clear();
                }
                break;

            // ====== Dấu chấm ======
            case ".":
                if (!dot) {
                    dot = true;
                    screenModel.addString(btText);
                } else {
                    calculatorWarning("Đã tồn tại dấu thập phân");
                }
                break;
        }
    }

    private void calculatorWarning(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void solve() {
        String str = screenModel.getString().getValue();
        if (str == null || str.isEmpty()) return;

        try {
            double result = evaluateExpression(str);
            historyModel.addHistory(str + " = " + result);
        } catch (Exception e) {
            calculatorWarning("Lỗi tính toán");
        }
    }

    private double evaluateExpression(String expression) {
        // Đơn giản hóa: chỉ xử lý các phép toán cơ bản
        String[] parts = expression.split("(?<=[-+*/])|(?=[-+*/])");
        if (parts.length < 3) {
            return Double.parseDouble(expression);
        }

        double result = Double.parseDouble(parts[0]);
        for (int i = 1; i < parts.length; i += 2) {
            if (i + 1 < parts.length) {
                String operator = parts[i];
                double operand = Double.parseDouble(parts[i + 1]);
                
                switch (operator) {
                    case "+":
                        result += operand;
                        break;
                    case "-":
                        result -= operand;
                        break;
                    case "*":
                        result *= operand;
                        break;
                    case "/":
                        if (operand != 0) {
                            result /= operand;
                        } else {
                            throw new ArithmeticException("Chia cho 0");
                        }
                        break;
                }
            }
        }
        return result;
    }
}
