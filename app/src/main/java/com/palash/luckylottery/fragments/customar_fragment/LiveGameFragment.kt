package com.palash.luckylottery.fragments.customar_fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.FragmentLiveGameBinding
import com.palash.luckylottery.models.LiveGameResponse
import com.palash.luckylottery.sping_view.SpinningWheelView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LiveGameFragment : Fragment() {
    private var _binding: FragmentLiveGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var spinningWheel: SpinningWheelView
    private var currentAngle = 0f

    /*val number = 201063
    val numberString = number.toString()*/

    var result = ""
    var txtPrizeName: TextView? = null
    var txt1: TextView? = null
    var txt2: TextView? = null
    var txt3: TextView? = null
    var txt4: TextView? = null
    var txt5: TextView? = null
    var txt6: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLiveGameBinding.inflate(inflater, container, false)
        txtPrizeName = binding.txtPrizeName
        txt1 = binding.digit1
        txt2 = binding.digit2
        txt3 = binding.digit3
        txt4 = binding.digit4
        txt5 = binding.digit5
        txt6 = binding.digit6
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prizeList = listOf(
            /*LiveGameResponse("1", "1st prize to win", "123654"),
            LiveGameResponse("2", "2nd prize to win", "852789"),*/
            LiveGameResponse("3", "3rd prize to win", "0"),
            LiveGameResponse("4", "4th prize to win", "0"),
            LiveGameResponse("5", "5th prize to win", "074123")
        )
        spinningWheel = binding.spinningWheel
        lifecycleScope.launch {
            for (task in prizeList) {

                if (task.luckyNumber=="0"){
                    //lifecycleScope.launch {
                    val textViews = arrayOf(
                        binding.digit1,
                        binding.digit2,
                        binding.digit3,
                        binding.digit4,
                        binding.digit5,
                        binding.digit6
                    )
                    for (i in 0..5) {
                        spinWheel {
                            val selectedNumber = calculateSelectedNumber()
                            textViews[i].text = selectedNumber.toString()
                        }
                        delay(10000)
                    }
                    //}
                    //
                }



                /*if (luckyNumber=="0"){
                    txtPrizeName!!.text = task.prizeName
                    Log.d("TR", task.prizeName)

                    Log.d("MyTT", luckyNumber)
                    txtPrizeName!!.text = ""
                }*/

                /*if (luckyNumber == "0") {
                     */
                /*else {
                    lifecycleScope.launch {
                        var i: Int = 0
                        val textViews = arrayOf(
                            binding.digit1,
                            binding.digit2,
                            binding.digit3,
                            binding.digit4,
                            binding.digit5,
                            binding.digit6
                        )
                        for (digit in luckyNumber) {
                            //println("MyPK "+digit.digitToInt()) // Print current number
                            spinToTarget(digit.digitToInt())
                            // println("MyPK1 "+digit.digitToInt())
                            delay(5000) // Non-blocking delay
                            textViews[i].text = digit.digitToInt().toString()
                            i++
                        }
                        val txt1 = binding.digit1.text.toString()
                        val txt2 = binding.digit2.text.toString()
                        val txt3 = binding.digit3.text.toString()
                        val txt4 = binding.digit4.text.toString()
                        val txt5 = binding.digit5.text.toString()
                        val txt6 = binding.digit6.text.toString()
                        if (txt6.isNotEmpty()) {
                            result = txt1 + txt2 + txt3 + txt4 + txt5 + txt6
                            Log.d("MyLock2", result)
                        }
                    }
                }*/

                delay(10000)
                txt1!!.text=""
                txt2!!.text=""
                txt3!!.text=""
                txt4!!.text=""
                txt5!!.text=""
                txt6!!.text=""
            }
        }


    }

    private fun spinToTarget(targetNumber: Int) {
        //if (targetNumber >= textViews.size) return
        val segmentCount = 10 // Total segments (0-9)
        val anglePerSegment = 360f / segmentCount // Each segment spans 36 degrees

        // Calculate target angle to align segment with pointer
        val targetAngle = 360f - (anglePerSegment * (targetNumber + 1)) + (anglePerSegment / 2)

        // Add multiple rotations for realistic spin
        val fullRotations = 5 // Number of full spins
        val finalRotation = (360 * fullRotations) + targetAngle

        // Create the spin animation
        val spinAnimation = RotateAnimation(
            0f, finalRotation,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 3000 // Spin duration in milliseconds
            fillAfter = true // Retain the position after animation
            interpolator = DecelerateInterpolator() // Slow down at the end
        }

        spinAnimation.setAnimationListener(object :
            android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {
                //spinButton.isEnabled = false // Disable button during spin
            }

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                //
            }

            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
        })

        spinningWheel.startAnimation(spinAnimation)
    }

    private fun calculateSelectedNumber(): Int {
        val anglePerSegment = 360 / 10
        val normalizedAngle = (360 - (currentAngle % 360)).toInt() // Reverse rotation
        return (normalizedAngle / anglePerSegment) % 10
    }

    private fun spinWheel(onEnd: () -> Unit) {
        val randomAngle = (720..3600).random().toFloat() // Random spins (2 to 10 full rotations)
        val targetAngle = currentAngle + randomAngle

        val animator = ObjectAnimator.ofFloat(spinningWheel, "rotation", currentAngle, targetAngle)
        animator.duration = 5000
        animator.interpolator = DecelerateInterpolator()
        animator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationRepeat(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                currentAngle = targetAngle % 360
                //onRestart()
                onEnd()
            }
        })
        animator.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}