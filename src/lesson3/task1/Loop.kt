@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var digitN = 0
    var number = n
    do {
        number /= 10
        digitN += 1
    } while (number != 0)
    return digitN
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var lastNumber = 1
    var number = lastNumber
    var penultimateNumber = 1
    var numberInSequence = 2
    while (numberInSequence < n) {
        lastNumber += penultimateNumber
        penultimateNumber = number
        number = lastNumber
        numberInSequence++
    }
    return lastNumber
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var firstNumber = m
    var secondNumber = n
    while (firstNumber != 0 && secondNumber != 0) {
        if (firstNumber > secondNumber) firstNumber %= secondNumber
        else secondNumber %= firstNumber
    }
    return m * n / (firstNumber + secondNumber)
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minDiv = 2
    while (n % minDiv != 0)
        minDiv++
    return minDiv
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var maxDiv = n - 1
    while (n % maxDiv != 0)
        maxDiv--
    return maxDiv
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var k = m
    var j = n
    while (k != j) {
        if (k > j) k -= j
        else j -= k
    }
    return j == 1
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val sqrtM = sqrt(m.toDouble()).toInt()
    val sqrtN = sqrt(n.toDouble()).toInt()
    for (i in sqrtM..sqrtN)
        if (sqr(i.toDouble()) in m..n) return true
    return false
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    val angle = calculateAngle(x)
    var sinX = angle
    if ((angle == 0.0) || (angle == PI)) return 0.0
    if (angle == 3 * PI / 2) return -1.0
    if (angle == PI / 2.0) return 1.0
    var counter = 4.0
    var part = -1.0 * pow(angle, 3.0) / factorial(3)
    while (abs(part) >= eps) {
        sinX += part
        part *= -1.0 * sqr(angle) / counter / (1.0 + counter)
        counter += 2.0
    }
    return sinX
}

fun calculateAngle(x: Double): Double {
    var angle = x / PI
    while (angle >= 2) {
        angle -= 2
    }
    while (angle < 0) {
        angle += 2
    }
    return angle * PI
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    val angle = calculateAngle(x)
    var cosX = 1.0
    if ((angle == PI / 2) || (angle == 3 * PI / 2)) return 0.0
    if (angle == PI) return -1.0
    if (angle == 0.0) return 1.0
    var counter = 3.0
    var part = -1.0 * sqr(angle) / factorial(2)
    while (abs(part) >= eps) {
        cosX += part
        part *= -1.0 * sqr(angle) / counter / (1.0 + counter)
        counter += 2.0
    }
    return cosX
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var number = n
    var result = 0
    while (number > 0) {
        result = result * 10 + number % 10
        number /= 10
    }
    return result
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)


/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var number = n
    val lastDigit = n % 10
    while (number != 0) {
        if (lastDigit != number % 10) return true
        number /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var numberCounter = 0
    var squareNumber = 0.0
    while (n > numberCounter) {
        squareNumber += 1
        val squareCopy = sqr(squareNumber).toInt()
        numberCounter += digitNumber(squareCopy)
    }
    val square = sqr(squareNumber).toInt()
    return findSomeDigit(square, numberCounter - n)
}


fun findSomeDigit(x1: Int, counter: Int): Int {
    var c = counter
    var number = x1
    while (c > 0) {
        number /= 10
        c--
    }
    return if (number in 0..9) number
    else number % 10
}


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var numberCounter = 0
    var serialNumber = 0
    while (n > numberCounter) {
        serialNumber += 1
        val fibNumberCopy = fib(serialNumber)
        numberCounter += digitNumber(fibNumberCopy)
    }
    val fibNumber = fib(serialNumber)
    return findSomeDigit(x1 = fibNumber, counter = numberCounter - n)
}
