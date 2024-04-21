package com.example.bankee;

import android.text.InputType;
import android.widget.EditText;

public class Utility {
    public static void togglePasswordVisibility(EditText editTextPassword) {
        int inputType = editTextPassword.getInputType();

        if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            // Password is visible, so hide it
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            // Password is hidden, so show it
            editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

        // Move cursor to the end of the text
        editTextPassword.setSelection(editTextPassword.getText().length());
    }
}
