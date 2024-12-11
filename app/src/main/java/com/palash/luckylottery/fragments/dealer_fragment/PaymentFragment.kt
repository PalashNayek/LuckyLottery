package com.palash.luckylottery.fragments.dealer_fragment

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private var _binding : FragmentPaymentBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backIV.setOnClickListener {
            findNavController().navigate(R.id.action_paymentFragment_to_dealerPaymentFragment)
        }
        binding.paymentBtn.setOnClickListener {
            //triggerTestUPIPayment("upi://pay?pa=example@upi&pn=TestMerchant&mc=1234&tid=1234567890&tr=9876543210&tn=TestPayment&am=1.00&cu=INR")
            val upiUri = "upi://pay?pa=example@upi&pn=TestMerchant&mc=1234&tid=1234567890&tr=9876543210&tn=TestPayment&am=1.00&cu=INR"
            triggerTestUPIPayment(upiUri)
        //showUPIAppsDialog(requireContext(), upiUri)
        }
    }

    private fun triggerTestUPIPayment(upiUri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(upiUri))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No UPI app found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAvailableUPIApps(context: Context): List<ResolveInfo> {
        val upiIntent = Intent(Intent.ACTION_VIEW, Uri.parse("upi://pay"))
        return context.packageManager.queryIntentActivities(upiIntent, PackageManager.MATCH_DEFAULT_ONLY)
    }

    fun showUPIAppsDialog(context: Context, upiUri: String) {
        val upiApps = getAvailableUPIApps(context)

        if (upiApps.isEmpty()) {
            Toast.makeText(context, "No UPI apps found!", Toast.LENGTH_SHORT).show()
            return
        }

        // Extract app names and packages
        val appNames = upiApps.map { it.loadLabel(context.packageManager).toString() }
        val appPackages = upiApps.map { it.activityInfo.packageName }

        // Show a dialog with app options
        AlertDialog.Builder(context)
            .setTitle("Choose a UPI App")
            .setItems(appNames.toTypedArray()) { _, which ->
                val selectedAppPackage = appPackages[which]
                launchUPIPayment(context, upiUri, selectedAppPackage)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun launchUPIPayment(context: Context, upiUri: String, appPackageName: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(upiUri))
        intent.setPackage(appPackageName) // Restrict the intent to the selected app
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to launch UPI app!", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}