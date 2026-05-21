package com.example.a211021_aininnajihah_mrnelson_lab05.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.a211021_aininnajihah_mrnelson_lab05.data.OrderEntity

@Composable
fun OrderHistoryScreen(
    orderList: List<OrderEntity>,
    onDeleteOrder: (OrderEntity) -> Unit,
    onIncreaseQuantity: (OrderEntity) -> Unit,
    onBackToStartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var orderToDelete by remember { mutableStateOf<OrderEntity?>(null) }
    var orderToIncrease by remember { mutableStateOf<OrderEntity?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Saved Order History",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Orders saved using Room Database",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (orderList.isEmpty()) {
            EmptyOrderHistory(
                onBackToStartClicked = onBackToStartClicked,
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = orderList,
                    key = { _, order -> order.id }
                ) { index, order ->
                    OrderHistoryCard(
                        displayNumber = index + 1,
                        order = order,
                        onDeleteOrderClicked = {
                            orderToDelete = order
                        },
                        onIncreaseQuantityClicked = {
                            orderToIncrease = order
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBackToStartClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 420.dp)
            ) {
                Text(text = "Back to Start")
            }
        }
    }

    orderToIncrease?.let { order ->
        AlertDialog(
            onDismissRequest = {
                orderToIncrease = null
            },
            title = {
                Text(text = "Add 1 quantity?")
            },
            text = {
                Text(
                    text = "This will increase the quantity from ${order.quantity} to ${order.quantity + 1} and update the saved order in Room."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onIncreaseQuantity(order)
                        orderToIncrease = null
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        orderToIncrease = null
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    orderToDelete?.let { order ->
        AlertDialog(
            onDismissRequest = {
                orderToDelete = null
            },
            title = {
                Text(text = "Delete order?")
            },
            text = {
                Text(
                    text = "This saved order will be permanently removed from the Room database."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteOrder(order)
                        orderToDelete = null
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        orderToDelete = null
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

@Composable
private fun EmptyOrderHistory(
    onBackToStartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No saved orders yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Complete an order first, then it will appear here.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = onBackToStartClicked) {
                    Text(text = "Back to Start")
                }
            }
        }
    }
}

@Composable
private fun OrderHistoryCard(
    displayNumber: Int,
    order: OrderEntity,
    onDeleteOrderClicked: () -> Unit,
    onIncreaseQuantityClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 520.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Order #$displayNumber",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            DetailRow(label = "Quantity", value = "${order.quantity}")
            DetailRow(label = "Flavor", value = order.flavor)
            DetailRow(label = "Pickup Date", value = order.date)
            DetailRow(label = "Price", value = order.price)

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onIncreaseQuantityClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Add 1",
                        textAlign = TextAlign.Center
                    )
                }

                OutlinedButton(
                    onClick = onDeleteOrderClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Delete",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End
        )
    }
}