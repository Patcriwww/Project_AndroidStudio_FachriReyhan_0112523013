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
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.a0112523013_fachrireyhan_p1.navigation.StudentDetailRoute
import com.example.a0112523013_fachrireyhan_p1.navigation.StudentFormRoute
import com.example.a0112523013_fachrireyhan_p1.navigation.StudentListRoute
import com.example.a0112523013_fachrireyhan_p1.ui.StudentDetailScreen
import com.example.a0112523013_fachrireyhan_p1.ui.StudentFormScreen
import com.example.a0112523013_fachrireyhan_p1.ui.StudentListScreen
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

private val NavyPrimary = Color(0xFF2B3A4F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContent { StudentProfileApp() }
    }
}

@Composable
fun StudentProfileApp(model: StudentViewModel = viewModel()) {
    val navController = rememberNavController()
    // Snackbar di level aplikasi: tidak ikut hilang ketika form ditutup.
    val successSnackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    MaterialTheme(colorScheme = lightColorScheme(primary = NavyPrimary)) {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = StudentListRoute,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<StudentListRoute> {
                    StudentListScreen(
                        model = model,
                        onStudentClick = { id -> navController.navigate(StudentDetailRoute(id)) },
                        onAddClick = { navController.navigate(StudentFormRoute()) }
                    )
                }
                composable<StudentDetailRoute> { entry ->
                    val route = entry.toRoute<StudentDetailRoute>()
                    StudentDetailScreen(
                        model = model,
                        studentId = route.studentId,
                        onBack = { navController.popBackStack() },
                        onEdit = { navController.navigate(StudentFormRoute(route.studentId)) }
                    )
                }
                composable<StudentFormRoute> { entry ->
                    val route = entry.toRoute<StudentFormRoute>()
                    StudentFormScreen(
                        model = model,
                        editingId = route.studentId.takeIf { it > 0 },
                        onBack = { navController.popBackStack() },
                        onSaved = { message ->
                            // Kembali secara benar ke halaman sebelumnya, tanpa fake back.
                            navController.popBackStack()
                            // Tampil di atas halaman List atau Detail setelah kembali.
                            scope.launch {
                                successSnackbar.showSnackbar(
                                    message = message,
                                    duration = SnackbarDuration.Long,
                                    withDismissAction = true
                                )
                            }
                        }
                    )
                }
            }
            SnackbarHost(
                hostState = successSnackbar,
                modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()
            )
        }
    }
}
