package com.palash.luckylottery.view_models

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.palash.luckylottery.repositories.PhoneAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(private val repository: PhoneAuthRepository) : ViewModel() {

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    fun updatePhoneNumber(fullPhoneNumber: String) {
        _phoneNumber.value = fullPhoneNumber
    }

    ///
    val verificationId: LiveData<String?> = repository.verificationId
    val authResult: LiveData<FirebaseUser?> = repository.authResult

    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        repository.sendVerificationCode(phoneNumber, activity)
    }

    fun verifyCode(code: String) {
        repository.verifyCode(code)
    }
}