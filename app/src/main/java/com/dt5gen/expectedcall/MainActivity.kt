package com.dt5gen.expectedcall

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dt5gen.expectedcall.receivers.CallReceiver
import com.dt5gen.expectedcall.ui.theme.ExpectedCallTheme

class MainActivity : ComponentActivity() {

    private lateinit var callReceiver: CallReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Запрос разрешения на READ_PHONE_STATE
        requestPhoneStatePermission()

        enableEdgeToEdge()
        setContent {
            ExpectedCallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Sanyok",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    // Проверка и запрос разрешения
    private fun requestPhoneStatePermission() {
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("MainActivity", "READ_PHONE_STATE permission granted")
                // Инициализация CallReceiver после получения разрешения
                callReceiver = CallReceiver(applicationContext)
            } else {
                Log.e("MainActivity", "READ_PHONE_STATE permission denied")
            }
        }

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.READ_PHONE_STATE)
        } else {
            // Разрешение уже предоставлено, инициализируем CallReceiver
            callReceiver = CallReceiver(applicationContext)
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ExpectedCallTheme {
            Greeting("Android")
        }
    }
}
