package com.example.reactive.model

enum class Currency(val longName: String, val relativeValue: Double) {
    RUB("Russian Rubles", 77.79),
    USD("US Dollars", 1.0),
    EUR("Euros", 0.84);

    companion object : Converter {
        override fun convert(amount: Double, toCurrency: Currency): Double {
            return amount * toCurrency.relativeValue
        }
    }
}

interface Converter {
    fun convert(amount: Double, toCurrency: Currency): Double
}