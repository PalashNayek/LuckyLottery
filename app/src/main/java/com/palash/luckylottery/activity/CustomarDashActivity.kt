package com.palash.luckylottery.activity

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.palash.luckylottery.R
import com.palash.luckylottery.databinding.ActivityCustomarDashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomarDashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomarDashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityCustomarDashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        intent?.let {
            if (it.action == Intent.ACTION_VIEW) {
                val navController = findNavController(R.id.nav_graph_customar)
                navController.handleDeepLink(it)
            }
        }
    }
}