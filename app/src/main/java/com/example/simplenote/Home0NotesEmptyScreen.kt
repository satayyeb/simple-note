package com.example.simplenote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.simplenote.home0notes.*

/**
 * A wrapper screen that composes the auto-generated chunks conditionally
 * and wires up click handlers for Home / Settings / Plus.
 */
@Composable
fun Home0NotesEmptyScreen(
    onAddNote: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopLevel (modifier = modifier.fillMaxSize()) {
        // --- Empty-state UI (only when there is no note) ---
        TitleDescription (
            modifier = Modifier.boxAlign(
                alignment = Alignment.CenterStart,
                offset = DpOffset(x = 0.dp, y = 79.5.dp)
            ).rowWeight(1f)
        ) {
            Title()
            Description()
        }

        Illustration(
            modifier = Modifier.boxAlign(
                alignment = Alignment.Center,
                offset = DpOffset(x = 0.dp, y = -117.dp)
            )
        ) {
            // این‌ها همان اجزای داخل Illustration در فایل اتوژن‌اند
            Details(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                Background(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Group(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group1(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
                Plant(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector1(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector2(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(x = -0.175048828125.dp, y = -0.1750030517578125.dp)
                        ).rowWeight(1f).columnWeight(1f)
                    )
                    Group2(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group5(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector9(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group3(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group6(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector10(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group4(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group7(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector11(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Vector3(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector4(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector5(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector6(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector7(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector8(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                }
                Stairs(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector12(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(x = 13.9599609375.dp, y = -0.2427978515625.dp)
                        ).rowWeight(1f).columnWeight(1f)
                    )
                    Group8(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group12(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector13(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group9(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group13(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector14(
                                modifier = Modifier.boxAlign(
                                    alignment = Alignment.TopStart,
                                    offset = DpOffset(x = 1.2449951171875.dp, y = 0.dp)
                                ).rowWeight(1f).columnWeight(1f)
                            )
                        }
                    }
                    Group10(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group14(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector15(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group11(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group15(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector16(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
            }
            Character(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                // ... باقی اجزای کاراکتر (همانند فایل اتوژن)
                // برای کوتاهی، همان توالی Vectorها و Groupها را نگه داشتیم
                LegRight(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector17(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group16(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group25(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector19(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group17(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group26(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector20(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group18(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group27(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector21(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group19(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group28(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector22(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Vector18(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group20(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group29(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector23(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group21(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group30(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector24(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group22(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group31(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector25(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group23(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group32(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector26(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group24(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group33(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector27(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
                LegLeft(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector28(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group34(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group42(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector30(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group35(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group43(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector31(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group36(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group44(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector32(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group37(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group45(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector33(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Vector29(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group38(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group46(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector34(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group39(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group47(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector35(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group40(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group48(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector36(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group41(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group49(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector37(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
                HandRight(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector38(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector39(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group50(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group60(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector40(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group51(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group61(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector41(modifier = Modifier.rowWeight(1f).columnWeight(1.0f))
                        }
                    }
                    Group52(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group62(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector42(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group53(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group63(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector43(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group54(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group64(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector44(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group55(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group65(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector45(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group56(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group66(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector46(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group57(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group67(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector47(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group58(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group68(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector48(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group59(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group69(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector49(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
                Torso(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector50(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group70(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group78(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector51(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group71(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group79(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector52(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group72(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group80(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector53(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group73(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group81(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector54(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group74(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group82(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector55(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group75(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group83(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector56(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group76(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group84(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector57(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group77(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group85(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector58(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
                Bag(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector59(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector60(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group86(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group94(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector61(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group87(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group95(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector62(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group88(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group96(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector63(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group89(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group97(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector64(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group90(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group98(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector65(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group91(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group99(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector66(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group92(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group100(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector67(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group93(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group101(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector68(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
                HandLeft(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector69(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector70(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Group102(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group113(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector71(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group103(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group114(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector72(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group104(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group115(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector73(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group105(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group116(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector74(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group106(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group117(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector75(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group107(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group118(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector76(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group108(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group119(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector77(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group109(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group120(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector78(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group110(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group121(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector79(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group111(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group122(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector80(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group112(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group123(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector81(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                }
                Head(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                    Vector82(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector83(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector84(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    Vector85(
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(x = 0.12939453125.dp, y = 0.11747778255178076.dp)
                        ).rowWeight(1f).columnWeight(1f)
                    )
                    Group124(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group131(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector87(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group125(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group132(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector88(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group126(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group133(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector89(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group127(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group134(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector90(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group128(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group135(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector91(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group129(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group136(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector92(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Group130(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                        Group137(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
                            Vector93(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                        }
                    }
                    Vector86(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                }
            }
        }

        Arrow(
            modifier = Modifier.boxAlign(
                alignment = Alignment.BottomCenter,
                offset = DpOffset(x = 0.dp, y = -137.dp)
            )
        ) {
            Direction(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopEnd,
                    offset = DpOffset(x = 33.dp, y = -0.94.dp)
                ).columnWeight(1f)
            )
        }

        // --- Bottom TabBar (always visible) + click handlers ---
        TabBar(modifier = Modifier.rowWeight(1f).columnWeight(1f)) {
            // پس‌زمینه‌ی میله
            Box(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomStart,
                    offset = DpOffset(x = 0.dp, y = 0.009.dp)
                ).rowWeight(1f)
            )

            // دکمه Home (سمت راست UI)
            Right(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomStart,
                    offset = DpOffset(x = 0.dp, y = -15.991.dp)
                ).rowWeight(1f)
            ) {
                NavPartIconMenu(
                    modifier = Modifier
                        .boxAlign(
                            alignment = Alignment.BottomStart,
                            offset = DpOffset(x = 0.dp, y = 0.dp)
                        )
                        .rowWeight(1f)
                        .clickable { onHome() } // کلیک هوم
                ) {
                    IconSolidHome {
                        Icon(modifier = Modifier.rowWeight(1f).columnWeight(1f))
                    }
                    Home()
                }
            }

            // دکمه Settings (سمت چپ UI)
            Left(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomStart,
                    offset = DpOffset(x = 0.dp, y = -15.991.dp)
                ).rowWeight(1f)
            ) {
                NavPartIconMenu3(
                    modifier = Modifier
                        .boxAlign(
                            alignment = Alignment.BottomStart,
                            offset = DpOffset(x = 0.dp, y = 0.dp)
                        )
                        .rowWeight(1f)
                        .clickable { onSettings() } // کلیک تنظیمات
                ) {
                    IconOutlineCog {
                        Icon3(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(x = -1.dp, y = -1.dp)
                            ).rowWeight(1f).columnWeight(1f)
                        )
                    }
                    Settings()
                }
            }

            // دکمه Plus (وسط)
            IconButton(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomCenter,
                    offset = DpOffset(x = 0.dp, y = -43.991.dp)
                ).clickable { onAddNote() } // کلیک پلاس
            ) {
                Button {
                    IconOutlinePlus {
                        Icon4(
                            modifier = Modifier.boxAlign(
                                alignment = Alignment.TopStart,
                                offset = DpOffset(x = -1.5.dp, y = -1.5.dp)
                            ).rowWeight(1f).columnWeight(1f)
                        )
                    }
                }
            }
        }
    }
}
