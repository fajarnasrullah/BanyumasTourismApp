package com.jer.banyumastourismapp.presentation.auth.login

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.core.verySmallIcon
import com.jer.banyumastourismapp.presentation.auth.AuthViewModel
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    viewModel: AuthViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var pwVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val signinResult by viewModel.signInResult.collectAsState()
    val context = LocalContext.current

     fun signIn() {
        val credentialManager = CredentialManager.create(context)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.web_client_id))
            .build()


        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            fun onSignInFailure(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                when (val credential = result.credential) {
                    is GoogleIdTokenCredential -> {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                            viewModel.signInWithGoogle(googleIdTokenCredential.idToken)
                            Log.d("LoginScreen", "User Success to Sign In ")
                        } catch (e: GoogleIdTokenParsingException) {
                            Log.e("LoginScreen", "Failed, Google ID token parsing is invalid.", e)
                            onSignInFailure("Failed, Google ID token parsing is invalid.")
                        }
                    }
                    else -> {
                        Log.e("LoginScreen", "Unknown credential type.")
                        onSignInFailure("Unknown credential type.")
                    }
                }
            } catch (e: GetCredentialException) {
                Log.e("Error Credential", e.message.toString())
                onSignInFailure(e.message.toString())
            }

        }


    }




    LaunchedEffect(signinResult) {
        signinResult?.let { result ->
            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    navigateToHome()
                }
            } else {
                Log.e("LoginScreen", "Sign in failed: ${result.exceptionOrNull()}")
            }
        }
    }

    LaunchedEffect(Unit) {
        val currentUser = viewModel.getCurrentUser()
        if (currentUser != null) {
            navigateToHome()
        }
    }



    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(30.dp)
    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Welcome Back",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email address", fontSize = 14.sp,) },
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", fontSize = 14.sp) },
            colors = TextFieldDefaults.colors(
                unfocusedLabelColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            visualTransformation = if (pwVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                val icon = if (pwVisible) painterResource(id = R.drawable.visible_icon) else painterResource(id = R.drawable.invisible_icon)
                IconButton(onClick = { pwVisible = !pwVisible }) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        modifier = Modifier.size(verySmallIcon)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))


        Button(
            onClick = {

            },
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "or login with",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)

            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.weight(1f)

            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedButton(
            border = BorderStroke(1.dp, color = Color.LightGray,),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                signIn()
            }
        ) {

            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Row {
            Text(
                text = "Don't have an account? ",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Register",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.clickable {

                }
            )
        }

    }

}



//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun PrevLoginScreen() {
//    BanyumasTourismAppTheme{ LoginScreen() }
//}