package com.example.a0112523013_fachrireyhan_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a0112523013_fachrireyhan_p1.navigation.StudentAppNavHost
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModelFactory
import kotlinx.coroutines.launch

private val NavyPrimary = Color(0xFF2B3A4F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        setContent { StudentProfileApp() }
    }
}

@Composable
fun StudentProfileApp() {
    val context = LocalContext.current.applicationContext
    val factory = remember(context) { StudentViewModelFactory(context) }
    val model: StudentViewModel = viewModel(factory = factory)
    val state by model.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    MaterialTheme(
        colorScheme = if (state.settings.darkMode) darkColorScheme(
            primary = Color(0xFF9ABBEA)
        ) else lightColorScheme(primary = NavyPrimary)
    ) {
        Box(Modifier.fillMaxSize()) {
            StudentAppNavHost(
                navController = navController,
                model = model,
                onFeedback = { message ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Long,
                            withDismissAction = true
                        )
                    }
                }
            )
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()
            )
        }
    }
}
