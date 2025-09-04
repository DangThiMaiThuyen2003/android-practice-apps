package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> number;
    private MutableLiveData<ArrayList<Integer>> numbersList;

    public MyViewModel(@NonNull Application application) {
        super(application);
        number = new MutableLiveData<>();
        number.setValue(0); // Giá trị mặc định
        
        numbersList = new MutableLiveData<>();
        numbersList.setValue(new ArrayList<>());
    }

    public LiveData<Integer> getNumbers() {
        return number;
    }
    
    public LiveData<ArrayList<Integer>> getNumbersList() {
        return numbersList;
    }

    public void increaseNumber() {
        Integer currentValue = number.getValue();
        if (currentValue != null) {
            number.setValue(currentValue + 1);
        } else {
            number.setValue(1);
        }
    }

    public void setNumber(int newValue) {
        if (number != null) {
            number.setValue(newValue);
        }
    }
    
    public void setNumberAtPosition(int position, int newValue) {
        ArrayList<Integer> currentList = numbersList.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.set(position, newValue);
            numbersList.setValue(currentList);
            // Cũng cập nhật số hiện tại
            number.setValue(newValue);
        }
    }
    
    public void addNumber(int value) {
        ArrayList<Integer> currentList = numbersList.getValue();
        if (currentList != null) {
            currentList.add(value);
            numbersList.setValue(currentList);
        }
    }
    
    public void removeNumber(int position) {
        ArrayList<Integer> currentList = numbersList.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            numbersList.setValue(currentList);
        }
    }
}