package org.example.project.component

import com.arkivanov.decompose.ComponentContext

interface SecondComponent {
    val param: String
    fun goBack()
}

class SecondComponentImpl(
    override val param: String,
    private val onGoBack: () -> Unit,
    componentContext: ComponentContext,
) : SecondComponent, ComponentContext by componentContext {
    override fun goBack() = onGoBack()
}