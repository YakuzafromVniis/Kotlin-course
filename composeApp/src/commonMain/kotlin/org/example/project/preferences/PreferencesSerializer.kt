package org.example.project.preferences

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use

class PreferencesSerializer : OkioSerializer<Preferences> {
    override val defaultValue: Preferences = Preferences()

    override suspend fun readFrom(source: BufferedSource): Preferences {
        return try {
            Json.decodeFromString(Preferences.serializer(), source.readUtf8())
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: Preferences, sink: BufferedSink) {
        sink.writeUtf8(Json.encodeToString(Preferences.serializer(), t))
    }
}