package com.bsacalculator.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.bsacalculator.app.billing.BillingManager
import com.bsacalculator.app.ui.MainScreen
import com.bsacalculator.app.ui.theme.BSACalculatorTheme
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {

    private lateinit var billingManager: BillingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Mobile Ads SDK
        MobileAds.initialize(this) {}

        billingManager = BillingManager(this)
        billingManager.startConnection()

        setContent {
            BSACalculatorTheme {
                val isAdFree by billingManager.isAdFree.collectAsState()
                MainScreen(
                    billingManager = billingManager,
                    isAdFree = isAdFree
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        billingManager.destroy()
    }
}
