package io.yasa.ui.viewbinding.edittext

import android.os.Build
import android.text.Editable
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.os.postDelayed
import androidx.core.widget.doAfterTextChanged


@RequiresApi(Build.VERSION_CODES.P)
fun EditText.debounce(delay: Long, action: (Editable?) -> Unit) {
    doAfterTextChanged { text ->
        var counter = getTag(id) as? Int ?: 0
        handler.removeCallbacksAndMessages(counter)
        handler.postDelayed(delay, ++counter) { action(text) }
        setTag(id, counter)
    }
}