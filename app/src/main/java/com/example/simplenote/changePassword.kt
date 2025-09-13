package com.example.simplenote

import ChangePasswordViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplenote.settingschangepasswordblank.Box
import com.example.simplenote.settingschangepasswordblank.Button
import com.example.simplenote.settingschangepasswordblank.Caption
import com.example.simplenote.settingschangepasswordblank.Divider
import com.example.simplenote.settingschangepasswordblank.Icon
import com.example.simplenote.settingschangepasswordblank.Icon1
import com.example.simplenote.settingschangepasswordblank.IconOutlineArrowRight
import com.example.simplenote.settingschangepasswordblank.IconSolidCheveronLeft
import com.example.simplenote.settingschangepasswordblank.InputField
import com.example.simplenote.settingschangepasswordblank.InputField1
import com.example.simplenote.settingschangepasswordblank.InputField2
import com.example.simplenote.settingschangepasswordblank.InputFields
import com.example.simplenote.settingschangepasswordblank.Label
import com.example.simplenote.settingschangepasswordblank.Label1
import com.example.simplenote.settingschangepasswordblank.Label2
import com.example.simplenote.settingschangepasswordblank.Link
import com.example.simplenote.settingschangepasswordblank.NavBar
import com.example.simplenote.settingschangepasswordblank.NowCreateYourNewPassword
import com.example.simplenote.settingschangepasswordblank.PleaseInputYourCurrentPasswordFirst
import com.example.simplenote.settingschangepasswordblank.Text
import com.example.simplenote.settingschangepasswordblank.Text1
import com.example.simplenote.settingschangepasswordblank.Title
import com.example.simplenote.settingschangepasswordblank.TopLevel

@Composable
fun ChangePasswordActivity(modifier: Modifier = Modifier, onBackSettings: () -> Unit) {

    val viewModel: ChangePasswordViewModel = viewModel()

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPassword2 by remember { mutableStateOf("") }


    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            // TODO: some user notify
            onBackSettings()
        }
    }

    TopLevel(modifier = modifier) {
        Divider(
            modifier = Modifier
                .boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 262.0.dp
                    )
                )
                .rowWeight(1.0f)
        )
        InputFields(
            modifier = Modifier
                .boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 318.0.dp
                    )
                )
                .rowWeight(1.0f)
        ) {
            InputField1(modifier = Modifier.rowWeight(1.0f)) {
                Label(modifier = Modifier.rowWeight(1.0f))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    placeholder = { Text("********") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )
                Caption(modifier = Modifier.rowWeight(1.0f))
            }
            InputField2(modifier = Modifier.rowWeight(1.0f)) {
                Label1(modifier = Modifier.rowWeight(1.0f))
                OutlinedTextField(
                    value = newPassword2,
                    onValueChange = { newPassword2 = it },
                    placeholder = { Text("********") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

            }
            viewModel.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

        }
        Button(
            modifier = Modifier
                .boxAlign(
                    alignment = Alignment.BottomStart,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = -40.0.dp
                    )
                )
                .rowWeight(1.0f)
                .clickable { viewModel.changePassword(oldPassword, newPassword, newPassword2) }
        ) {
            Text(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    ),
                )
            )
            IconOutlineArrowRight(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.CenterEnd,
                    offset = DpOffset(
                        x = -20.0.dp,
                        y = 0.0.dp
                    )
                )
            ) {
                Icon(
                    modifier = Modifier
                        .boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = -1.0.dp,
                                y = -1.0.dp
                            )
                        )
                        .rowWeight(1.0f)
                        .columnWeight(1.0f)
                )
            }
        }

        InputField(
            modifier = Modifier
                .boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 151.0.dp
                    )
                )
                .rowWeight(1.0f)
        ) {
            Label2(modifier = Modifier.rowWeight(1.0f))
            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                placeholder = { Text("********") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        }
        PleaseInputYourCurrentPasswordFirst(
            modifier = Modifier.boxAlign(
                alignment = Alignment.TopStart,
                offset = DpOffset(
                    x = 16.0.dp,
                    y = 120.0.dp
                )
            )
        )
        NowCreateYourNewPassword(
            modifier = Modifier.boxAlign(
                alignment = Alignment.TopStart,
                offset = DpOffset(
                    x = 16.0.dp,
                    y = 287.0.dp
                )
            )
        )
        NavBar(
            modifier = Modifier
                .boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 42.0.dp
                    )
                )
                .rowWeight(1.0f)
        ) {
            Box(modifier = Modifier.rowWeight(1.0f))
            Link(
                modifier = Modifier
                    .boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 16.0.dp,
                            y = 16.0.dp
                        )
                    )
                    .clickable { onBackSettings() }
            ) {
                IconSolidCheveronLeft {
                    Icon1(
                        modifier = Modifier
                            .rowWeight(1.0f)
                            .columnWeight(1.0f)
                    )
                }
                Text1()
            }
            Title(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopCenter,
                    offset = DpOffset(
                        x = 0.5.dp,
                        y = 16.0.dp
                    )
                )
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 780)
@Composable
private fun ChangePasswordActivityPreview() {
    ChangePasswordActivity(modifier = Modifier, {})
}
