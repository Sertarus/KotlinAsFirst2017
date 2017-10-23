@file:Suppress("UNUSED_PARAMETER")

package lesson4.task1

import lesson1.task1.discriminant
import java.lang.Math.pow
import java.lang.Math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    val sqrList = v.map { it * it }
    return sqrt(sqrList.sum())
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isNotEmpty()) list.sum() / list.size else 0.0

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty()) return list
    else {
        val average = mean(list)
        for (i in 0 until list.size) {
            list[i] -= average
        }
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    val result = mutableListOf<Double>()
    for (i in 0 until a.size) {
        val part = a[i] * b[i]
        result.add(part)
    }
    return result.sum()
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var counter = 0.0
    val result = mutableListOf<Double>()
    for (i in 0 until p.size) {
        val part = p[i] * pow(x, counter)
        result.add(part)
        counter++
    }
    return result.sum()
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    for (i in list.size - 1 downTo 1) {
        for (k in 1..i) {
            list[i] += list[i - k]
        }
    }
    return list
}


/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val result = mutableListOf<Int>()
    var number = n
    while (number % 2 == 0) {
        result.add(2)
        number /= 2
    }
    for (i in 3..n step 2) {
        while (number % i == 0) {
            result.add(i)
            number /= i
        }
    }
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var number = n
    val list = mutableListOf<Int>()
    while (number >= base) {
        list.add(number % base)
        number /= base
    }
    list.add(number)
    val result = mutableListOf<Int>()
    if (list.isEmpty())
        result.add(n)
    for (i in list.size - 1 downTo 0)
        result.add(list[i])
    return result
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val list = convert(n, base)
    var result = ""
    for (i in 0 until list.size) {
        result += if (list[i] in 10..35)
            'a' + list[i] - 10
        else list[i].toString()
    }
    return result
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var number = 0
    for (i in 0 until digits.size) {
        number += (digits[i] * pow(base.toDouble(), (digits.size - 1 - i.toDouble()))).toInt()
    }
    return number
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var number = 0
    var counter = str.length - 1
    for (char in str) {
        val digit = if (char.toInt() <= 57) char.toInt() - 48
        else char.toInt() - 87
        number += digit * pow(base.toDouble(), (counter.toDouble())).toInt()
        counter--
    }
    return number
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var number = n
    var result = ""
    while (number != 0) {
        when {
            number >= 1000 -> {
                result += "M"
                number -= 1000
            }
            number >= 900 -> {
                result += "CM"
                number -= 900
            }
            number >= 500 -> {
                result += "D"
                number -= 500
            }
            number >= 400 -> {
                result += "CD"
                number -= 400
            }
            number >= 100 -> {
                result += "C"
                number -= 100
            }
            number >= 90 -> {
                result += "XC"
                number -= 90
            }
            number >= 50 -> {
                result += "L"
                number -= 50
            }
            number >= 40 -> {
                result += "XL"
                number -= 40
            }
            number >= 10 -> {
                result += "X"
                number -= 10
            }
            number >= 9 -> {
                result += "IX"
                number -= 9
            }
            number >= 5 -> {
                result += "V"
                number -= 5
            }
            number >= 4 -> {
                result += "IV"
                number -= 4
            }
            number >= 1 -> {
                result += "I"
                number -= 1
            }
        }
    }
    return result
}


/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val units = mutableListOf("", "одна ", "две ", "три ", "четыре ", "пять ", "шесть ", "семь ", "восемь ", "девять ")
    val dozensAndUnits = listOf("десять ", "одиннадцать ", "двенадцать ", "тринадцать ", "четырнадцать ",
            "пятнадцать ", "шестнадцать ", "семнадцать ", "восемнадцать ", "девятнадцать ")
    val dozens = listOf("", "", "двадцать ", "тридцать ", "сорок ", "пятьдесят ", "шестьдесят ", "семьдесят ",
            "восемьдесят ", "девяносто ")
    val hundreds = listOf("", "сто ", "двести ", "триста ", "четыреста ", "пятьсот ", "шестьсот ", "семьсот ",
            "восемьсот ", "девятьсот ")
    var result = ""
    var numberHundreds = n % 1000
    var numberThousands = n / 1000
    result += hundreds[numberThousands / 100]
    numberThousands %= 100
    if (numberThousands in 10..19)
        result += dozensAndUnits[numberThousands - 10]
    else {
        result += dozens[numberThousands / 10]
        result += units[numberThousands % 10]
    }
    if (n / 1000 != 0) {
        result += when {
            numberThousands % 10 == 1 && numberThousands % 100 !=11 -> "тысяча "
            (numberThousands % 10 in 2..4 && numberThousands % 100 !in 12..14) -> "тысячи "
            else -> "тысяч "
        }
    }
    units[1] = "один "
    units[2] = "два "
    result += hundreds[numberHundreds / 100]
    numberHundreds %= 100
    if (numberHundreds in 10..19)
        result += dozensAndUnits[numberHundreds - 10]
    else {
        result += dozens[numberHundreds / 10]
        result += units[numberHundreds % 10]
    }
    return result.trim()
}
