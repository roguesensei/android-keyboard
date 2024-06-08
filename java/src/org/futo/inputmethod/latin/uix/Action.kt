package org.futo.inputmethod.latin.uix

import android.content.Context
import android.view.View
import android.view.inputmethod.InputConnection
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import org.futo.inputmethod.latin.uix.theme.ThemeOption
import java.util.Locale

interface ActionInputTransaction {
    fun updatePartial(text: String)
    fun commit(text: String)
    fun cancel()
}

interface KeyboardManagerForAction {
    fun getContext(): Context
    fun getLifecycleScope(): LifecycleCoroutineScope

    fun triggerContentUpdate()

    fun createInputTransaction(applySpaceIfNeeded: Boolean): ActionInputTransaction

    fun typeText(v: String)
    fun backspace(amount: Int)

    fun closeActionWindow()

    fun triggerSystemVoiceInput()

    fun updateTheme(newTheme: ThemeOption)
    fun getThemeProvider(): DynamicThemeProvider

    fun sendCodePointEvent(codePoint: Int)
    fun sendKeyEvent(keyCode: Int, metaState: Int)

    fun cursorLeft(steps: Int, stepOverWords: Boolean, select: Boolean)
    fun cursorRight(steps: Int, stepOverWords: Boolean, select: Boolean)

    fun performHapticAndAudioFeedback(code: Int, view: View)
    fun announce(s: String)
    fun getActiveLocale(): Locale

    fun overrideInputConnection(inputConnection: InputConnection)
    fun unsetInputConnection()
}

interface ActionWindow {
    @Composable
    fun windowName(): String

    @Composable
    fun WindowContents(keyboardShown: Boolean)

    fun close()
}

interface PersistentActionState {
    /**
     * When called, the device may be on low memory and is requesting the action to clean up its
     * state. You can close any resources that may not be necessary anymore. This will never be
     * called when the action window is currently open. The PersistentActionState will stick around
     * after this.
     */
    suspend fun cleanUp()
}

data class Action(
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
    val canShowKeyboard: Boolean = false,
    val keepScreenAwake: Boolean = false,

    val windowImpl: ((KeyboardManagerForAction, PersistentActionState?) -> ActionWindow)?,
    val simplePressImpl: ((KeyboardManagerForAction, PersistentActionState?) -> Unit)?,
    val persistentState: ((KeyboardManagerForAction) -> PersistentActionState)? = null,
)
