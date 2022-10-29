package ru.leushinilya.loftmoney.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.leushinilya.loftmoney.R
import ru.leushinilya.loftmoney.TransactionType
import ru.leushinilya.loftmoney.cells.Item

@Composable
fun ListScreen(viewModel: MainViewModel, type: TransactionType) {
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing),
        onRefresh = {

        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val list = when (type) {
                TransactionType.EXPENSE -> viewModel.expenses
                TransactionType.INCOME -> viewModel.incomes
            }
            items(list) { transaction ->
                ItemView(viewModel = viewModel, item = transaction)
                Divider(
                    color = colorResource(id = R.color.medium_grey),
                    modifier = Modifier.alpha(0.2F)
                )
            }
        }
    }

}

@Composable
fun ItemView(viewModel: MainViewModel, item: Item) {
    var isSelected by remember { mutableStateOf(false) }
    val textStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    )
    val priceColor = when (item.type) {
        0 -> colorResource(id = R.color.lightish_blue)
        1 -> colorResource(id = R.color.apple_green)
        else -> colorResource(id = R.color.medium_grey)
    }
    val backgroundColor = when (isSelected) {
        true -> colorResource(id = R.color.selection_item_color)
        else -> colorResource(id = R.color.white)
    }

    fun switchSelection() {
        isSelected = !isSelected
        viewModel.onItemSelectionChanged(isSelected)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { switchSelection() },
                    onTap = { if (viewModel.selectedCount > 0) switchSelection() }
                )
            }
            .background(backgroundColor)
            .padding(dimensionResource(id = R.dimen.spacing_24)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            color = colorResource(id = R.color.medium_grey),
            style = textStyle
        )
        Text(
            text = item.price,
            style = textStyle,
            color = priceColor
        )
    }
}