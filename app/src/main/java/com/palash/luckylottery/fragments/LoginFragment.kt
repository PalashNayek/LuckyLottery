package com.palash.luckylottery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentIntroBinding
import com.palash.luckylottery.databinding.FragmentLoginBinding
import com.palash.luckylottery.hideKeyboard
import com.palash.luckylottery.token_manager.TokenManager
import com.palash.luckylottery.view_models.PhoneNumberViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

    private val phoneNumberViewModel: PhoneNumberViewModel by viewModels()
    private var mobileNo: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryCodePicker = binding.countryCodePicker
        val phoneNumberInput = binding.phoneNumberInput
        val txtError = binding.txtError
        val sendOTPBtn = binding.btnLogin
        val constant = binding.constraintsLayout
        //

        // Combine the country code and phone number
        val fullPhoneNumberLiveData = MutableLiveData<String>()
        countryCodePicker.registerCarrierNumberEditText(phoneNumberInput)
        phoneNumberInput.addTextChangedListener {
            fullPhoneNumberLiveData.value = countryCodePicker.fullNumberWithPlus
        }

        fullPhoneNumberLiveData.observe(viewLifecycleOwner) { fullPhoneNumber ->
            phoneNumberViewModel.updatePhoneNumber(fullPhoneNumber)
        }


        // Example of observing the phone number in the ViewModel
        phoneNumberViewModel.phoneNumber.observe(viewLifecycleOwner) { fullPhoneNumber ->
            if (fullPhoneNumber.length <= 12) {
                txtError.visibility = View.VISIBLE
                txtError.text = "Please enter valid mobile number"
                constant.visibility = View.GONE
            } else {
                hideKeyboard(phoneNumberInput)
                txtError.visibility = View.GONE
                constant.visibility = View.VISIBLE
                mobileNo = fullPhoneNumber
            }
        }

        sendOTPBtn.setOnClickListener {
            //phoneNumberViewModel.sendVerificationCode("+918617626957", requireActivity())
            //findNavController().navigate(R.id.action_loginFragment_to_customarDashActivity)

            //findNavController().navigate(R.id.action_loginFragment_to_dealerActivity)
            if (mobileNo =="+919800771774"){
                tokenManager.saveToken("dealer")
                findNavController().navigate(R.id.action_loginFragment_to_dealerActivity)
            }else if (mobileNo =="+919933388857"){
                tokenManager.saveToken("sub-dealer")
            }else{
                tokenManager.saveToken("customer")
            }
            //sendOTPBtn.visibility = View.GONE
            //binding.constraintsLayout1.visibility = View.VISIBLE
            Log.d("PhoneNumberFragment", "Full Phone Number: $mobileNo")
        }
        // Observe verification ID
        phoneNumberViewModel.verificationId.observe(viewLifecycleOwner) { verificationId ->
            // Use verificationId if needed
            Log.d("PhoneNumberFragment", "OTP - : $verificationId")
        }

        /*verifyCodeButton.setOnClickListener {
            val code = verificationCodeEditText.text.toString()
            viewModel.verifyCode(code)
        }*/

        // Observe authentication result
        phoneNumberViewModel.authResult.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                // Navigate to next screen
                //findNavController().navigate(R.id.action_loginFragment_to_customarDashActivity)
                //findNavController().navigate(R.id.action_loginFragment_to_dealerActivity)
                Log.d("PhoneAuth", "User authenticated: ${user.uid}")
            }
        }

        //Continue button click .....
        binding.constraintsLayout1.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}