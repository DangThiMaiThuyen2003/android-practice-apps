package com.example.contactapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Contact.class, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();

    private static AppDatabase instance;
    
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context, 
                AppDatabase.class,
                "contacts"
            )
            .fallbackToDestructiveMigration() // Xóa database cũ nếu schema thay đổi
            .build();
        }
        return instance;
    }
}
