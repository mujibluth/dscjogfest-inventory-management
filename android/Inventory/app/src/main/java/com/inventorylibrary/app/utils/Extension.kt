package com.inventorylibrary.app.utils

import android.text.Editable
import com.google.android.material.textfield.TextInputLayout


fun isValidForm(text: Editable?, layout: TextInputLayout): Boolean{
    return if (text.isNullOrEmpty()){
        layout.error = "Form tidak boleh kosong"
        false
    } else true
}