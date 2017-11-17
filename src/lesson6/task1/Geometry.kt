@file:Suppress("UNUSED_PARAMETER")

package lesson6.task1

import lesson1.task1.sqr

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val result = other.center.distance(this.center) - radius - other.radius
        return if (result < 0) 0.0
        else result
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = p.distance(this.center) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    var firstPointIndex = 0
    var secondPointIndex = 1
    try {
        for (i in 0 until points.size)
            for (k in 0 until points.size)
                if (points[i].distance(points[k]) > points[firstPointIndex].distance(points[secondPointIndex])) {
                    firstPointIndex = i
                    secondPointIndex = k
                }
        return Segment(points[firstPointIndex], points[secondPointIndex])
    } catch (e: IndexOutOfBoundsException) {
        throw IllegalArgumentException()
    }
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle = Circle(Point((diameter.begin.x + diameter.end.x) / 2,
        (diameter.begin.y + diameter.end.y) / 2), (diameter.begin.distance(diameter.end)) / 2)

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val y = (other.b * Math.sin(angle) - b * Math.sin(other.angle)) /
                (Math.cos(other.angle) * Math.sin(angle) - Math.cos(angle) * Math.sin(other.angle))
        val x = (y * Math.cos(angle) - b) / Math.sin(angle)
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    if (s.begin.x == s.end.x) return Line(s.begin, Math.PI / 2)
    else if (s.begin.y == s.end.y) return Line(s.begin, 0.0)
    val oppositeCatheter = s.end.distance(Point(s.end.x, s.begin.y))
    val contiguousCatheter = s.end.distance(Point(s.begin.x, s.end.y))
    val angle = Math.atan(oppositeCatheter / contiguousCatheter)
    return if ((s.begin.x < s.end.x && s.begin.y < s.end.y) || (s.end.x < s.begin.x && s.end.y < s.begin.y))
        Line(s.begin, angle)
    else Line(s.begin, Math.PI - angle)
}


/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val s = Segment(a, b)
    return lineBySegment(s)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val point = Point(a.x / 2 + b.x / 2, a.y / 2 + b.y / 2)
    val angle = lineByPoints(a, b).angle
    return if (angle < Math.PI / 2) Line(point, angle + Math.PI / 2)
    else Line(point, angle - Math.PI / 2)
}


/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    var firstCircleIndex = 0
    var secondCircleIndex = 1
    try {
        for (i in 0 until circles.size)
            for (k in 0 until circles.size) {
                if (circles[firstCircleIndex].distance(circles[secondCircleIndex]) > circles[i].distance(circles[k]) && i != k) {
                    firstCircleIndex = i
                    secondCircleIndex = k
                }
            }
        return Pair(circles[firstCircleIndex], circles[secondCircleIndex])
    } catch (e: IndexOutOfBoundsException) {
        throw IllegalArgumentException()
    }
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val center = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    return Circle(center, center.distance(a))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    if (points.isEmpty()) throw IllegalArgumentException()
    if (points.size == 1) return Circle(points[0], 0.0)
    val mostRemotePoints = mutableListOf(points[0], points[1])
    for (i in 0 until points.size)
        for (k in 0 until points.size)
            if (points[i].distance(points[k]) > mostRemotePoints[0].distance(mostRemotePoints[1])) {
                mostRemotePoints[0] = points[i]
                mostRemotePoints[1] = points[k]
            }
    var circle = Circle(Point(mostRemotePoints[0].x / 2 + mostRemotePoints[1].x / 2, mostRemotePoints[0].y / 2 + mostRemotePoints[1].y / 2), mostRemotePoints[0].distance(mostRemotePoints[1]) / 2)
    var contain = true
    for (i in points)
        if (!circle.contains(i)) contain = false
    if (contain) return circle
    circle = Circle(Point(0.0, 0.0), Double.MAX_VALUE)
    contain = true
    for (i in 0 until points.size)
        for (k in 0 until points.size)
            for (j in 0 until points.size) {
                if (i != k && i != j && k != j) {
                    println("$i,$j,$k")
                    println(circleByThreePoints(points[i], points[k], points[j]))
                    println(circle)
                    for (p in points)
                        if (!circleByThreePoints(points[i], points[k], points[j]).contains(p)) contain = false
                    if (contain && circleByThreePoints(points[i], points[k], points[j]).radius < circle.radius)
                        circle = circleByThreePoints(points[i], points[k], points[j])
                    contain = true
                }
            }
    return circle
}
