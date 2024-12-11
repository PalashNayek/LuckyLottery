package com.palash.luckylottery.fragments.dealer_fragment

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentDealerPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealerPaymentFragment : Fragment() {
    private var _binding: FragmentDealerPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDealerPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //btn buy click...........................
        binding.btnBuy.setOnClickListener {
            showCustomDialog()
        }
        //Back icon click
        binding.backIV.setOnClickListener {
            findNavController().navigate(R.id.action_dealerPaymentFragment_to_dealerHomeFragment)
        }
        //add more btn click
        binding.addMoreBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dealerPaymentFragment_to_dealerHomeFragment)
        }
    }

    private fun showCustomDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_custom)

        // Set animation for the dialog
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation

        // Customize dialog properties
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

        // Handle dialog buttons
        val linearLayoutSelf = dialog.findViewById<LinearLayout>(R.id.llSelf)
        val linearLayoutCustomer = dialog.findViewById<LinearLayout>(R.id.llCustomer)

        //default selected...............
        var ticketOption = ""
        linearLayoutCustomer.setBackgroundResource(R.drawable.select_item_dialogbox_background)
        linearLayoutSelf.setBackgroundResource(R.drawable.dialogbox_background)
        ticketOption = "custo"

        linearLayoutSelf.setOnClickListener {
            linearLayoutSelf.setBackgroundResource(R.drawable.select_item_dialogbox_background)
            linearLayoutCustomer.setBackgroundResource(R.drawable.dialogbox_background)
            ticketOption = "self"
        }
        linearLayoutCustomer.setOnClickListener {
            linearLayoutSelf.setBackgroundResource(R.drawable.dialogbox_background)
            linearLayoutCustomer.setBackgroundResource(R.drawable.select_item_dialogbox_background)
            ticketOption = "custo"
        }
        val okButton = dialog.findViewById<Button>(R.id.chooseButton)
        okButton.setOnClickListener {
            dialog.dismiss()
            Log.d("ticketOptions", ticketOption)
            if (ticketOption =="custo"){
            findNavController().navigate(R.id.action_dealerPaymentFragment_to_paymentFragment)
            }else{
                val upiUri = "upi://pay?pa=example@upi&pn=TestMerchant&mc=1234&tid=1234567890&tr=9876543210&tn=TestPayment&am=1.00&cu=INR"
                triggerTestUPIPayment(upiUri)
            }
        }
        val closeButton = dialog.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun triggerTestUPIPayment(upiUri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(upiUri))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No UPI app found!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}