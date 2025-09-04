package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContacts;
    private FloatingActionButton btnAdd;
    private SearchView searchView;
    private Toolbar toolbar;
    private ArrayList<Contact> contactList;
    private ArrayList<Contact> filteredContactList;
    private ContactsAdapter contactAdapter;
    private AppDatabase database;

    private ActivityResultLauncher<Intent> newContactLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK) {
                // Refresh danh sách khi thêm contact mới
                loadContacts();
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo database
        database = AppDatabase.getInstance(this);

        // Khởi tạo views
        initViews();
        
        // Thiết lập toolbar
        setupToolbar();
        
        // Thiết lập RecyclerView
        setupRecyclerView();
        
        // Thiết lập listeners
        setupListeners();
        
        // Load dữ liệu
        loadContacts();
    }

    private void initViews() {
        rvContacts = findViewById(R.id.rv_contacts);
        btnAdd = findViewById(R.id.btn_add);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        contactList = new ArrayList<>();
        filteredContactList = new ArrayList<>();
        contactAdapter = new ContactsAdapter(filteredContactList);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(contactAdapter);
    }

    private void setupListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
            newContactLauncher.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterContacts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterContacts(newText);
                return true;
            }
        });
        
        return true;
    }

    private void loadContacts() {
        new Thread(() -> {
            ArrayList<Contact> contacts = (ArrayList<Contact>) database.contactDao().getAll();
            
            runOnUiThread(() -> {
                contactList.clear();
                contactList.addAll(contacts);
                filteredContactList.clear();
                filteredContactList.addAll(contacts);
                contactAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void filterContacts(String query) {
        filteredContactList.clear();
        
        if (query.isEmpty()) {
            filteredContactList.addAll(contactList);
        } else {
            for (Contact contact : contactList) {
                if (contact.getName().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getPhone().contains(query) ||
                    contact.getEmail().toLowerCase().contains(query.toLowerCase())) {
                    filteredContactList.add(contact);
                }
            }
        }
        
        contactAdapter.notifyDataSetChanged();
    }
}
