package ru.leushinilya.loftmoney.screens

import ru.leushinilya.loftmoney.R

enum class Screens(val titleRes: Int) {
    SPLASH(-1),
    LOGIN(-1),
    MAIN(R.string.budget_accounting),
    LIST(-1),
    DIAGRAM(R.string.balance),
    ADD_ITEM(-1)
}