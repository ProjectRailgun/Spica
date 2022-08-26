package co.railgun.spica.ui.login

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.railgun.spica.ui.ProvideSpicaPreviewContainer
import co.railgun.spica.ui.common.resources.R
import co.railgun.spica.ui.component.AppBarAction
import co.railgun.spica.ui.component.HeightSpacer
import co.railgun.spica.ui.component.OnSubmitAction
import co.railgun.spica.ui.component.PasswordTextField
import co.railgun.spica.ui.component.RequestedOrientation
import co.railgun.spica.ui.component.SpicaTopAppBar
import co.railgun.spica.ui.component.TextFieldState
import co.railgun.spica.ui.component.autofill
import co.railgun.spica.ui.component.rememberTextFieldState
import co.railgun.spica.ui.navigation.navigateToHome

@Preview
@Composable
fun PreviewLoginUI() {
    ProvideSpicaPreviewContainer {
        LoginUI(
            uiState = LoginUIState.Empty,
            onSubmitAction = {},
        )
    }
}

@Composable
fun LoginUI(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = viewModel(),
) {
    val uiState: LoginUIState by viewModel.uiState.collectAsState()
    RequestedOrientation(screenOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT)
    LoginUI(
        uiState = uiState,
        onSubmitAction = { action ->
            when (action) {
                LoginAction.Logged -> navController.navigateToHome()
                else -> viewModel.submitAction(action)
            }
        },
    )
}

private typealias OnSubmitLoginAction = OnSubmitAction<LoginAction>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginUI(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    uiState: LoginUIState,
    onSubmitAction: OnSubmitLoginAction,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState) {
        if (uiState.logged) onSubmitAction(LoginAction.Logged)
        uiState.loginError?.let { uiError ->
            snackbarHostState.showSnackbar(uiError.message)
        }
    }
    Scaffold(
        modifier = run {
            Modifier
                .fillMaxSize()
        },
        topBar = {
            SpicaTopAppBar {
                AppBarAction(
                    imageVector = when {
                        isDarkTheme -> Icons.Rounded.LightMode
                        else -> Icons.Rounded.DarkMode
                    },
                    onClick = {
                        val mode = when {
                            isDarkTheme -> AppCompatDelegate.MODE_NIGHT_NO
                            else -> AppCompatDelegate.MODE_NIGHT_YES
                        }
                        AppCompatDelegate.setDefaultNightMode(mode)
                    },
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        LoginContent(
            modifier = Modifier.padding(innerPadding),
            onSubmitAction = onSubmitAction,
        )
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    focusRequest: FocusRequester = remember { FocusRequester() },
    passwordTextFieldState: TextFieldState = rememberTextFieldState(
        errorFor = { "Invalid password" },
    ),
    onSubmitAction: OnSubmitLoginAction,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var username: String by remember { mutableStateOf("") }
    val onSubmitLoginAction = {
        if (username.isNotBlank() && passwordTextFieldState.text.isNotBlank()) {
            keyboardController?.hide()
            onSubmitAction(
                LoginAction.Login(
                    username = username,
                    password = passwordTextFieldState.text,
                ),
            )
        }
    }
    Column(
        modifier = run {
            modifier
                .fillMaxSize()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = run {
                Modifier
                    .size(size = 240.dp)
                    .padding(vertical = 56.dp)
            },
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
        )
        OutlinedTextField(
            modifier = run {
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester = focusRequest)
                    .autofill(
                        autofillTypes = listOf(AutofillType.Username),
                        onFill = { username = it },
                    )
            },
            label = { Text(text = "Username") },
            value = username,
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onDone = { focusRequest.requestFocus() },
            ),
        )
        HeightSpacer(height = 16.dp)
        PasswordTextField(
            modifier = run {
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester = focusRequest)
                    .autofill(
                        autofillTypes = listOf(AutofillType.Password),
                        onFill = { passwordTextFieldState.text = it },
                    )
            },
            label = "Password",
            textFieldState = passwordTextFieldState,
            onImeAction = onSubmitLoginAction,
        )
        Button(
            modifier = run {
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp)
            },
            onClick = onSubmitLoginAction,
        ) {
            Text(
                modifier = run {
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        )
                },
                text = "登录",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}
