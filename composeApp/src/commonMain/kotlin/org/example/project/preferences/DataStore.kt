fun createDataStore(producePath: () -> okio.Path): androidx.datastore.core.DataStore<Preferences> {
    return androidx.datastore.core.DataStoreFactory.create(
        storage = androidx.datastore.core.okio.OkioStorage(
            fileSystem = okio.FileSystem.SYSTEM,
            serializer = PreferencesSerializer(),
            producePath = producePath
        )
    )
}