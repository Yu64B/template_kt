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
    var previousElement = this[0]
    var count = 1
    for (i in 1..lastIndex) {
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
        for (i in (0 until r).reversed()) {
            cycles[i]--
            if (cycles[i] == 0) {
                if (cycles[0] == 0) return result
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
        for (i in (0 until r).reversed()) {
            cycles[i]--
            if (cycles[i] == 0) {
                if (cycles[0] == 0) return result
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
class SegmentTree<T> constructor(val size: Int, val identity: T, val combine: (T, T) -> T) {
    val node: MutableList<T> // How to use Array<T>?
    init {
        var nodeSize = 1
        while (nodeSize < size) nodeSize = nodeSize.shl(1)
        node = MutableList<T>(nodeSize.shl(1)) { identity }
    }
    constructor(elements: Iterable<T>, identity: T, combine: (T, T) -> T) : this(elements.count(), identity, combine) {
        for ((index, element) in elements.withIndex()) node[index + size] = element
        for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
    }
    constructor(size: Int, init: (Int) -> T, identity: T, combine: (T, T) -> T) : this(size, identity, combine) {
        repeat(size) { index -> node[index + size] = init(index) }
        for (index in size - 1 downTo 1) node[index] = combine(node[index.shl(1).or(0)], node[index.shl(1).or(1)]) 
    }
    operator fun get(index: Int): T {
        checkElementIndex(index)
        return node[index + size]
    }
    fun updata(index: Int, value: T): T {
        checkElementIndex(index)
        var count = index + size
        node[count] = value
        while (count > 1) {
            count = count.shr(1)
            node[count] = combine(node[count.shl(1).or(0)], node[count.shl(1).or(1)])
        }
        return value
    }
    fun query(fromIndex: Int, toIndex: Int): T {
        checkRangeIndexes(fromIndex, toIndex)
        var left = fromIndex + size
        var right = toIndex + size
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
    private fun checkElementIndex(index: Int) {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("index: $index, size: $size")
        }
    }
    private fun checkRangeIndexes(fromIndex: Int, toIndex: Int) {
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
