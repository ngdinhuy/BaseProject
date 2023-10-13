package com.example.fashionapp.utils

import android.content.Context
import android.widget.Toast

class Utils {
    companion object{
        fun validatePassword(password: String): String? {
            if (password.length < 8 || password.length > 20) {
                return "Password must be less than 20 and more than 8 characters"
            }
            val upperCaseChars = "(.*[A-Z].*)"
            if (!password.matches(upperCaseChars.toRegex())) {
                return "Password must have atleast one uppercase character"
            }
            val lowerCaseChars = "(.*[a-z].*)"
            if (!password.matches(lowerCaseChars.toRegex())) {
                return "Password must have atleast one lowercase character"
            }
            val numbers = "(.*[0-9].*)"
            if (!password.matches(numbers.toRegex())) {
                return "Password must have atleast one number"
            }
            val specialChars = "(.*[@,#,$,%].*$)"
            return if (!password.matches(specialChars.toRegex())) {
                "Password must have atleast one special character"
            } else null
        }
    }
}

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Double.get2digit(): Double {
    return String.format(("%.2f"), this).toDouble()
}

