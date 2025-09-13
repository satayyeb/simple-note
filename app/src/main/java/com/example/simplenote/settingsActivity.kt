package com.example.simplenote

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.simplenote.settings.AppSettings
import com.example.simplenote.settings.AppVersion
import com.example.simplenote.settings.Box
import com.example.simplenote.settings.Content
import com.example.simplenote.settings.Divider
import com.example.simplenote.settings.Divider1
import com.example.simplenote.settings.EmailAddress
import com.example.simplenote.settings.EmailAddressIcon
import com.example.simplenote.settings.ExtraMenuSingle
import com.example.simplenote.settings.FullName
import com.example.simplenote.settings.FullNameEmailAddress
import com.example.simplenote.settings.Icon
import com.example.simplenote.settings.Icon1
import com.example.simplenote.settings.Icon2
import com.example.simplenote.settings.Icon3
import com.example.simplenote.settings.Icon4
import com.example.simplenote.settings.IconOutlineCheveronRight
import com.example.simplenote.settings.IconOutlineLockClosed
import com.example.simplenote.settings.IconOutlineLogout
import com.example.simplenote.settings.IconOutlineMail
import com.example.simplenote.settings.IconSolidCheveronLeft
import com.example.simplenote.settings.Label
import com.example.simplenote.settings.Link
import com.example.simplenote.settings.LogOut
import com.example.simplenote.settings.Main
import com.example.simplenote.settings.MenuName
import com.example.simplenote.settings.MenuName1
import com.example.simplenote.settings.Menus
import com.example.simplenote.settings.NavBar
import com.example.simplenote.settings.ProfilePicture
import com.example.simplenote.settings.ProfilePictureFullNameEmailAddress
import com.example.simplenote.settings.ProfileSection
import com.example.simplenote.settings.Text
import com.example.simplenote.settings.Title
import com.example.simplenote.settings.TopLevel

@Composable
fun SettingsActivity(
    modifier: Modifier = Modifier,
    onBackHome: () -> Unit,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit
) {
    TopLevel(modifier = modifier) {
        Content(
            modifier = Modifier
                .boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 120.0.dp
                    )
                )
                .rowWeight(1.0f)
        ) {
            ProfileSection(modifier = Modifier.rowWeight(1.0f)) {
                ProfilePictureFullNameEmailAddress(modifier = Modifier.rowWeight(1.0f)) {
                    ProfilePicture()
                    FullNameEmailAddress(modifier = Modifier.rowWeight(1.0f)) {
                        FullName(modifier = Modifier.rowWeight(1.0f))
                        EmailAddressIcon(modifier = Modifier.rowWeight(1.0f)) {
                            IconOutlineMail {
                                Icon(
                                    modifier = Modifier
                                        .boxAlign(
                                            alignment = Alignment.TopStart,
                                            offset = DpOffset(
                                                x = -0.5.dp,
                                                y = -0.5.dp
                                            )
                                        )
                                        .rowWeight(1.0f)
                                        .columnWeight(1.0f)
                                )
                            }
                            EmailAddress(modifier = Modifier.rowWeight(1.0f))
                        }
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 0.0.dp,
                            y = -1.0.dp
                        )
                    )
                    .rowWeight(1.0f)
            )
            AppSettings(modifier = Modifier.rowWeight(1.0f)) {
                Label()
                Menus(modifier = Modifier.rowWeight(1.0f)) {
                    Main(modifier = Modifier
                        .rowWeight(1.0f)
                        .clickable { onChangePassword() }) {
                        ExtraMenuSingle(modifier = Modifier.rowWeight(1.0f)) {
                            IconOutlineLockClosed(
                                modifier = Modifier.boxAlign(
                                    alignment = Alignment.TopStart,
                                    offset = DpOffset(
                                        x = 8.0.dp,
                                        y = 16.0.dp
                                    )
                                )
                            ) {
                                Icon1(
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
                            MenuName(
                                modifier = Modifier.boxAlign(
                                    alignment = Alignment.TopStart,
                                    offset = DpOffset(
                                        x = 44.0.dp,
                                        y = 17.0.dp
                                    )
                                )
                            )
                            IconOutlineCheveronRight(
                                modifier = Modifier.boxAlign(
                                    alignment = Alignment.TopEnd,
                                    offset = DpOffset(
                                        x = -8.0.dp,
                                        y = 22.0.dp
                                    )
                                )
                            ) {
                                Icon2(
                                    modifier = Modifier
                                        .boxAlign(
                                            alignment = Alignment.TopStart,
                                            offset = DpOffset(
                                                x = -0.49999959202705213.dp,
                                                y = -0.5000017033621447.dp
                                            )
                                        )
                                        .rowWeight(1.0f)
                                        .columnWeight(1.0f)
                                )
                            }
                        }
                    }
                    Divider1(
                        modifier = Modifier
                            .boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 0.0.dp,
                                    y = -1.0.dp
                                )
                            )
                            .rowWeight(1.0f)
                    )
                    LogOut(modifier = Modifier
                        .rowWeight(1.0f)
                        .clickable { onLogout() }) {
                        IconOutlineLogout(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 8.0.dp,
                                    y = 16.0.dp
                                )
                            )
                        ) {
                            Icon3(
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
                        MenuName1(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(
                                    x = 44.0.dp,
                                    y = 17.0.dp
                                )
                            )
                        )
                    }
                }
            }
        }
        AppVersion(
            modifier = Modifier.boxAlign(
                alignment = Alignment.BottomCenter,
                offset = DpOffset(
                    x = 0.0.dp,
                    y = -24.0.dp
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
            Box(modifier = Modifier
                .rowWeight(1.0f)
                )
            Link(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 16.0.dp,
                        y = 16.0.dp
                    )
                ).clickable { onBackHome() }
            ) {
                IconSolidCheveronLeft {
                    Icon4(
                        modifier = Modifier
                            .rowWeight(1.0f)
                            .columnWeight(1.0f)
                    )
                }
                Text()
            }
            Title(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopCenter,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 16.0.dp
                    )
                )
            )
        }
    }
}


@Preview
@Composable
fun settingsPreview() {
    SettingsActivity(Modifier, {}, {}, {})
}