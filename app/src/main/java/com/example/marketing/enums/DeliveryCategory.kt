package com.example.marketing.enums

enum class DeliveryCategory(val code: Int) {
    ALL(-1),
    LIFE(0),
    SERVICE(1),
    DIGITAL(2),
    BEAUTY(3),
    FASSION(4),
    BOOK(5),
    FOOD(6),
    PET(7);

    companion object {
        val codeMap: Map<Int, DeliveryCategory> = entries.associateBy { it.code }

        fun fromCode(code: Int): DeliveryCategory? = codeMap[code]

        val actualCategories: List<DeliveryCategory>
            get() = entries.filterNot { it == ALL }
    }
}