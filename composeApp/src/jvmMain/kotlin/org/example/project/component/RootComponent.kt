package org.example.project.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Home(val component: HomeComponent) : Child
        class Second(val component: SecondComponent) : Child
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<Config, RootComponent.Child>> = childStack(
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