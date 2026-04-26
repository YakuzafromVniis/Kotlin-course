package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import shoppingbasket.composeapp.generated.resources.Res
import shoppingbasket.composeapp.generated.resources.compose_multiplatform

// Модель данных для продукта
data class Product(val name: String, val amount: String, val price: String)

@Composable
fun App() {
    // Список статических данных для корзины
    val basketItems = remember {
        listOf(
            Product("Молоко 3.2%", "1 шт.", "89 ₽"),
            Product("Хлеб Бородинский", "1 шт.", "45 ₽"),
            Product("Яйца С0 (10 шт)", "2 уп.", "240 ₽"),
            Product("Куриное филе", "0.8 кг", "350 ₽"),
            Product("Яблоки Ред Чиф", "1.5 кг", "180 ₽"),
            Product("Сыр Пармезан", "200 г", "290 ₽"),
            Product("Кофе молотый", "1 шт.", "450 ₽")
        )
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Моя корзина",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )

                // Ленивый список товаров
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(basketItems) { product ->
                        ProductItem(product)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            // Стандартная картинка из ресурсов проекта
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Количество: ${product.amount}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = product.price,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
