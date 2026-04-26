package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


data class ShoppingListItem(
    val description: String,
    val bought: Boolean = false
)

@Composable
fun App() {

    val shoppingList = remember {
        mutableStateListOf(
            ShoppingListItem("Молоко"),
            ShoppingListItem("Мука"),
            ShoppingListItem("Яйца")
        )
    }


    var newItemDesc by remember { mutableStateOf("") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Список покупок",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    item {
                        OutlinedTextField(
                            value = newItemDesc,
                            onValueChange = { newItemDesc = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            label = { Text("Название продукта") },
                            trailingIcon = {
                                IconButton(onClick = {
                                    if (newItemDesc.isNotBlank()) {
                                        shoppingList.add(ShoppingListItem(newItemDesc.trim()))
                                        newItemDesc = ""
                                    }
                                }) {
                                    Icon(Icons.Default.Add, contentDescription = "Добавить")
                                }
                            }
                        )
                    }


                    itemsIndexed(shoppingList) { index, item ->
                        ShoppingListElement(
                            item = item,
                            onBoughtChange = { isChecked ->

                                shoppingList[index] = item.copy(bought = isChecked)
                            },
                            onDelete = {
                                shoppingList.removeAt(index)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListElement(
    item: ShoppingListItem,
    onBoughtChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Checkbox(
            checked = item.bought,
            onCheckedChange = onBoughtChange
        )


        Text(
            text = item.description,
            modifier = Modifier.weight(1f),
            style = if (item.bought)
                MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.outline)
            else MaterialTheme.typography.bodyLarge
        )

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}