package com.example.kuitandroidapiexample.ui.register.screen

import android.R.attr.name
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kuitandroidapiexample.ui.home.viewmodel.AnimalViewModel
import com.example.kuitandroidapiexample.ui.model.AnimalType
import com.example.kuitandroidapiexample.ui.register.componet.FindUTextField
import com.example.kuitandroidapiexample.ui.register.componet.TypeSelectContent
import com.example.kuitandroidapiexample.ui.register.uistate.AnimalAddUiState
import com.example.kuitandroidapiexample.ui.register.viewmodel.AnimalRegisterViewModel
import com.example.kuitandroidapiexample.ui.theme.FindUTheme.colors
import com.example.kuitandroidapiexample.ui.theme.FindUTheme.typography
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    padding: PaddingValues,
    navigateToBack: () -> Unit = {},
    viewModel: AnimalRegisterViewModel = hiltViewModel()
) {
//    var url by remember { mutableStateOf("") }
//    var animalName by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var reporterName by remember { mutableStateOf("") }
//    var animalType by remember { mutableStateOf(AnimalType.PROTECT) }

//    val addAnimal by viewModel.addAnimalState

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHost = remember { SnackbarHostState() }
    val cScope = rememberCoroutineScope()


    LaunchedEffect(uiState.isAdded) {
        if (uiState.isAdded) {
            cScope.launch {

                val result = snackBarHost.showSnackbar(
                    message = "등록이 완료되었습니다.",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
                navigateToBack()
            }


        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(
            modifier = Modifier.matchParentSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "등록하기",
                    modifier = Modifier.align(Alignment.Center),
                    style = typography.semiBold.copy(
                        fontSize = 18.sp
                    )
                )
            }

            FindUTextField(
                modifier = Modifier.padding(top = 21.dp), title = "사진 url 입력", value = uiState.url
            ) {
                viewModel.onUrlChanged(it) }

            FindUTextField(
                modifier = Modifier.padding(top = 15.dp), title = "이름 입력", value = uiState.reporter
            ) { viewModel.onReporterChanged(it) }

            FindUTextField(
                modifier = Modifier.padding(top = 15.dp), title = "주소 입력", value = uiState.address
            ) { viewModel.onAddressChanged(it) }

            FindUTextField(
                modifier = Modifier.padding(top = 15.dp), title = "동물 이름", value = uiState.animalName
            ) { viewModel.onAnimalNameChanged(it) }

            TypeSelectContent(
                modifier = Modifier.padding(start = 16.dp, top = 30.dp), animalType = uiState.animalType
            ) { viewModel.onTypeChanged(it) }

        }

        Button(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter), colors = ButtonDefaults.buttonColors(
                containerColor = colors.orange
            ), shape = RoundedCornerShape(8.dp), onClick = {

                viewModel.postAnimal()


            }) {
            Text(
                text = "등록하기", style = typography.semiBold.copy(fontSize = 18.sp)
            )


        }

        SnackbarHost(
            hostState = snackBarHost,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(padding = PaddingValues())
}