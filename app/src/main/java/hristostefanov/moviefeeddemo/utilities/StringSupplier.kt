package hristostefanov.moviefeeddemo.utilities

import androidx.annotation.StringRes

interface StringSupplier {
    fun get(@StringRes resId: Int): String
}