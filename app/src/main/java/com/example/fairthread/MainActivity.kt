package com.example.fairthread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.fairthread.data.remote.AbstractApiService
import com.example.fairthread.data.repository.AuthRepository
import com.example.fairthread.ui.theme.FairThreadTheme
import com.example.fairthread.viewmodel.AuthViewModel
import com.example.fairthread.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.fairthread.di.AppModule

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // ✅ Retrofit setup for Abstract API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://emailvalidation.abstractapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val abstractApi = retrofit.create(AbstractApiService::class.java)

        // ✅ Inject into AuthRepository and ViewModel
        val authRepo = AuthRepository(abstractApi = abstractApi)
        val authViewModel = AppModule.authViewModel

        setContent {
            FairThreadTheme {
                val navController = rememberNavController()

                // ✅ Pass ViewModel into NavGraph or screens
                NavGraph(navController = navController, uid = uid, authViewModel = authViewModel)
            }
        }
    }
}
