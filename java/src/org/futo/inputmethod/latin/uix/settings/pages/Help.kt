package org.futo.inputmethod.latin.uix.settings.pages

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.runBlocking
import org.futo.inputmethod.latin.BuildConfig
import org.futo.inputmethod.latin.uix.getSetting
import org.futo.inputmethod.latin.uix.setSetting
import org.futo.inputmethod.latin.uix.settings.NavigationItem
import org.futo.inputmethod.latin.uix.settings.NavigationItemStyle
import org.futo.inputmethod.latin.uix.settings.ScreenTitle
import org.futo.inputmethod.latin.uix.settings.ScrollableList
import org.futo.inputmethod.latin.uix.settings.Tip
import org.futo.inputmethod.updates.openURI

@Preview(showBackground = true)
@Composable
fun HelpScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

    val numPresses = remember { mutableIntStateOf(0) }
    var lastToast: MutableState<Toast?> = remember { mutableStateOf(null) }

    ScrollableList {
        ScreenTitle("Help & Feedback", showBack = true, navController)

        NavigationItem(
            title = "Version Name: ${BuildConfig.VERSION_NAME}",
            style = NavigationItemStyle.MiscNoArrow,
            navigate = {

            }
        )
        NavigationItem(
            title = "Version Code: ${BuildConfig.VERSION_CODE}",
            style = NavigationItemStyle.MiscNoArrow,
            navigate = {
                val makeToast: (String) -> Unit = { text ->
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).let {
                        lastToast.value?.cancel()
                        lastToast.value = it
                        it.show()
                    }
                }
                if(runBlocking { context.getSetting(IS_DEVELOPER, false) }) {
                    makeToast("You're already a developer")
                } else {
                    numPresses.value += 1
                    if (numPresses.value > 3) {
                        val pressesUntilDeveloper = 8 - numPresses.value
                        if (pressesUntilDeveloper <= 0) {
                            makeToast("You're now a developer")
                            runBlocking { context.setSetting(IS_DEVELOPER, true) }
                        } else {
                            makeToast("You are $pressesUntilDeveloper steps until becoming a developer")
                        }
                    }
                }
            }
        )

        Tip("We want to hear from you! If you're reporting an issue, your version may be relevant: v${BuildConfig.VERSION_NAME}")

        NavigationItem(title = "Wiki", style = NavigationItemStyle.Misc, navigate = {
            context.openURI("https://gitlab.futo.org/alex/keyboard-wiki/-/wikis/FUTO-Keyboard")
        })
        NavigationItem(title = "Discord Server", style = NavigationItemStyle.Misc, navigate = {
            context.openURI("https://keyboard.futo.org/discord")
        })
        NavigationItem(title = "FUTO Chat", style = NavigationItemStyle.Misc, navigate = {
            context.openURI("https://chat.futo.org/")
        })
        NavigationItem(title = "Email keyboard@futo.org", style = NavigationItemStyle.Misc, navigate = {
            context.openURI("mailto:keyboard@futo.org")
        })
    }
}