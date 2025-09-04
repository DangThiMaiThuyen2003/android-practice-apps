package com.example.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryModel extends ViewModel {
    private MutableLiveData<List<String>> history = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<String>> getHistory() {
        return history;
    }

    public void addHistory(String result) {
        List<String> currentHistory = history.getValue();
        if (currentHistory == null) {
            currentHistory = new ArrayList<>();
        }
        currentHistory.add(result);
        history.setValue(currentHistory);
    }

    public void clearHistory() {
        history.setValue(new ArrayList<>());
    }
}
