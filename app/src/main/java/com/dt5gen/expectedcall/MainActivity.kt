package com.dt5gen.expectedcall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dt5gen.expectedcall.ui.screens.SelectContactScreen
import com.dt5gen.expectedcall.ui.theme.ExpectedCallTheme
import com.dt5gen.expectedcall.viewModels.ContactViewModel

class MainActivity : ComponentActivity() {

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Загружаем контакты при старте активности
        contactViewModel.loadContacts(this)

        setContent {
            ExpectedCallTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    // Применяем padding к содержимому
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(paddingValues) // Исправление здесь
                    ) {
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable("selectContact") {
                            SelectContactScreen(
                                viewModel = contactViewModel,
                                onContactSelected = { contact ->
                                    // Сохраняем выбранный контакт и возвращаемся на главный экран
                                    contactViewModel.selectContact(contact, this@MainActivity)
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold { paddingValues ->
        Button(
            onClick = { navController.navigate("selectContact") },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Применяем padding на главном экране
        ) {
            Text("Выбрать контакт")
        }
    }
}