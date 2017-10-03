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
fun fib(n: Int): Int = if (n <= 2) 1 else fib(n - 2) + fib(n - 1)

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var k = m
    var j = n
    while (k != j) {
        if (k > j) k -= j
        else j -= k
    }
    return m * n / k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minDiv = 0
    for (i in 2..n) {
        if (n % i == 0) {
            minDiv = i
            break
        }
    }
    return minDiv
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var maxDiv = 0
    for (i in n - 1 downTo 1) {
        if (n % i == 0) {
            maxDiv = i
            break
        }
    }
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
    var exists = false
    for (i in m..n)
        if (sqrt(i.toDouble()) % 1.0 == 0.0) exists = true
    return exists
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var angle = x / PI
    while (angle >= 2) {
        angle -= 2
    }
    while (angle < 0) {
        angle += 2
    }
    angle *= PI

    var sinX = angle
    if ((angle == 0.0) || (angle == PI)) return 0.0
    else if (angle == 3 * PI / 2) return -1.0
    else if (angle == PI / 2.0) return 1.0
    var counter = 1
    var part = pow(-1.0, counter.toDouble()) * pow(angle, 2.0 * counter + 1) / factorial(2 * counter + 1)
    while (abs(part) >= eps) {
        part = pow(-1.0, counter.toDouble()) * pow(angle, 2.0 * counter + 1) / factorial(2 * counter + 1)
        sinX += part
        counter++
    }
    return sinX
}


/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var angle = x

    while (angle >= 2 * PI) {
        angle -= 2 * PI
    }
    while (angle < 0) {
        angle += 2 * PI
    }

    var cosX = 1.0
    if ((angle == PI / 2) || (angle == 3 * PI / 2)) return 0.0
    else if (angle == PI) return -1.0
    else if (angle == 0.0) return 1.0
    var counter = 1
    var part = pow(-1.0, counter.toDouble()) * pow(angle, 2.0 * counter) / factorial(2 * counter)
    while (abs(part) >= eps) {
        part = pow(-1.0, counter.toDouble()) * pow(angle, 2.0 * counter) / factorial(2 * counter)
        cosX += part
        counter++
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
    var number1 = n
    var number2 = n
    var counter = 0
    var answer = 0.0
    while (number1 != 0) {
        number1 /= 10
        counter++
    }
    for (i in 1..counter) {
        answer += number2 % 10 * pow(10.0, counter.toDouble() - 1)
        counter--
        number2 /= 10
    }
    return answer.toInt()
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    var number1 = n
    var number2 = n
    var counter = 0
    var answer = 0.0
    while (number1 != 0) {
        number1 /= 10
        counter++
    }
    for (i in 1..counter) {
        answer += number2 % 10 * pow(10.0, counter.toDouble() - 1)
        counter--
        number2 /= 10
    }
    return answer.toInt() == n
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    if (n in 0..9) return false
    var number1 = n
    var lastDigit = n % 10
    while (number1 != 0) {
        if (lastDigit != number1 % 10) return true
        lastDigit = number1 % 10
        number1 /= 10

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
    var counter1 = 0
    var counter2 = 0
    var sqrt = 1.0
    var square2 = sqr(sqrt).toInt()
    while (n > counter1) {
        var square1 = sqr(sqrt).toInt()
        square2 = sqr(sqrt).toInt()
        while (square1 != 0) {
            counter2++
            square1 /= 10
        }
        counter1 = counter2
        sqrt += 1

    }
    return Digit(x1 = square2, counter = counter1 - n)
}


fun Digit(x1: Int, counter: Int): Int {
    var c1 = counter
    var number2 = x1
    while (c1 > 0) {
        number2 /= 10
        c1--
    }
    return if (number2 in 0..9) number2
    else number2 % 10
}


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var counter1 = 0
    var counter2 = 0
    var number = 1
    var part2 = fib(number)
    while (n > counter1) {
        var part1 = fib(number)
        part2 = fib(number)
        while (part1 != 0) {
            counter2++
            part1 /= 10
        }
        counter1 = counter2
        number += 1
    }

    return Digit(x1 = part2, counter = counter1 - n)
}
