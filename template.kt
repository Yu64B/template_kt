import java.awt.Point
import kotlin.random.Random
import java.io.PrintWriter
fun main() {
    var error: Throwable? = null
    val thread = Thread(null, ::runner, "runner", 128L * 1024 * 1024)
    thread.setUncaughtExceptionHandler { _, e -> error = e }
    thread.start()
    thread.join()
    error?.let { throw it }
}
fun runner() {
    val writer = PrintWriter(System.out, false) // Boolean autoFlush
    writer.solve()
    writer.close()
}
fun PrintWriter.solve() {
    val reader = System.`in`.bufferedReader()
    
}
val DEBUG = true
fun debug(message: Any?) { if (DEBUG) print(message) }
fun debug(message: Int) { if (DEBUG) print(message) }
fun debug(message: Long) { if (DEBUG) print(message) }
fun debug(message: Byte) { if (DEBUG) print(message) }
fun debug(message: Short) { if (DEBUG) print(message) }
fun debug(message: Char) { if (DEBUG) print(message) }
fun debug(message: Boolean) { if (DEBUG) print(message) }
fun debug(message: Float) { if (DEBUG) print(message) }
fun debug(message: Double) { if (DEBUG) print(message) }
fun debug(message: CharArray) { if (DEBUG) print(message) }
fun debug(message: String) { if (DEBUG) print(message) }
fun debugln() { if (DEBUG) println() }
fun debugln(message: Any?) { if (DEBUG) println(message) }
fun debugln(message: Int) { if (DEBUG) println(message) }
fun debugln(message: Long) { if (DEBUG) println(message) }
fun debugln(message: Byte) { if (DEBUG) println(message) }
fun debugln(message: Short) { if (DEBUG) println(message) }
fun debugln(message: Char) { if (DEBUG) println(message) }
fun debugln(message: Boolean) { if (DEBUG) println(message) }
fun debugln(message: Float) { if (DEBUG) println(message) }
fun debugln(message: Double) { if (DEBUG) println(message) }
fun debugln(message: CharArray) { if (DEBUG) println(message) }
fun debugln(message: String) { if (DEBUG) println(message) }
fun <T> Iterable<T>.runLengthEncode(): List<Pair<T, Int>> {
    val result = ArrayList<Pair<T, Int>>()
    val iterator = iterator()
    if (!iterator.hasNext()) return result
    var previousElement = iterator.next()
    var count = 1
    while (iterator.hasNext()) {
        val element = iterator.next()
        if (element == previousElement) count++
        else {
            result.add(Pair(previousElement, count))
            previousElement = element
            count = 1
        }
    }
    result.add(Pair(previousElement, count))
    return result
}
fun CharSequence.runLengthEncode(): List<Pair<Char, Int>> {
    val result = ArrayList<Pair<Char, Int>>()
    if (isEmpty()) return result
    var previousElement = first()
    var count = 1
    for (i in 1 .. lastIndex) {
        val element = this[i]
        if (element == previousElement) count++
        else {
            result.add(Pair(previousElement, count))
            previousElement = element
            count = 1
        }
    }
    result.add(Pair(previousElement, count))
    return result
}
fun <T> Iterable<Pair<T, Int>>.runLengthDecode(): List<T> {
    val result = ArrayList<T>()
    for (element in this) for (i in 0 until element.second) result.add(element.first)
    return result
}
// 動作未確認
fun <T> Iterable<T>.combinations(r: Int = count()) = sequence {
    require(r >= 0) { "r must be non-negative" }
    val n = count()
    if (r > n) return@sequence
    val indices = IntArray(r) { it }
    yield(indices.map { elementAt(it) })
    while (true) {
        val i = (r - 1 downTo 0).firstOrNull { indices[it] != it + n - r }
        if (i == null) return@sequence
        indices[i]++
        for (j in i + 1 until r) indices[j] = indices[j - 1] + 1
        yield(indices.map { elementAt(it) })
    }
}
// 動作未確認
fun CharSequence.combinations(r: Int = length) = sequence {
    require(r >= 0) { "r must be non-negative" }
    val n = length
    if (r > n) return@sequence
    val indices = IntArray(r) { it }
    yield(StringBuilder(r).also { for (k in indices) it.append(get(k)) }.toString())
    while (true) {
        val i = (r - 1 downTo 0).firstOrNull { indices[it] != it + n - r }
        if (i == null) return@sequence
        indices[i]++
        for (j in i + 1 until r) indices[j] = indices[j - 1] + 1
        yield(StringBuilder(r).also { for (k in indices) it.append(get(k)) }.toString())
    }
}
// 動作未確認
fun <T> Iterable<T>.permutations(r: Int = count()) = sequence {
    require(r >= 0) { "r must be non-negative" }
    val n = count()
    if (r > n) return@sequence
    val indices = IntArray(n) { it }
    val cycles = IntArray(r) { n - it }
    yield(indices.asList().subList(0, r).map { elementAt(it) })
    while (n != 0) {
        for (i in r - 1 downTo 0) {
            cycles[i]--
            if (cycles[i] == 0) {
                indices[n - 1] = indices[i].also { for (j in i until n - 1) indices[j] = indices[j + 1] }
                cycles[i] = n - i
            }
            else {
                val j = cycles[i]
                indices[n - j] = indices[i].also { indices[i] = indices[n - j] }
                yield(indices.asList().subList(0, r).map { elementAt(it) })
                break
            }
            if (i == 0) return@sequence
        }
    }
}
// 動作未確認
fun CharSequence.permutations(r: Int = count()) = sequence {
    require(r >= 0) { "r must be non-negative" }
    val n = length
    if (r > n) return@sequence
    val indices = IntArray(n) { it }
    val cycles = IntArray(r) { n - it }
    yield(StringBuilder(r).also { for (i in indices.asList().subList(0, r)) it.append(get(i)) }.toString())
    while (n != 0) {
        for (i in r - 1 downTo 0) {
            cycles[i]--
            if (cycles[i] == 0) {
                indices[n - 1] = indices[i].also { for (j in i until n - 1) indices[j] = indices[j + 1] }
                cycles[i] = n - i
            }
            else {
                val j = cycles[i]
                indices[n - j] = indices[i].also { indices[i] = indices[n - j] }
                yield(StringBuilder(r).also { for (k in indices.asList().subList(0, r)) it.append(get(k)) }.toString())
                break
            }
            if (i == 0) return@sequence
        }
    }
}
val iterableComparator = Comparator<Iterable<Comparable<*>>> { a, b ->
    for ((aElement, bElement) in a zip b) if (aElement != bElement) return@Comparator compareValues(aElement, bElement)
    compareValues(a.count(), b.count())
}
class UnionFind(val n: Int) {
    val parents = IntArray(n) { -1 } // 親の番号 (-1で初期化)
    var groups = n // 集合の個数
    // 木の根を求める
    fun findRoot(x: Int): Int {
        if (parents[x] < 0) return x // 根
        else {
            parents[x] = findRoot(parents[x]) // 経路圧縮
            return parents[x]
        }
    }
    // xとyが同じ集合に属するか否か
    fun same(x: Int, y: Int): Boolean {
        return findRoot(x) == findRoot(y)
    }
    // xとyの属する集合を併合
    fun unite(x: Int, y: Int) {
        var xRoot = findRoot(x)
        var yRoot = findRoot(y)
        if (xRoot == yRoot) return
        if (xRoot > yRoot) { // yの属する集合のほうが大きかったら
            val tmp = xRoot
            xRoot = yRoot
            yRoot = tmp
        }
        parents[xRoot] += parents[yRoot]
        parents[yRoot] = xRoot // このとき子孫は変更しない (findRoot()で更新)
        groups--
    }
    fun size(): Int {
        return n
    }
    fun sizeOf(x: Int): Int {
        return -parents[findRoot(x)]
    }
    fun groupCount(): Int {
        return groups
    }
}
class SegmentTree<T> constructor(override val size: Int, private val identity: T, private val combine: (T, T) -> T) : AbstractList<T>() {
    private val node: MutableList<T> // How to use Array<T>?
    // private val node = MutableList(size.shl(1)) { identity }
    private val nodeSize: Int
    init {
        var tmpNodeSize = 1
        while (tmpNodeSize < size) tmpNodeSize = tmpNodeSize.shl(1)
        nodeSize = tmpNodeSize
        node = MutableList(nodeSize.shl(1)) { identity }
    }
    constructor(elements: Iterable<T>, identity: T, combine: (T, T) -> T) : this(elements.count(), identity, combine) {
        for ((index, element) in elements.withIndex()) node[nodeSize + index] = element
        // for ((index, element) in elements.withIndex()) node[size + index] = element
        for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
        // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
    }
    constructor(size: Int, init: (Int) -> T, identity: T, combine: (T, T) -> T) : this(size, identity, combine) {
        repeat(size) { index -> node[nodeSize + index] = init(index) }
        // repeat(size) { index -> node[size + index] = init(index) }
        for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
        // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
    }
    override fun get(index: Int): T {
        checkElementIndex(index, size)
        return node[nodeSize + index]
        // return node[size + index]
    }
    fun set(index: Int, value: T) {
        checkElementIndex(index, size)
        var count = nodeSize + index
        // var count = size + index
        node[count] = value
        while (count > 1) {
            count = count.shr(1)
            node[count] = combine(node[count.shl(1).or(0)], node[count.shl(1).or(1)])
        }
    }
    fun query(fromIndex: Int, toIndex: Int): T {
        checkRangeIndexes(fromIndex, toIndex, size)
        var left = nodeSize + fromIndex
        // var left = size + fromIndex
        var right = nodeSize + toIndex
        // var right = size + toIndex
        var leftVal = identity
        var rightVal = identity
        while (left < right) {
            if (left.and(1) == 1) leftVal = combine(leftVal, node[left++])
            if (right.and(1) == 1) rightVal = combine(node[--right], rightVal)
            left = left.shr(1)
            right = right.shr(1)
        }
        return combine(leftVal, rightVal)
    }
    internal fun checkElementIndex(index: Int, size: Int) {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("index: $index, size: $size")
        }
    }
    internal fun checkRangeIndexes(fromIndex: Int, toIndex: Int, size: Int) {
        if (fromIndex < 0 || toIndex > size) {
            throw IndexOutOfBoundsException("fromIndex: $fromIndex, toIndex: $toIndex, size: $size")
        }
        if (fromIndex > toIndex) {
            throw IllegalArgumentException("fromIndex: $fromIndex > toIndex: $toIndex")
        }
    }
}
// interface SegmentTree<T> : List<T> {
//     abstract operator fun set(index: Int, value: T)
//     abstract fun query(fromIndex: Int, toIndex: Int): T
//     override fun subList(fromIndex: Int, toIndex: Int): SegmentTree<T>
// }
// abstract class AbstractSegmentTree<T> : AbstractList<T>(), SegmentTree<T> {
//     override fun subList(fromIndex: Int, toIndex: Int): SegmentTree<T> = SubList(this, fromIndex, toIndex)
//     private class SubList<T>(private val list: SegmentTree<T>, private val fromIndex: Int, toIndex: Int) : AbstractSegmentTree<T>() {
//         private var _size = 0
//         init {
//             checkRangeIndexes(fromIndex, toIndex, list.size)
//             this._size = toIndex - fromIndex
//         }
//         override fun get(index: Int): T {
//             checkElementIndex(index, _size)
//             return list[fromIndex + index]
//         }
//         override fun set(index: Int, value: T) {
//             checkElementIndex(index, _size)
//             list.set(fromIndex + index, value)
//         }
//         override fun query(fromIndex: Int, toIndex: Int): T {
//             checkRangeIndexes(fromIndex, toIndex, _size)
//             return query(this.fromIndex + fromIndex, this.fromIndex + toIndex)
//         }
//         override val size: Int get() = _size
//     }
//     internal fun checkElementIndex(index: Int, size: Int) {
//         if (index < 0 || index >= size) {
//             throw IndexOutOfBoundsException("index: $index, size: $size")
//         }
//     }
//     internal fun checkRangeIndexes(fromIndex: Int, toIndex: Int, size: Int) {
//         if (fromIndex < 0 || toIndex > size) {
//             throw IndexOutOfBoundsException("fromIndex: $fromIndex, toIndex: $toIndex, size: $size")
//         }
//         if (fromIndex > toIndex) {
//             throw IllegalArgumentException("fromIndex: $fromIndex > toIndex: $toIndex")
//         }
//     }
// }
// class ListSegmentTree<T> constructor(override val size: Int, private val identity: T, private val combine: (T, T) -> T) : AbstractSegmentTree<T>() {
//     private val node: MutableList<T> // How to use Array<T>?
//     // private val node = MutableList(size.shl(1)) { identity }
//     private val nodeSize: Int
//     init {
//         var tmpNodeSize = 1
//         while (tmpNodeSize < size) tmpNodeSize = tmpNodeSize.shl(1)
//         nodeSize = tmpNodeSize
//         node = MutableList(nodeSize.shl(1)) { identity }
//     }
//     constructor(elements: Iterable<T>, identity: T, combine: (T, T) -> T) : this(elements.count(), identity, combine) {
//         for ((index, element) in elements.withIndex()) node[nodeSize + index] = element
//         // for ((index, element) in elements.withIndex()) node[size + index] = element
//         for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
//         // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
//     }
//     constructor(size: Int, init: (Int) -> T, identity: T, combine: (T, T) -> T) : this(size, identity, combine) {
//         repeat(size) { index -> node[nodeSize + index] = init(index) }
//         // repeat(size) { index -> node[size + index] = init(index) }
//         for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
//         // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
//     }
//     override fun get(index: Int): T {
//         checkElementIndex(index, size)
//         return node[nodeSize + index]
//         // return node[size + index]
//     }
//     override fun set(index: Int, value: T) {
//         checkElementIndex(index, size)
//         var count = nodeSize + index
//         // var count = size + index
//         node[count] = value
//         while (count > 1) {
//             count = count.shr(1)
//             node[count] = combine(node[count.shl(1).or(0)], node[count.shl(1).or(1)])
//         }
//     }
//     override fun query(fromIndex: Int, toIndex: Int): T {
//         checkRangeIndexes(fromIndex, toIndex, size)
//         var left = nodeSize + fromIndex
//         // var left = size + fromIndex
//         var right = nodeSize + toIndex
//         // var right = size + toIndex
//         var leftVal = identity
//         var rightVal = identity
//         while (left < right) {
//             if (left.and(1) == 1) leftVal = combine(leftVal, node[left++])
//             if (right.and(1) == 1) rightVal = combine(node[--right], rightVal)
//             left = left.shr(1)
//             right = right.shr(1)
//         }
//         return combine(leftVal, rightVal)
//     }
// }
// fun <T> SegmentTree(size: Int, identity: T, combine: (T, T) -> T): SegmentTree<T> = ListSegmentTree(size, identity, combine)
// fun <T> SegmentTree(elements: Iterable<T>, identity: T, combine: (T, T) -> T): SegmentTree<T> = ListSegmentTree(elements, identity, combine)
// fun <T> SegmentTree(size: Int, init: (Int) -> T, identity: T, combine: (T, T) -> T): SegmentTree<T> = ListSegmentTree(size, init, identity, combine)
fun <T> Iterable<T>.toSegmentTree(identity: T, combine: (T, T) -> T): SegmentTree<T> {
    return SegmentTree(this, identity, combine)
}
// import java.awt.Point
operator fun Point.component1(): Int = x
operator fun Point.component2(): Int = y
// import kotlin.random.Random
class XorShiftRandom : Random {
    private var x: UInt
    constructor() {
        x = 2463534242U
    }
    constructor(seed: Int) {
        x = seed.toUInt()
        require(x != 0U) { "Initial state must have non-zero element." }
    }
    override fun nextBits(bitCount: Int): Int {
        x = x xor x.shl(13)
        x = x xor x.shr(17)
        x = x xor x.shl(5)
        return if (bitCount <= 0) 0 else (x and (2U.shl((bitCount - 1) and 31) - 1U)).toInt()
    }
    override fun nextInt(): Int = nextBits(32)
    override fun nextInt(until: Int): Int = nextInt(0, until)
    override fun nextInt(from: Int, until: Int): Int {
        checkRangeBounds(from, until)
        return (nextInt().toUInt() % (until - from).toUInt()).toInt() + from
    }
    override fun nextLong(): Long = nextInt().toLong().shl(32) + nextInt()
    override fun nextLong(until: Long): Long = nextLong(0, until)
    override fun nextLong(from: Long, until: Long): Long {
        checkRangeBounds(from, until)
        return (nextLong().toULong() % (until - from).toULong()).toLong() + from
    }
    private fun checkRangeBounds(from: Int, until: Int) = require(until > from) { boundsErrorMessage(from, until) }
    private fun checkRangeBounds(from: Long, until: Long) = require(until > from) { boundsErrorMessage(from, until) }
    private fun boundsErrorMessage(from: Any, until: Any) = "Random range is empty: [$from, $until)."
}
// 未完成
class RandomizedSet<E>(private val random: Random = XorShiftRandom()) : AbstractMutableSet<E>() {
    private val indexes = mutableMapOf<E, Int>()
    private val values = mutableListOf<E>()
    private val valuesSet = mutableSetOf<E>() // for iterator function
    override val size: Int
        get() = indexes.size
    override fun add(element: E): Boolean {
        if (indexes.contains(element)) {
            return false
        }
        values.add(element)
        indexes[element] = values.lastIndex
        valuesSet.add(element)
        return true
    }
    override fun contains(element: E): Boolean {
        return indexes.contains(element)
    }
    override fun iterator(): MutableIterator<E> {
        // val iterator = indexes.keys.iterator()
        val iterator = valuesSet.iterator()
        var nextValue: E? = null
        return object : MutableIterator<E> {
            override fun hasNext(): Boolean {
                return iterator.hasNext()
            }
            override fun next(): E {
                return iterator.next().also { nextValue = it }
            }
            // 動作未確認
            override fun remove() {
                val index = indexes[nextValue]
                iterator.remove()
                removeFromList(index!!)
                indexes.remove(nextValue)
                nextValue = null
            }
        }
    }
    override fun remove(element: E): Boolean {
        val index = indexes[element]
        if (index == null) {
            return false
        }
        valuesSet.remove(element)
        removeFromList(index)
        indexes.remove(element)
        return true
    }
    private fun removeFromList(index: Int) {
        val lastElement = values[values.lastIndex]
        values[index] = lastElement
        indexes[lastElement] = index
        values.removeAt(values.lastIndex)
    }
    fun getRandom(): E {
        val randomIndex = random.nextInt(size)
        return values[randomIndex]
    }
}
fun <T> Iterable<T>.toRandomizedSet(): RandomizedSet<T> {
    return RandomizedSet<T>().also { for (element in this) it.add(element) }
}
// fun <T : Comparable<T>> List<T?>.ceilingIndexOf(element: T?, fromIndex: Int = 0, toIndex: Int = size): Int {
//     rangeCheck(size, fromIndex, toIndex)
// }
// private fun rangeCheck(size: Int, fromIndex: Int, toIndex: Int) {
//     when {
//         fromIndex > toIndex -> throw IllegalArgumentException("fromIndex ($fromIndex) is greater than toIndex ($toIndex).")
//         fromIndex < 0 -> throw IndexOutOfBoundsException("fromIndex ($fromIndex) is less than zero.")
//         toIndex > size -> throw IndexOutOfBoundsException("toIndex ($toIndex) is greater than size ($size).")
//     }
// }
public fun <T : Comparable<T>> Iterable<T>.maxOrNull(): T? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var max = iterator.next()
    while (iterator.hasNext()) {
        val e = iterator.next()
        if (max < e) max = e
    }
    return max
}
public fun <T : Comparable<T>> Iterable<T>.minOrNull(): T? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var min = iterator.next()
    while (iterator.hasNext()) {
        val e = iterator.next()
        if (min > e) min = e
    }
    return min
}
fun <T> List<List<T>>.transposed(): List<List<T>> {
    require(size > 0) { "The number of rows must be at least 1." }
    require(all { it.size == first().size }) { "All column numbers must match." }
    return List(first().size) { j -> List(size) { i -> this[i][j] } }
}
fun List<CharSequence>.transposedString(): List<String> {
    require(size > 0) { "The number of rows must be at least 1." }
    require(all { it.length == first().length }) { "All column numbers must match." }
    return List(first().length) { j -> StringBuilder(size).also { for (i in 0 until size) it.append(this[i][j]) }.toString() }
}
fun List<List<Long>>.kMeans(k: Int, maxTimes: Int = 1000, random: Random = XorShiftRandom()): List<List<List<Long>>> {
    require(size > 0) { "The size of elements must be at least 1." }
    require(all { it.size == first().size }) { "The dimension sizes of all elements must match." }
    require(k > 0) { "k must be at least 1." }
    val clusters = Array(size) { random.nextInt(k) }
    val centroids = List(k) { Array(first().size) { 0.0 } }
    // 高速化のため変数を使いまわす
    val elementsCounts = Array(k) { 0 }
    val centroidsSums = List(k) { Array(first().size) { 0L } }
    for (time in 0 until maxTimes) {
        // クラスターの重心の更新
        for (cluster in 0 until k) {
            elementsCounts[cluster] = 0
            for (dim in 0 until centroidsSums[cluster].size) {
                centroidsSums[cluster][dim] = 0
            }
        }
        for (index in 0 until size) {
            val cluster = clusters[index]
            elementsCounts[cluster]++
            for (dim in 0 until this[index].size) {
                centroidsSums[cluster][dim] += this[index][dim]
            }
        }
        for (cluster in 0 until k) {
            if (elementsCounts[cluster] != 0) {
                for (dim in 0 until centroids[cluster].size) {
                    centroids[cluster][dim] = centroidsSums[cluster][dim].toDouble() / elementsCounts[cluster]
                }
            }
            // クラスターに所属する要素が0のときは、そのクラスターの重心をランダムな要素の座標に一致させる
            else {
                val randomIndex = random.nextInt(size)
                for (dim in 0 until centroids[cluster].size) {
                    centroids[cluster][dim] = this[randomIndex][dim].toDouble()
                }
            }
        }
        // 要素の所属の更新
        var movedCount = 0
        for (index in 0 until size) {
            var closestDistance = (this[index] zip centroids[0]).map { Math.abs(it.first - it.second) * Math.abs(it.first - it.second) }.sum()
            var closestCluster = 0
            for (cluster in 0 until k) {
                val distance = (this[index] zip centroids[cluster]).map { Math.abs(it.first - it.second) * Math.abs(it.first - it.second) }.sum()
                if (distance < closestDistance) {
                    closestDistance = distance
                    closestCluster = cluster
                }
            }
            if (closestCluster != clusters[index]) {
                clusters[index] = closestCluster
                movedCount++
            }
        }
        if (movedCount == 0) {
            break
        }
    }
    val results = List(k) { mutableListOf<List<Long>>() }
    for (index in 0 until size) {
        val cluster = clusters[index]
        results[cluster].add(this[index])
    }
    return results
}
fun primeFactorize(x: Long): List<Pair<Long, Int>> {
    val result = mutableListOf<Pair<Long, Int>>()
    var remainingValue = x
    var primeNumber = 2L
    while (primeNumber * primeNumber <= remainingValue) {
        if (remainingValue % primeNumber != 0L) {
            primeNumber++
            continue
        }
        var count = 0
        while (remainingValue % primeNumber == 0L) {
            count++
            remainingValue /= primeNumber
        }
        result.add(Pair(primeNumber, count))
        primeNumber++
    }
    if (remainingValue != 1L) {
        result.add(Pair(remainingValue, 1))
    }
    return result
}
// fun Long.primeFactorize() = primeFactorize(this)
class MutableMultiSet<T> {
    private class Node<T>(var value: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null
        var height = 0
        var size = 0
        fun update(): Node<T> {
            height = Math.max(left!!.height, right!!.height) + 1
            size = left!!.size + right!!.size + 1
            return this
        }
        fun rotateLeft(): Node<T> {
            val newRoot = right!!
            right = newRoot.left
            newRoot.left = this
            update()
            return newRoot.update()
        }
        fun rotateRight(): Node<T> {
            val newRoot = left!!
            left = newRoot.right
            newRoot.right = this
            update()
            return newRoot.update()
        }
        fun calcHeightDiff() = left!!.height - right!!.height
        fun balance(): Node<T> {
            val heightDiff = calcHeightDiff()
            if (heightDiff == 2) {
                if (left!!.calcHeightDiff() < 0) left = left!!.rotateLeft()
                return rotateRight()
            }
            else if (heightDiff == -2) {
                if (right!!.calcHeightDiff() > 0) right = right!!.rotateRight()
                return rotateLeft()
            }
            else return update()
        }
    }
}
