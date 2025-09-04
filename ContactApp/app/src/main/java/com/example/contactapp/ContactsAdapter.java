package com.example.contactapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Random;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private ArrayList<Contact> contactList;
    private int[] avatarColors = {
        Color.parseColor("#2196F3"), // blue
        Color.parseColor("#9C27B0"), // purple
        Color.parseColor("#4CAF50"), // green
        Color.parseColor("#FF9800"), // orange
        Color.parseColor("#F44336"), // red
        Color.parseColor("#E91E63"), // pink
        Color.parseColor("#009688"), // teal
        Color.parseColor("#3F51B5")  // indigo
    };

    public ContactsAdapter(ArrayList<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.tvName.setText(contact.getName());
        
        // Tạo avatar với chữ cái đầu
        String name = contact.getName();
        if (!name.isEmpty()) {
            String initial = name.substring(0, 1).toUpperCase();
            holder.tvInitial.setText(initial);
            
            // Chọn màu ngẫu nhiên dựa trên tên
            int colorIndex = Math.abs(name.hashCode()) % avatarColors.length;
            holder.ivAvatar.setBackgroundColor(avatarColors[colorIndex]);
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPhone, tvEmail, tvInitial;
        public ShapeableImageView ivAvatar;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvPhone = (TextView) view.findViewById(R.id.tv_phone);
            tvEmail = (TextView) view.findViewById(R.id.tv_email);
            ivAvatar = (ShapeableImageView) view.findViewById(R.id.iv_avatar);
            tvInitial = (TextView) view.findViewById(R.id.tv_initial);
        }
    }
}
