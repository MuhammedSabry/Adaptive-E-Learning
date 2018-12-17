package com.eng.asu.adaptivelearning;

import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

public class EditTextWatcher implements TextWatcher {
    private final TextInputLayout field;

    /**
     * Saves the TextInputLayout associated with the EditableText
     */
    public EditTextWatcher(TextInputLayout field) {
        this.field = field;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        field.setError(null);
        field.setErrorEnabled(false);
    }
}
