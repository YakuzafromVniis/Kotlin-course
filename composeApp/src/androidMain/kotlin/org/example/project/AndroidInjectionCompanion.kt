package org.example.project

import android.content.Context
import androidx.datastore.core.DataStore
import org.example.project.preferences.Preferences
import org.example.project.preferences.createDataStore
import okio.FileSystem
import okio.Path.Companion.toOkioPath

object AndroidInjectionCompanion {
    private var dataStore: DataStore<Preferences>? = null

    fun getDataStore(context: Context): DataStore<Preferences> {
        return dataStore ?: createDataStore(
            fileSystem = FileSystem.SYSTEM,
            producePath = {
                context.filesDir.resolve("preferences.json").toOkioPath()
            }
        ).also { dataStore = it }
    }
}