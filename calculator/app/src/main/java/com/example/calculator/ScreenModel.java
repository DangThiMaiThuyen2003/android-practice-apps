package com.example.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScreenModel extends ViewModel {
    private MutableLiveData<String> string = new MutableLiveData<>("");

    public MutableLiveData<String> getString() {
        return string;
    }

    public void addString(String s) {
        String current = string.getValue();
        if (current == null) {
            current = "";
        }
        string.setValue(current + s);
    }

    public void removeLast() {
        String current = string.getValue();
        if (current != null && current.length() > 0) {
            string.setValue(current.substring(0, current.length() - 1));
        }
    }

    public void clear() {
        string.setValue("");
    }
}
