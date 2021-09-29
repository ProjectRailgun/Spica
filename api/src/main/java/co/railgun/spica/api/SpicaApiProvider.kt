package co.railgun.spica.api

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import co.railgun.spica.api.function.initialize

class SpicaApiProvider : ContentProvider() {

    override fun onCreate(): Boolean =
        context?.applicationContext?.let {
            SpicaApiClient.initialize(it)
            true
        } ?: error("Context cannot be null")

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?,
    ): Cursor = error("Not allowed.")

    override fun getType(uri: Uri): String =
        error("Not allowed.")

    override fun insert(uri: Uri, values: ContentValues?): Uri =
        error("Not allowed.")

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String?>?): Int =
        error("Not allowed.")

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?,
    ): Int = error("Not allowed.")
}
