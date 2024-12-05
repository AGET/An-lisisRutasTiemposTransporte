package com.example.anlisisrutastiempostransporte.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.anlisisrutastiempostransporte.RoutesApp
import com.example.anlisisrutastiempostransporte.ui.utils.RecyclerViewItemDecoration


val Context.app: RoutesApp
    get() = applicationContext as RoutesApp

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(body))
}

fun <T : ViewDataBinding> ViewGroup.bindingInflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = true
): T = DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot)


@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

    val viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this.viewModelStore, viewModelFactory)[T::class.java]
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val viewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this.viewModelStore, viewModelFactory)[T::class.java]
}

fun RecyclerView.setItemDecorationSpacing(padding: Float) {
    addItemDecoration(RecyclerViewItemDecoration(padding.toInt()))
}

fun View.visibleWithAnimation(
    fillAfter: Boolean = false,
    duration: Long = 500
) {
    this.visibility = VISIBLE
    val animate = TranslateAnimation(
        0F,
        0F,
        this.height.toFloat(),
        0F
    )
    animate.duration = duration
    animate.fillAfter = fillAfter
    this.startAnimation(animate)
}

fun View.goneWithAnimation(
    fillAfter: Boolean = false,
    duration: Long = 200
) {
    this.visibility = GONE
    val animate = TranslateAnimation(
        0F,
        0F,
        0F,
        this.height.toFloat()
    )
    animate.duration = duration
    animate.fillAfter = fillAfter
    this.startAnimation(animate)
}

fun FragmentActivity.showDialogFragment(dialogFragment: DialogFragment) {
    supportFragmentManager.findFragmentByTag(dialogFragment::class.java.name).let {
        if (it == null) {
            dialogFragment.show(supportFragmentManager, dialogFragment::class.java.name)
        }
    }
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment) {
    activity?.showDialogFragment(dialogFragment)
}