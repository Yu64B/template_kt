import kotlin.random.Random
val DEBUG = true

fun main() {
    val list = listOf(29, 18, 16, 19, 13, 12, 17, 27)
    val seg = list.toSegmentTree(0) { a, b -> Math.max(a, b) }
    println("a ${seg.query(1, 7)}")
    println("b ${seg.query(1, 5)}")
    println("c ${seg.query(2, 7)}")
}

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
// 未完成
fun <T> Iterable<T>.combinations(r: Int = count()): List<List<T>> {
    val result = ArrayList<List<T>>()
    val n = count()
    if (r > n || r <= 0) return result
    val indices = IntArray(r) { it }
    result.add(indices.map { elementAt(it) })
    while (true) {
        val i = (0 until r).findLast { indices[it] != it + n - r }
        if (i == null) return result
        indices[i]++
        for (j in i + 1 until r) indices[j] = indices[j - 1] + 1
        result.add(indices.map { elementAt(it) })
    }
}
// 未完成
fun CharSequence.combinations(r: Int = count()): List<List<Char>> {
    val result = ArrayList<List<Char>>()
    val n = count()
    if (r > n || r <= 0) return result
    val indices = IntArray(r) { it }
    result.add(indices.map { elementAt(it) })
    while (true) {
        val i = (0 until r).findLast { indices[it] != it + n - r }
        if (i == null) return result
        indices[i]++
        for (j in i + 1 until r) indices[j] = indices[j - 1] + 1
        result.add(indices.map { elementAt(it) })
    }
}
// 未完成
fun <T> Iterable<T>.permutations(r: Int = count()): List<List<T>> {
    val result = ArrayList<List<T>>()
    val n = count()
    if (r > n || r <= 0) return result
    val indices = IntArray(n) { it }
    val cycles = IntArray(r) { n - it }
    result.add(indices.take(r).map { elementAt(it) })
    while (true) {
        for (i in r - 1 downTo 0) {
            cycles[i]--
            if (cycles[i] == 0) {
                if (cycles.first() == 0) return result
                indices[n - 1] = indices[i].also { for (j in i until n - 1) indices[j] = indices[j + 1] }
                cycles[i] = n - i
            }
            else {
                val j = cycles[i]
                indices[n - j] = indices[i].also { indices[i] = indices[n - j] }
                result.add(indices.take(r).map { elementAt(it) })
                break
            }
        }
    }
}
// 未完成
fun CharSequence.permutations(r: Int = count()): List<List<Char>> {
    val result = ArrayList<List<Char>>()
    val n = count()
    if (r > n || r <= 0) return result
    val indices = IntArray(n) { it }
    val cycles = IntArray(r) { n - it }
    result.add(indices.take(r).map { elementAt(it) })
    while (true) {
        for (i in r - 1 downTo 0) {
            cycles[i]--
            if (cycles[i] == 0) {
                if (cycles.first() == 0) return result
                indices[n - 1] = indices[i].also { for (j in i until n - 1) indices[j] = indices[j + 1] }
                cycles[i] = n - i
            }
            else {
                val j = cycles[i]
                indices[n - j] = indices[i].also { indices[i] = indices[n - j] }
                result.add(indices.take(r).map { elementAt(it) })
                break
            }
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
class SegmentTree<T> constructor(override val size: Int, val identity: T, val combine: (T, T) -> T) : AbstractList<T>() {
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
    override operator fun get(index: Int): T {
        checkElementIndex(index, size)
        return node[nodeSize + index]
        // return node[size + index]
    }
    fun update(index: Int, value: T): T {
        checkElementIndex(index, size)
        var count = nodeSize + index
        // var count = size + index
        node[count] = value
        while (count > 1) {
            count = count.shr(1)
            node[count] = combine(node[count.shl(1).or(0)], node[count.shl(1).or(1)])
        }
        return value
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
fun <T> Iterable<T>.toSegmentTree(identity: T, combine: (T, T) -> T): SegmentTree<T> {
    return SegmentTree(this, identity, combine)
}
class XorShiftRandom : Random() {
    private var x = 2463534242U
    override fun nextBits(bitCount: Int): Int {
        checkBitCount(bitCount)
        x = x xor x.shl(13)
        x = x xor x.shr(17)
        x = x xor x.shl(5)
        return if (bitCount == 32) x.toInt() else (x and (1U.shl(bitCount) - 1U)).toInt()
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
    internal fun checkRangeBounds(from: Int, until: Int) = require(until > from) { boundsErrorMessage(from, until) }
    internal fun checkRangeBounds(from: Long, until: Long) = require(until > from) { boundsErrorMessage(from, until) }
    internal fun boundsErrorMessage(from: Any, until: Any) = "Random range is empty: [$from, $until)."
    internal fun checkBitCount(bitCount: Int) = require(0 <= bitCount && bitCount <= 32) { "bitCount: $bitCount, must be in range 0..32" }
}
class RandomizedSet<E>() : AbstractMutableSet<E>() {
    private val indexes = mutableMapOf<E, Int>()
    private val values = mutableListOf<E>()
    private val random = XorShiftRandom()
    override val size: Int
        get() = indexes.size
    override fun add(element: E): Boolean {
        if (indexes.contains(element)) {
            return false
        }
        values.add(element)
        indexes[element] = values.lastIndex
        return true
    }
    // override fun addAll(elements: Collection<E>): Boolean {
    //     return elements.fold(false) { result, element -> add(element) || result }
    // }
    // override fun clear() {
    //     indexes.clear()
    //     values.clear()
    // }
    // override fun contains(element: E): Boolean {
    //     return indexes.contains(element)
    // }
    // override fun containsAll(elements: Collection<E>): Boolean {
    //     return elements.all { contains(it) }
    // }
    // override fun isEmpty(): Boolean {
    //     return indexes.isEmpty()
    // }
    override fun iterator(): MutableIterator<E> {
        return indexes.keys.iterator()
    }
    // override fun remove(element: E): Boolean {
    //     val index = indexes[element]
    //     if (index == null) {
    //         return false
    //     }
    //     val lastElement = values[values.lastIndex]
    //     values[index] = lastElement
    //     indexes[lastElement] = index
    //     values.removeAt(values.lastIndex)
    //     indexes.remove(element)
    //     return true
    // }
    // override fun removeAll(elements: Collection<E>): Boolean {
    //     return elements.fold(false) { result, element -> remove(element) || result }
    // }
    // override fun retainAll(elements: Collection<E>): Boolean {
    //     val set = elements.toSet()
    //     return removeAll(indexes.keys.filter { !set.contains(it) })
    // }
    fun getRandom(): E {
        val randomIndex = random.nextInt(size)
        return values[randomIndex]
    }
}
fun <T> Iterable<T>.toRandomizedSet(): RandomizedSet<T> {
    return RandomizedSet<T>().also { for (element in this) it.add(element) }
}
