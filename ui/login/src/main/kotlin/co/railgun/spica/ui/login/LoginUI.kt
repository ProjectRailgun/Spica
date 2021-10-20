package co.railgun.spica.ui.login

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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import co.railgun.spica.ui.component.AppBarAction
import co.railgun.spica.ui.component.HeightSpacer
import co.railgun.spica.ui.component.OnSubmitAction
import co.railgun.spica.ui.component.PasswordTextField
import co.railgun.spica.ui.component.SpicaTopAppBar
import co.railgun.spica.ui.component.TextFieldState
import co.railgun.spica.ui.component.autofill
import co.railgun.spica.ui.component.rememberTextFieldState
import co.railgun.spica.ui.navigation.navigateToHome
import com.google.accompanist.insets.ui.Scaffold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewLoginUI() {
    ProvideSpicaPreviewContainer {
        LoginUI(
            uiState = LoginUIState.Empty,
            onSubmitAction = {}
        )
    }
}

@Composable
fun LoginUI(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = viewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val uiState: LoginUIState by viewModel.uiState.collectAsState()
    LoginUI(
        scaffoldState = scaffoldState,
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

@Composable
private fun LoginUI(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    uiState: LoginUIState,
    onSubmitAction: OnSubmitLoginAction,
) {
    LaunchedEffect(uiState) {
        if (uiState.logged) onSubmitAction(LoginAction.Logged)
        if (uiState.error !is LoginUIState.Error.Message) return@LaunchedEffect
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(uiState.error.message)
        }
    }
    Scaffold(
        modifier = run {
            Modifier
                .fillMaxSize()
        },
        scaffoldState = scaffoldState,
        topBar = {
            SpicaTopAppBar(
                elevation = 0.dp,
                actions = {
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
            )
        },
    ) { innerPadding ->
        LoginContent(
            modifier = Modifier.padding(innerPadding),
            onSubmitAction = onSubmitAction,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
        keyboardController?.hide()
        onSubmitAction(
            LoginAction.Login(
                username = username,
                password = passwordTextFieldState.text,
            )
        )
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
            contentDescription = null
        )
        OutlinedTextField(
            modifier = run {
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester = focusRequest)
                    .autofill(
                        autofillTypes = listOf(AutofillType.Username),
                        onFill = { username = it }
                    )
            },
            label = { Text(text = "Username") },
            value = username,
            onValueChange = { username = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onDone = { focusRequest.requestFocus() }
            )
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
                        onFill = { passwordTextFieldState.text = it }
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
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}
