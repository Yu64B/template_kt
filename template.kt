import java.awt.Point
import kotlin.random.Random
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.TimeUnit
import java.io.InputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.PriorityQueue
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
    // val (H, W) = reader.readLine()!!.split(' ').map { it.toInt() }
    // val Q = reader.readLine()!!.toInt()
    // val queries = List(Q) { reader.readLine()!!.split(' ').map { it.toInt() } }
    // val grids = List(H + 2) { BooleanArray(W + 2) { false } }
    // val uf = List(H + 2) { r -> List(W + 2) { c -> Point(c, r) } }.flatten().toMutableUnionFind()
    // val dxs = listOf(0, 0, -1, 1)
    // val dys = listOf(-1, 1, 0, 0)
    // for (q in queries) {
    //     val t = q[0]
    //     if (t == 1) {
    //         val (r, c) = q.drop(1)
    //         grids[r][c] = true
    //         for ((dx, dy) in dxs zip dys) {
    //             if (grids[r + dy][c + dx]) {
    //                 uf.unite(Point(r, c), Point(r + dy, c + dx))
    //             }
    //         }
    //     }
    //     else {
    //         val (ra, ca, rb, cb) = q.drop(1)
    //         println(if (grids[ra][ca] && grids[rb][cb] && uf.same(Point(ra, ca), Point(rb, cb))) "Yes" else "No")
    //     }
    // }
    val pb = ProcessBuilder("echo", "a").apply {
        redirectOutput(Redirect.PIPE)
        // redirectOutput(System.out)
    }
    val process = pb.start()
    val os = process.getOutputStream()
    val writer = PrintWriter(os, true)
    writer.println("1+1")
    writer.println("aaa")
    writer.println("exit()")
    val _is = process.getInputStream()
    val isr = InputStreamReader(_is)
    val br = BufferedReader(isr)
    println(br.readLine())
    process.waitFor(2, TimeUnit.SECONDS)
    println(br.readLine())
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
    require(r >= 0) { "r must be non-negative." }
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
    require(r >= 0) { "r must be non-negative." }
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
    require(r >= 0) { "r must be non-negative." }
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
    require(r >= 0) { "r must be non-negative." }
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
val pairComparator = Comparator<Pair<Comparable<*>, Comparable<*>>> { a, b -> 
    if (a.first != b.first) compareValues(a.first, b.first) else compareValues(a.second, b.second)
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
    fun members(x: Int): List<Int> {
        val root = findRoot(x)
        return (0 until n).filter { findRoot(it) == root }
    }
    fun roots(): List<Int> {
        return parents.withIndex().filter { it.value < 0 }.map { it.index }
    }
    fun allGroupMembers(): Map<Int, List<Int>> {
        val groupMembers = mutableMapOf<Int, MutableList<Int>>()
        for (member in 0 until n) {
            val root = findRoot(member)
            val members = groupMembers[root]
            if (members == null) groupMembers[root] = mutableListOf(member)
            else members.add(member)
        }
        return groupMembers
    }
}
class SegmentTree<E> constructor(override val size: Int, private val identity: E, private val combine: (E, E) -> E) : AbstractList<E>() {
    private val node: MutableList<E> // How to use Array<E>?
    // private val node = MutableList(size.shl(1)) { identity }
    private val nodeSize: Int
    init {
        var tmpNodeSize = 1
        while (tmpNodeSize < size) tmpNodeSize = tmpNodeSize.shl(1)
        nodeSize = tmpNodeSize
        node = MutableList(nodeSize.shl(1)) { identity }
    }
    constructor(elements: Iterable<E>, identity: E, combine: (E, E) -> E) : this(elements.count(), identity, combine) {
        for ((index, element) in elements.withIndex()) node[nodeSize + index] = element
        // for ((index, element) in elements.withIndex()) node[size + index] = element
        for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
        // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
    }
    constructor(size: Int, init: (Int) -> E, identity: E, combine: (E, E) -> E) : this(size, identity, combine) {
        repeat(size) { index -> node[nodeSize + index] = init(index) }
        // repeat(size) { index -> node[size + index] = init(index) }
        for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
        // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
    }
    override fun get(index: Int): E {
        checkElementIndex(index, size)
        return node[nodeSize + index]
        // return node[size + index]
    }
    fun set(index: Int, value: E) {
        checkElementIndex(index, size)
        var count = nodeSize + index
        // var count = size + index
        node[count] = value
        while (count > 1) {
            count = count.shr(1)
            node[count] = combine(node[count.shl(1).or(0)], node[count.shl(1).or(1)])
        }
    }
    fun query(fromIndex: Int, toIndex: Int): E {
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
// interface SegmentTree<E> : List<E> {
//     abstract operator fun set(index: Int, value: E)
//     abstract fun query(fromIndex: Int, toIndex: Int): E
//     override fun subList(fromIndex: Int, toIndex: Int): SegmentTree<E>
// }
// abstract class AbstractSegmentTree<E> : AbstractList<E>(), SegmentTree<E> {
//     override fun subList(fromIndex: Int, toIndex: Int): SegmentTree<E> = SubList(this, fromIndex, toIndex)
//     private class SubList<E>(private val list: SegmentTree<E>, private val fromIndex: Int, toIndex: Int) : AbstractSegmentTree<E>() {
//         private var _size = 0
//         init {
//             checkRangeIndexes(fromIndex, toIndex, list.size)
//             this._size = toIndex - fromIndex
//         }
//         override fun get(index: Int): E {
//             checkElementIndex(index, _size)
//             return list[fromIndex + index]
//         }
//         override fun set(index: Int, value: E) {
//             checkElementIndex(index, _size)
//             list.set(fromIndex + index, value)
//         }
//         override fun query(fromIndex: Int, toIndex: Int): E {
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
// class ImplementationSegmentTree<E> constructor(override val size: Int, private val identity: E, private val combine: (E, E) -> E) : AbstractSegmentTree<E>() {
//     private val node: MutableList<E> // How to use Array<E>?
//     // private val node = MutableList(size.shl(1)) { identity }
//     private val nodeSize: Int
//     init {
//         var tmpNodeSize = 1
//         while (tmpNodeSize < size) tmpNodeSize = tmpNodeSize.shl(1)
//         nodeSize = tmpNodeSize
//         node = MutableList(nodeSize.shl(1)) { identity }
//     }
//     constructor(elements: Iterable<E>, identity: E, combine: (E, E) -> E) : this(elements.count(), identity, combine) {
//         for ((index, element) in elements.withIndex()) node[nodeSize + index] = element
//         // for ((index, element) in elements.withIndex()) node[size + index] = element
//         for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
//         // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
//     }
//     constructor(size: Int, init: (Int) -> E, identity: E, combine: (E, E) -> E) : this(size, identity, combine) {
//         repeat(size) { index -> node[nodeSize + index] = init(index) }
//         // repeat(size) { index -> node[size + index] = init(index) }
//         for (index in nodeSize - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)])
//         // for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
//     }
//     override fun get(index: Int): E {
//         checkElementIndex(index, size)
//         return node[nodeSize + index]
//         // return node[size + index]
//     }
//     override fun set(index: Int, value: E) {
//         checkElementIndex(index, size)
//         var count = nodeSize + index
//         // var count = size + index
//         node[count] = value
//         while (count > 1) {
//             count = count.shr(1)
//             node[count] = combine(node[count.shl(1).or(0)], node[count.shl(1).or(1)])
//         }
//     }
//     override fun query(fromIndex: Int, toIndex: Int): E {
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
// fun <T> SegmentTree(size: Int, identity: T, combine: (T, T) -> T): SegmentTree<T> = ImplementationSegmentTree(size, identity, combine)
// fun <T> SegmentTree(elements: Iterable<T>, identity: T, combine: (T, T) -> T): SegmentTree<T> = ImplementationSegmentTree(elements, identity, combine)
// fun <T> SegmentTree(size: Int, init: (Int) -> T, identity: T, combine: (T, T) -> T): SegmentTree<T> = ImplementationSegmentTree(size, init, identity, combine)
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
        require(x != 0U) { "seed must be non-zero." }
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
    return RandomizedSet<T>().also { for (item in this) it.add(item) }
}
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
class MutableUnionFind<E> : AbstractMutableSet<E>() {
    val map = mutableMapOf<E, MutableSet<E>>()
    val groups = mutableMapOf<E, MutableSet<E>>()
    override val size: Int
        get() = map.size
    override fun add(element: E): Boolean {
        if (map.containsKey(element)) {
            return false
        }
        map[element] = HashSet<E>(2).apply { add(element) }
        groups[element] = HashSet<E>(2).apply { add(element) }
        return true
    }
    override fun contains(element: E): Boolean {
        return map.containsKey(element)
    }
    override fun iterator(): MutableIterator<E> {
        return object : MutableIterator<E> {
            val iterator = map.entries.iterator()
            var nextEntry: MutableMap.MutableEntry<E, MutableSet<E>>? = null
            override fun hasNext(): Boolean {
                return iterator.hasNext()
            }
            override fun next(): E {
                return iterator.next().also { nextEntry = it }.key
            }
            override fun remove() {
                val element = nextEntry!!.key
                val set = nextEntry!!.value
                set.remove(element)
                if (groups.remove(element) != null && set.isNotEmpty()) {
                    groups[set.first()] = set
                }
                iterator.remove()
                nextEntry = null
            }
        }
    }
    override fun remove(element: E): Boolean {
        val set = map[element]
        if (set == null) {
            return false
        }
        set.remove(element)
        if (groups.remove(element) != null && set.isNotEmpty()) {
            groups[set.first()] = set
        }
        map.remove(element)
        return true
    }
    fun same(x: E, y: E): Boolean {
        val xSet = map[x] ?: throw NoSuchElementException("Collection contains no element matching the x.")
        val ySet = map[y] ?: throw NoSuchElementException("Collection contains no element matching the y.")
        return xSet === ySet
    }
    fun unite(x: E, y: E): Boolean {
        val xSet = map[x] ?: throw NoSuchElementException("Collection contains no element matching the x.")
        val ySet = map[y] ?: throw NoSuchElementException("Collection contains no element matching the y.")
        if (xSet === ySet) {
            return false
        }
        val smallSet = if (xSet.size > ySet.size) ySet else xSet
        val largeSet = if (xSet.size > ySet.size) xSet else ySet
        val iterator = smallSet.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            largeSet.add(element)
            map[element] = largeSet
            groups.remove(element)
            iterator.remove()
        }
        return true
    }
}
fun <T> Iterable<T>.toMutableUnionFind(): MutableUnionFind<T> {
    return MutableUnionFind<T>().also { for (item in this) it.add(item) }
}
fun binarySearch(from: Int, to: Int, predicate: (Int) -> Boolean): Int? {
    if (from == to) {
        return null
    }
    var ok = from
    var ng = to
    while (Math.abs(ng - ok) > 1) {
        val mid = (ok + ng).ushr(1)
        if (predicate(mid)) {
            ok = mid
        }
        else {
            ng = mid
        }
    }
    return if (predicate(ok)) ok else null
}
fun binarySearch(from: Long, to: Long, predicate: (Long) -> Boolean): Long? {
    if (from == to) {
        return null
    }
    var ok = from
    var ng = to
    while (Math.abs(ng - ok) > 1) {
        val mid = (ok + ng).ushr(1)
        if (predicate(mid)) {
            ok = mid
        }
        else {
            ng = mid
        }
    }
    return if (predicate(ok)) ok else null
}
fun <T> List<T>.binarySearch(fromIndex: Int = 0, toIndex: Int = size, predicate: (T) -> Boolean): Int {
    rangeCheck(size, fromIndex, toIndex)
    if (fromIndex == toIndex) {
        return -1
    }
    var ok = fromIndex
    var ng = toIndex
    while (Math.abs(ng - ok) > 1) {
        val mid = (ok + ng).ushr(1)
        if (predicate(get(mid))) {
            ok = mid
        }
        else {
            ng = mid
        }
    }
    return if (predicate(get(ok))) ok else -1
}
fun <T : Comparable<T>> List<T?>.ceilingIndexOf(element: T?, fromIndex: Int = 0, toIndex: Int = size): Int {
    rangeCheck(size, fromIndex, toIndex)
    if (fromIndex == 0 && toIndex == size) {
        return asReversed().binarySearch { compareValues(it, element) >= 0 }
    }
    else {
        return subList(fromIndex, toIndex).asReversed().binarySearch { compareValues(it, element) >= 0 }
    }
}
fun <T : Comparable<T>> List<T?>.floorIndexOf(element: T?, fromIndex: Int = 0, toIndex: Int = size): Int {
    return binarySearch(fromIndex, toIndex) { compareValues(it, element) <= 0 }
}
fun <T : Comparable<T>> List<T?>.higherIndexOf(element: T?, fromIndex: Int = 0, toIndex: Int = size): Int {
    rangeCheck(size, fromIndex, toIndex)
    if (fromIndex == 0 && toIndex == size) {
        return asReversed().binarySearch { compareValues(it, element) > 0 }
    }
    else {
        return subList(fromIndex, toIndex).asReversed().binarySearch { compareValues(it, element) > 0 }
    }
}
fun <T : Comparable<T>> List<T?>.lowerIndexOf(element: T?, fromIndex: Int = 0, toIndex: Int = size): Int {
    return binarySearch(fromIndex, toIndex) { compareValues(it, element) < 0 }
}
private fun rangeCheck(size: Int, fromIndex: Int, toIndex: Int) {
    when {
        fromIndex > toIndex -> throw IllegalArgumentException("fromIndex ($fromIndex) is greater than toIndex ($toIndex).")
        fromIndex < 0 -> throw IndexOutOfBoundsException("fromIndex ($fromIndex) is less than zero.")
        toIndex > size -> throw IndexOutOfBoundsException("toIndex ($toIndex) is greater than size ($size).")
    }
}
// data class Edge<K, V>(val to: K, val weight: V) : Map.Entry<K, V> {
//     override val key: K
//         get() = to
//     override val value: V
//         get() = weight
// }
// // data class MutableEdge<K, V>(val to: K, var weight: V) : MutableMap.MutableEntry<K, V> {
// //     override val key: K
// //         get() = to
// //     override val value: V
// //         get() = weight
// //     override fun setValue(newValue: V): V = newValue.also { weight = newValue }
// // }
// // typealias MutableEdge<K, V> = MutableMap.MutableEntry<K, V>
// // val MutableEdge<K, V>.to: K = this.key
// class SimpleGraph<K, V>() : AbstractMutableMap<K, V>() {
//     interface MutableEdge<K, V> : MutableMap.MutableEntry<K, V> {
//         val to: K
//             get() = key
//         val weight: V
//             get() = value
//     }
//     val map: MutableMap<K, V> = HashMap<K, V>()
//     // override val entries: MutableSet<MutableEdge<K, V>>
//     //     get() = map.entries
//     override val entries: MutableSet<MutableEdge<K, V>>
//         get() = object : AbstractMutableSet<MutableEdge<K, V>>() {
//             val entries = map.entries
//             override val size: Int
//                 get() = entries.size
//             override fun add(element: MutableEdge<K, V>): Boolean = entries.add(element)
//             override fun iterator(): MutableIterator<MutableEdge<K, V>> = object : MutableIterator<MutableEdge<K, V>> {
//                 val iterator = entries.iterator()
//                 override fun hasNext(): Boolean = iterator.hasNext()
//                 override fun next(): MutableEdge<K, V> = iterator.next().let { MutableEdge(it.key, it.value) }
//                 override fun remove(): Unit = iterator.remove()
//             }
//         }
//     override fun put(key: K, value: V): V? = map.put(key, value)
// }
// val list: List<Map.Entry<Int, Int>> = listOf<Edge<Int, Int>>()
// interface Parent<K, V> {
//     val test: Map<K, Map.Entry<K, V>>
// }
// class Child<K, V>() : Parent<K, V> {
//     override val test = mutableMapOf<K, Edge<K, V>>()
// }
class IntCppRope() : AbstractMutableList<Int>() {
    // private val 
    override val size: Int get() = 0
    override fun add(index: Int, element: Int): Unit {
        
    }
    override fun get(index: Int): Int {
        return 0
    }
    override fun removeAt(index: Int): Int {
        return 0
    }
    override fun set(index: Int, element: Int): Int {
        return 0
    }
}
// import java.util.PriorityQueue
class IntWightedSimpleGraph<V : Comparable<V>> : AbstractList<MutableMap<Int, V>> {
    override val size: Int
    private val graph: List<MutableMap<Int, V>>
    constructor(size: Int) {
        this.size = size
        graph = List(size) { mutableMapOf<Int, V>() }
    }
    constructor(graph: IntWightedSimpleGraph<V>) {
        this.size = graph.size
        this.graph = graph.map { it.toMutableMap() }
    }
    constructor(size: Int, edges: Collection<Triple<Int, Int, V>>) {
        this.size = size
        graph = List(size) { mutableMapOf<Int, V>() }
        for ((from, to, weight) in edges) {
            graph[from][to] = weight
        }
    }
    override fun get(index: Int): MutableMap<Int, V> {
        return graph.get(index)
    }
    val vertices: IntRange get() = graph.indices
    fun containsEdge(from: Int, to: Int): Boolean {
        fromRangeCheck(from)
        toRangeCheck(to)
        return graph[from].containsKey(to)
    }
    fun getEdge(from: Int, to: Int): V {
        fromRangeCheck(from)
        toRangeCheck(to)
        return graph[from].getValue(to)
    }
    fun putEdge(from: Int, to: Int, weight: V): V? {
        fromRangeCheck(from)
        toRangeCheck(to)
        return graph[from][to].also { graph[from][to] = weight }
    }
    fun removeEdge(from: Int, to: Int): V? {
        fromRangeCheck(from)
        toRangeCheck(to)
        return graph[from].remove(to)
    }
    fun toGraph(): IntWightedSimpleGraph<V> {
        return IntWightedSimpleGraph(this)
    }
    fun IntWightedSimpleGraph<Long>.dijkstra(source: Int): Pair<List<Long?>, List<Int?>> {
        sourceRangeCheck(source)
        val distances = MutableList<Long?>(size) { null }
        val previouses = MutableList<Int?>(size) { null }
        val queue = PriorityQueue<Pair<Long, Int>>()
        for ((to, length) in get(source)) {
            distances[to] = length
            previouses[to] = source
            queue.add(Pair(length, to))
        }
        val fixed = BooleanArray(size)
        fixed[source] = true
        while (queue.isNotEmpty()) {
            val (distanceToFrom, from) = queue.poll()
            if (fixed[from]) continue
            fixed[from] = true
            for ((to, length) in get(from)) {
                if (fixed[to]) continue
                val alternative = distanceToFrom + length
                val distanceToTo = distances[to]
                if (distanceToTo == null || alternative < distanceToTo) {
                    distances[to] = alternative
                    previouses[to] = from
                    queue.add(Pair(alternative, to))
                }
            }
        }
        return Pair(distances, previouses)
    }
    private fun fromRangeCheck(from: Int) {
        when {
            from < 0 -> throw IndexOutOfBoundsException("from ($from) is less than zero.")
            from > size -> throw IndexOutOfBoundsException("from ($from) is greater than size ($size).")
        }
    }
    private fun toRangeCheck(to: Int) {
        when {
            to < 0 -> throw IndexOutOfBoundsException("to ($to) is less than zero.")
            to > size -> throw IndexOutOfBoundsException("to ($to) is greater than size ($size).")
        }
    }
    private fun sourceRangeCheck(source: Int) {
        when {
            source < 0 -> throw IndexOutOfBoundsException("source ($source) is less than zero.")
            source > size -> throw IndexOutOfBoundsException("source ($source) is greater than size ($size).")
        }
    }
}
