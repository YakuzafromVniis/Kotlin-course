package org.example.project.component

import androidx.compose.runtime.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnStart
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
sealed interface RequestState {
    @Serializable data object Loading : RequestState
    @Serializable data object Error : RequestState
    @Serializable data class Success(val value: String) : RequestState
}

interface SecondComponent {
    val param: String
    val requestState: RequestState
    fun goBack()
}

class SecondComponentImpl(
    override val param: String,
    private val httpClient: HttpClient,
    private val onGoBack: () -> Unit,
    componentContext: ComponentContext,
) : SecondComponent, ComponentContext by componentContext {

    private val scope = coroutineScope()
    override var requestState by mutableStateOf<RequestState>(RequestState.Loading)

    init {
        // ШАГ А: Пытаемся достать данные из "хранилища", если они там есть (после поворота экрана)
        stateKeeper.consume("REQUEST_STATE_KEY", RequestState.serializer())?.let { savedState ->
            requestState = savedState
        }

        // ШАГ Б: Говорим системе: "Если будешь закрывать экран, сохрани текущее requestState под ключом REQUEST_STATE_KEY"
        stateKeeper.register("REQUEST_STATE_KEY", RequestState.serializer()) { requestState }

        // ШАГ В: Загружаем данные только если мы еще ничего не скачали (состояние Loading)
        lifecycle.doOnStart {
            if (requestState is RequestState.Loading) {
                loadData()
            }
        }
    }

    private fun loadData() {
        scope.launch {
            requestState = RequestState.Loading
            try {
                // Прямой запрос к API
                val response = httpClient.get("https://dummyjson.com/http/200/$param")
                if (response.status.isSuccess()) {
                    requestState = RequestState.Success(response.bodyAsText())
                } else {
                    requestState = RequestState.Error
                }
            } catch (e: Exception) {
                requestState = RequestState.Error
                e.printStackTrace()
            }
        }
    }

    override fun goBack() = onGoBack()
}