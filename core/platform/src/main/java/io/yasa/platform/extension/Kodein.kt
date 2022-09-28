package io.yasa.platform.extension

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.KodeinPropertyDelegateProvider
import kotlin.reflect.KProperty

private fun getKodein(thisRef: Any?, rootContext: Context): Kodein {
    var context: Context? = rootContext
    while (context != null) {
        if (context != thisRef && context is KodeinAware) {
            return context.kodein
        }
        context = if (context is ContextWrapper) context.baseContext else null
    }
    return (rootContext.applicationContext as KodeinAware).kodein
}

private class FragmentKodeinPropertyDelegateProvider : KodeinPropertyDelegateProvider<Fragment> {
    override fun provideDelegate(thisRef: Fragment, property: KProperty<*>?): Lazy<Kodein> = lazy {
        (thisRef.parentFragment as? KodeinAware)?.kodein ?: getKodein(thisRef, thisRef.requireActivity())
    }
}

fun fragmentKodein(): KodeinPropertyDelegateProvider<Fragment> = FragmentKodeinPropertyDelegateProvider()