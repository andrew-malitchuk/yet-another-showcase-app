package io.yasa.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.instanceOrNull

//class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
//            ?: modelClass.newInstance()
//    }
//}
//
//inline fun <reified VM : ViewModel, T> T.activityViewModel(): Lazy<VM> where T : KodeinAware, T : FragmentActivity {
//    return viewModels(factoryProducer = { direct.instance() })
//}
//
//inline fun <reified VM : ViewModel, T> T.activityScopedFragmentViewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
//    return viewModels(ownerProducer = { requireParentFragment() },
//        factoryProducer = { getFactoryInstance() })
//}
//
//inline fun <reified VM : ViewModel, T> T.fragmentViewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
//    return viewModels(factoryProducer = { getFactoryInstance() })
//}
//
//inline fun <reified VM : ViewModel> Kodein.Builder.bindViewModel(overrides: Boolean? = null): Kodein.Builder.TypeBinder<VM> {
//    return bind<VM>(VM::class.java.simpleName, overrides)
//}
//
//fun <T> T.getFactoryInstance(
//): ViewModelProvider.Factory where T : KodeinAware, T : Fragment {
//    val viewModeFactory: ViewModelProvider.Factory by kodein.on(activity).instance()
//    return viewModeFactory
//}

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
            ?: modelClass.newInstance()
    }
}

inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel(overrides: Boolean? = null): Kodein.Builder.TypeBinder<T> {
    return bind<T>(T::class.java.simpleName, overrides)
}

inline fun <reified VM : ViewModel, T> T.kodeinViewModel(): Lazy<VM> where T : KodeinAware, T : AppCompatActivity {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}

inline fun <reified VM : ViewModel, T> T.kodeinViewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}