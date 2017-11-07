@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateStrToDigit(str: String): String {
    try {
        val parts = str.split(" ")
        if (parts.size != 3) return ""
        if (parts[0].toInt() !in 1..31) return ""
        val month = when (parts[1]) {
            "января" -> 1
            "февраля" -> 2
            "марта" -> 3
            "апреля" -> 4
            "мая" -> 5
            "июня" -> 6
            "июля" -> 7
            "августа" -> 8
            "сентября" -> 9
            "октября" -> 10
            "ноября" -> 11
            "декабря" -> 12
            else -> return ""
        }
        if (parts[2].toInt() < 0) return ""
        return String.format("%02d.%02d.%d", parts[0].toInt(), month, parts[2].toInt())
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    val result = StringBuilder()
    if (parts.size != 3) return ""
    try {
        if (parts[0].toInt() !in 1..31) return ""
        if (parts[0].first() == '0') result.append(parts[0].toInt())
        else result.append(parts[0])
        val month = when (parts[1]) {
            "01" -> " января "
            "02" -> " февраля "
            "03" -> " марта "
            "04" -> " апреля "
            "05" -> " мая "
            "06" -> " июня "
            "07" -> " июля "
            "08" -> " августа "
            "09" -> " сентября "
            "10" -> " октября "
            "11" -> " ноября "
            "12" -> " декабря "
            else -> return ""
        }
        result.append(month)
        if (parts[2].toInt() >= 0) result.append(parts[2]) else return ""
        return result.toString()
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    if (phone == "") return ""
    val phoneNumber = StringBuilder()
    if (phone.first() == '+' || phone.first() in '0'..'9') phoneNumber.append(phone.first())
    for (char in phone.substring(1, phone.length)) {
        if (char in '0'..'9') phoneNumber.append(char)
        else if (char !in "-() ") return ""
    }
    return phoneNumber.toString()
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (jumps == "") return -1
    return try {
        var result = -1
        val jump = jumps.split(Regex(" +"))
        for (element in jump) {
            if (element !in "%-" && element.toInt() > result) result = element.toInt()
        }
        result
    } catch (e: NumberFormatException) {
        return -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    for (char in jumps)
        if (char !in '0'..'9' && char !in "%+- ")
            return -1
    val jump = jumps.split(" ")
    var plusNumber = 0
    var minusNumber = 0
    for (element in jump)
        for (char in element) {
            if (char == '+') plusNumber += 1
            if (char == '-') minusNumber += 1
            if (minusNumber > 1 || plusNumber > 1) return -1
            plusNumber = 0
            minusNumber = 0
        }
    var result = -1
    for (i in 1 until jump.size) {
        if ("+" in jump[i] && jump[i - 1].toInt() > result)
            result = jump[i - 1].toInt()
    }
    return result
}


/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    return try {
        val expressionList = expression.split(" ")
        var result = expressionList[0].toInt()
        for (i in 1 until expressionList.size step 2) {
            when {
                expressionList[i] == "+" -> result += expressionList[i + 1].toInt()
                expressionList[i] == "-" -> result -= expressionList[i + 1].toInt()
                else -> throw IllegalArgumentException("Description")
            }
        }
        result
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException("Description")
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val strList = str.split(" ")
    var duplicateWordNumber = -1
    var result = -1
    for (i in 0 until strList.size - 1) {
        if (strList[i].toLowerCase() == strList[i + 1].toLowerCase()) {
            duplicateWordNumber = i
            break
        }
    }
    for (i in 0 until duplicateWordNumber)
        result += strList[i].length + 1
    return if (result == -1) result
    else result + 1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    if (description == "") return ""
    val descriptionList = description.split("; ")
    val priceList = mutableListOf<Double>()
    try {
        return try {
            for (i in 0 until descriptionList.size)
                priceList.add(descriptionList[i].split(" ")[1].toDouble())
            descriptionList[priceList.indexOf(priceList.max())].split(" ")[0]
        } catch (e: NumberFormatException) {
            return ""
        }
    } catch (e: IndexOutOfBoundsException) {
        return ""
    }
}


/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (roman == "") return -1
    var result = 0
    var counter = 0
    for (i in 0 until roman.length - 1) {
        when {
            roman[counter] == 'M' -> {
                result += 1000
                counter++
            }
            roman[counter] == 'D' && roman[counter + 1] in "M" -> return -1

            roman[counter] == 'D' -> {
                result += 500
                counter++
            }
            roman[counter] == 'C' && (roman[counter + 1] in "MD") -> {
                result += -100
                counter++
            }
            roman[counter] == 'C' -> {
                result += 100
                counter++
            }
            roman[counter] == 'L' && (roman[counter + 1] in "MDC") -> return -1
            roman[counter] == 'L' -> {
                result += 50
                counter++
            }
            roman[counter] == 'X' && (roman[counter + 1] in "MD") -> return -1
            roman[counter] == 'X' && (roman[counter + 1] in "CL") -> {
                result += -10
                counter++
            }
            roman[counter] == 'X' -> {
                result += 10
                counter++
            }
            roman[counter] == 'V' && (roman[counter + 1] !in "IV") -> return -1
            roman[counter] == 'V' -> {
                result += 5
                counter++
            }
            roman[counter] == 'I' && (roman[counter + 1] !in "IVX") -> return -1
            roman[counter] == 'I' && (roman[counter + 1] in "VX") -> {
                result += -1
                counter++
            }
            roman[counter] == 'I' -> {
                result += 1
                counter++
            }
            else -> return -1
        }
    }
    result += when {
        roman.last() == 'I' -> 1
        roman.last() == 'V' -> 5
        roman.last() == 'X' -> 10
        roman.last() == 'L' -> 50
        roman.last() == 'C' -> 100
        roman.last() == 'D' -> 500
        roman.last() == 'M' -> 1000
        else -> return -1
    }
    return result
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    if ('[' in commands && ']' !in commands) throw IllegalArgumentException()
    for (char in commands)
        if (char !in "><+-[] ") throw IllegalArgumentException()
    val regularBracketList = mutableListOf<Int>()
    val backwardBracketList = mutableListOf<Int>()
    var pairsOfBracketsNumber = 0
    var backwardBracketNumber = 0
    var regularBracketNumber = 0
    val commandsCopy = mutableListOf<Char>()
    for (char in commands)
        commandsCopy.add(char)
    while ('[' in commandsCopy) {
        for (i in 0 until commandsCopy.size)
            if (commandsCopy[i] == ']') {
                backwardBracketList.add(i)
                for (k in i downTo 0)
                    if (commandsCopy[k] == '[') {
                        regularBracketList.add(k)
                        commandsCopy[k] = ' '
                        pairsOfBracketsNumber++
                        break
                    }
            }
        if (pairsOfBracketsNumber < backwardBracketList.size - 1) throw IllegalArgumentException()
    }
    for (char in commands) {
        if (char == '[') regularBracketNumber++
        if (char == ']') backwardBracketNumber++
    }
    if (regularBracketNumber != backwardBracketNumber || (regularBracketNumber + backwardBracketNumber) / 2 != pairsOfBracketsNumber)
        throw IllegalArgumentException()
    val conveyer = mutableListOf<Int>()
    while (conveyer.size != cells)
        conveyer.add(0)
    var currentCell = cells / 2
    var currentCharIndex = 0
    var completedCommands = 0
    while (currentCharIndex < commands.length && completedCommands < limit) {
        when {
            commands[currentCharIndex] == '>' -> {
                currentCell++
                if (currentCell > cells - 1) throw IllegalStateException()
                currentCharIndex++
                completedCommands++
            }
            commands[currentCharIndex] == '<' -> {
                currentCell--
                if (currentCell < 0) throw IllegalStateException()
                currentCharIndex++
                completedCommands++
            }
            commands[currentCharIndex] == '+' -> {
                conveyer[currentCell]++
                currentCharIndex++
                completedCommands++
            }
            commands[currentCharIndex] == '-' -> {
                conveyer[currentCell]--
                currentCharIndex++
                completedCommands++
            }
            commands[currentCharIndex] == '[' -> {
                if (conveyer[currentCell] == 0) {
                    for (i in 0 until regularBracketList.size)
                        if (currentCharIndex == regularBracketList[i]) {
                            currentCharIndex = backwardBracketList[i] + 1
                            completedCommands++
                        }
                } else {
                    currentCharIndex++
                    completedCommands++
                }
            }
            commands[currentCharIndex] == ']' -> {
                if (conveyer[currentCell] != 0) {
                    for (i in 0 until backwardBracketList.size)
                        if (currentCharIndex == backwardBracketList[i]) {
                            currentCharIndex = regularBracketList[i] + 1
                            completedCommands++
                        }

                } else {
                    currentCharIndex++
                    completedCommands++
                }
            }
            commands[currentCharIndex] == ' ' -> {
                currentCharIndex++
                completedCommands++
            }
        }
    }
    return conveyer
}
