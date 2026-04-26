package org.example.project.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value // Вот этот импорт лечит Unresolved reference 'Value'
import io.ktor.client.HttpClient
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import org.example.project.preferences.Preferences

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>
    val preferences: Flow<Preferences>

    sealed interface Child {
        class Home(val component: HomeComponent) : Child
        class Second(val component: SecondComponent) : Child
    }
}

class RootComponentImpl(
    private val httpClient: HttpClient,
    private val dataStore: DataStore<Preferences>,
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val preferences: Flow<Preferences> = dataStore.data

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = { config, context ->
            when (config) {
                is Config.Home -> RootComponent.Child.Home(
                    HomeComponentImpl(
                        onNavigateToSecondScreen = { navigation.push(Config.Second(it)) },
                        componentContext = context
                    )
                )
                is Config.Second -> RootComponent.Child.Second(
                    SecondComponentImpl(
                        param = config.param,
                        httpClient = httpClient,
                        onGoBack = { navigation.pop() },
                        componentContext = context
                    )
                )
            }
        }
    )

    @Serializable
    sealed interface Config {
        @Serializable data object Home : Config
        @Serializable data class Second(val param: String) : Config
    }
}