package co.railgun.spica.ui.navigation

import android.os.Bundle
import androidx.navigation.NavBackStackEntry

interface NavigationArgumentsScope {

    fun <T> String.resolveArgument(key: String, value: T): String =
        replace(key.routeArgument, value.toString())

    fun NavBackStackEntry.requireBoolean(key: String): Boolean =
        requireNotNull(requireArguments().getBoolean(key))

    fun NavBackStackEntry.requireString(key: String): String =
        requireNotNull(requireArguments().getString(key))

    fun NavBackStackEntry.requireInt(key: String): Int =
        requireNotNull(requireArguments().getInt(key))

    private fun NavBackStackEntry.requireArguments(): Bundle =
        requireNotNull(arguments) { "Navigation arguments shouldn't be null here." }
}

val String.routeArgument: String
    get() = "{$this}"
