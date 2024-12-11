package com.palash.luckylottery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentIntroBinding
import com.palash.luckylottery.token_manager.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
       /* val token = tokenManager.getToken()
        Log.d("MyToken", token.toString())*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MyTAG-token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            //val msg = getString(R.string.msg_token_fmt, token)
            Log.d("MyTAG-token", token)
           // Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
        })

        binding.btnStarted.setOnClickListener {
            //get token ................

            if (tokenManager.getToken() != null) {
                val token = tokenManager.getToken()
                if (token=="dealer"){
                    findNavController().navigate(R.id.action_introFragment_to_dealerActivity)
                }else if (token=="customer"){
                    findNavController().navigate(R.id.action_introFragment_to_customarDashActivity)
                }else{
                    //findNavController().navigate(R.id.action_introFragment_to_loginFragment)
                }
            }else{
                findNavController().navigate(R.id.action_introFragment_to_loginFragment)
            }
            //findNavController().navigate(R.id.action_introFragment_to_loginFragment)
            //findNavController().navigate(R.id.action_introFragment_to_testingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}