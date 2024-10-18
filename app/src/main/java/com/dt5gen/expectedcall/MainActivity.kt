package com.dt5gen.expectedcall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dt5gen.expectedcall.ui.screens.PermissionScreen
import com.dt5gen.expectedcall.ui.screens.SelectContactScreen
import com.dt5gen.expectedcall.ui.theme.ExpectedCallTheme
import com.dt5gen.expectedcall.viewModels.ContactViewModel

class MainActivity : ComponentActivity() {

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactViewModel.loadContacts(this)

        setContent {
            ExpectedCallTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { NavigationBar(navController) }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("home") {
                            HomeScreen(navController, contactViewModel)
                        }
                        composable("permissions") {
                            PermissionScreen()
                        }
                        composable("selectContact") {
                            SelectContactScreen(
                                viewModel = contactViewModel,
                                onContactSelected = { contact ->
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
fun HomeScreen(navController: NavHostController, contactViewModel: ContactViewModel) {
    Scaffold { paddingValues ->
        Text(
            text = "Выбранные контакты:\n${contactViewModel.selectedContactsText()}",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
fun NavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem(route = "permissions", label = "Разрешения", icon = Icons.Default.Settings),
        NavigationItem(route = "home", label = "Главная", icon = Icons.Default.Home),
        NavigationItem(
            route = "selectContact",
            label = "Контакты",
            icon = Icons.Default.AccountCircle
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)