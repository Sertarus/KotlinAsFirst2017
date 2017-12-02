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
        return if (result < 0) 0.0 else result
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

    fun findMidpoint(): Point = Point(begin.x / 2 + end.x / 2, begin.y / 2 + end.y / 2)
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException()
    var firstPointIndex = 0
    var secondPointIndex = 1
    for (i in 0 until points.size)
        for (k in i + 1 until points.size)
            if (points[i].distance(points[k]) > points[firstPointIndex].distance(points[secondPointIndex])) {
                firstPointIndex = i
                secondPointIndex = k
            }
    return Segment(points[firstPointIndex], points[secondPointIndex])
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle =
        Circle(diameter.findMidpoint(), diameter.begin.distance(diameter.end) / 2)

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
        val firstSin = Math.sin(angle)
        val firstCos = Math.cos(angle)
        val secondSin = Math.sin(other.angle)
        val secondCos = Math.cos(other.angle)
        val x = (firstCos * other.b - b * secondCos) / (firstSin * secondCos - secondSin * firstCos)
        val y = (other.b * firstSin - b * secondSin) / (secondCos * firstSin - firstCos * secondSin)
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
    if (s.begin.y == s.end.y) return Line(s.begin, 0.0)
    val oppositeCatheter = s.end.distance(Point(s.end.x, s.begin.y))
    val contiguousCatheter = s.end.distance(Point(s.begin.x, s.end.y))
    val angle = Math.atan(oppositeCatheter / contiguousCatheter)
    val slopeFromBeginningToEndLessThan90 = s.begin.x < s.end.x && s.begin.y < s.end.y
    val slopeFromEndToBeginningLessThan90 = s.end.x < s.begin.x && s.end.y < s.begin.y
    return if   (slopeFromBeginningToEndLessThan90 || slopeFromEndToBeginningLessThan90) Line(s.begin, angle)
           else Line(s.begin, Math.PI - angle)
}


/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val point = Segment(a, b).findMidpoint()
    var angle = lineByPoints(a, b).angle
    if (angle < Math.PI / 2) angle += Math.PI / 2 else angle -= Math.PI / 2
    return Line(point, angle)
}


/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var firstCircleIndex = 0
    var secondCircleIndex = 1
    for (i in 0 until circles.size)
        for (k in i + 1 until circles.size) {
            if (circles[firstCircleIndex].distance(circles[secondCircleIndex]) >
                    circles[i].distance(circles[k]) && i != k) {
                firstCircleIndex = i
                secondCircleIndex = k
            }
        }
    return Pair(circles[firstCircleIndex], circles[secondCircleIndex])
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
    var center = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    if (center.x == Double.NaN || center.y == Double.NaN) center =
            bisectorByPoints(a, b).crossPoint(bisectorByPoints(a, c))
    if (center.x == Double.NaN || center.y == Double.NaN) center =
            bisectorByPoints(a, c).crossPoint(bisectorByPoints(b, c))
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
    var circle = circleByDiameter(diameter(*points))
    val firstDiameterPoint = diameter(*points).begin
    val secondDiameterPoint = diameter(*points).end
    for (p in points)
        if (!circle.contains(p)) {
            for (point in points)
                if (!circleByThreePoints(firstDiameterPoint, secondDiameterPoint, p).contains(p)) break
            circle = circleByThreePoints(firstDiameterPoint, secondDiameterPoint, p)
        }
    return circle
}
