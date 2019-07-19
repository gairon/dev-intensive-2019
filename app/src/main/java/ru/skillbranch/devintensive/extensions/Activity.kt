package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager

fun Activity.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = window.decorView.rootView
    val visRect: Rect = Rect();
    rootView.getWindowVisibleDisplayFrame(visRect);
    val screenHeight: Int = rootView.height;

    // r.bottom is the position above soft keypad or device button.
    // if keypad is shown, the r.bottom is smaller than that before.
    val keypadHeight: Int = screenHeight - visRect.bottom;

    return (keypadHeight > screenHeight * 0.15);
}

fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen();
}