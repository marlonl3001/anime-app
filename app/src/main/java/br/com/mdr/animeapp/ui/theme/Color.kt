package br.com.mdr.animeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)

val ColorScheme.statusBarColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple700

val ColorScheme.welcomeScreenBackgroundColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Color.White

val ColorScheme.titleColor
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else DarkGray

val ColorScheme.descriptionColor
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray.copy(alpha = 0.5f) else DarkGray.copy(alpha = 0.5f)

val ColorScheme.activeIndicatorColor
    @Composable
    get() = if (isSystemInDarkTheme()) Purple700 else Purple500

val ColorScheme.inactiveIndicatorColor
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGray else LightGray

val ColorScheme.buttonBackgroundColor
    @Composable
    get() = if (isSystemInDarkTheme()) Purple700 else Purple500

val ColorScheme.topAppBarContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else Color.White

val ColorScheme.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple500