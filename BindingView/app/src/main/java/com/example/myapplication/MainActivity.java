package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyViewModel model;
    private ArrayAdapter<Integer> arrayAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // ViewModel - sử dụng shared ViewModel từ Application
        MyApplication app = (MyApplication) getApplication();
        model = app.getSharedViewModel();

        // Adapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        binding.lvCount.setAdapter(arrayAdapter);

        // Quan sát số hiện tại
        model.getNumbers().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCount.setText("" + integer);
            }
        });

        // Quan sát danh sách số
        model.getNumbersList().observe(this, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> numbers) {
                arrayAdapter.clear();
                arrayAdapter.addAll(numbers);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        // Nút thêm số
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentValue = model.getNumbers().getValue();
                if (currentValue != null) {
                    model.addNumber(currentValue);
                    model.increaseNumber();
                }
            }
        });

        // Nhấn giữ để xóa item
        binding.lvCount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                model.removeNumber(position);
                return true;
            }
        });

        // Nhấn để mở DetailsActivity
        binding.lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Integer> numbers = model.getNumbersList().getValue();
                if (numbers != null && position >= 0 && position < numbers.size()) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("number", numbers.get(position).toString());
                    startActivity(intent);
                }
            }
        });
    }
}

// Cách hoạt động:
// Khi app khởi động, MyApplication.onCreate() được gọi và tạo một ViewModel instance  một lần và được chia sẻ giữa tất cả Activity
// MainActivity và DetailsActivity đều lấy cùng ViewModel instance từ Application
// Khi DetailsActivity cập nhật giá trị, MainActivity sẽ nhận được thông báo thay đổi ngay lập tức

// MainActivity và DetailsActivity:
// Cả hai Activity đều sử dụng app.getSharedViewModel() thay vì tạo ViewModel riêng
//Điều này đảm bảo chúng sử dụng cùng một instance