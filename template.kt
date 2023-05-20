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

    // val pb = ProcessBuilder("echo", "a").apply {
    //     redirectOutput(Redirect.PIPE)
    //     // redirectOutput(System.out)
    // }
    // val process = pb.start()
    // val os = process.getOutputStream()
    // val writer = PrintWriter(os, true)
    // writer.println("1+1")
    // writer.println("aaa")
    // writer.println("exit()")
    // val _is = process.getInputStream()
    // val isr = InputStreamReader(_is)
    // val br = BufferedReader(isr)
    // println(br.readLine())
    // process.waitFor(2, TimeUnit.SECONDS)
    // println(br.readLine())

    val N = 2000000
    val iuf = ImplementationUnionFind((0 until N).toList())
    var ans = 0L
    for (i in 0 until N) {
        if (iuf.contains(i)) ans++
    }
    println(ans)
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
interface UnionFind<E> : Set<E> {
    val roots: Set<E>
    val groupCount: Int
    fun same(x: E, y: E): Boolean
    fun unite(x: E, y: E): Boolean
    fun sizeOf(x: E): Int
    fun members(x: E): Set<E>
    fun allGroupMembers(): Map<E, Set<E>>
}
interface MutableUnionFind<E> : UnionFind<E>, MutableSet<E>
abstract class AbstractUnionFind<E>() : UnionFind<E>, AbstractSet<E>() {
    internal fun checkElementIndex(index: Int, size: Int) {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("index: $index, size: $size")
        }
    }
}
class ImplementationUnionFind<E>(elements: Collection<E>) : AbstractUnionFind<E>() {
    private val parentsOrDescendants: MutableMap<E, E> = LinkedHashMap(elements.size, 1.0f)
    private val children: MutableMap<E, E> = LinkedHashMap(elements.size, 1.0f)
    private val sizes: MutableMap<E, Int> = LinkedHashMap(elements.size, 1.0f)
    override val size: Int get() = parentsOrDescendants.size
    override val roots: Set<E> get() = sizes.keys.toSet()
    override val groupCount: Int get() = sizes.size
    init {
        for (element in elements) {
            parentsOrDescendants[element] = element
            children[element] = element
            sizes[element] = 1
        }
    }
    override fun iterator(): Iterator<E> {
        return parentsOrDescendants.keys.iterator()
    }
    override fun contains(element: E): Boolean {
        return parentsOrDescendants.containsKey(element)
    }
    fun findRoot(x: E): E {
        if (!parentsOrDescendants.containsKey(x)) throw NoSuchElementException("Element $x is missing in the set.")
        if (sizes.containsKey(x)) return x
        val root = findRoot(parentsOrDescendants.getValue(x))
        parentsOrDescendants[x] = root
        return root
    }
    override fun same(x: E, y: E): Boolean {
        if (!parentsOrDescendants.containsKey(x)) throw NoSuchElementException("Element $x is missing in the set.")
        if (!parentsOrDescendants.containsKey(y)) throw NoSuchElementException("Element $y is missing in the set.")
        return findRoot(x) == findRoot(y)
    }
    override fun unite(x: E, y: E): Boolean {
        if (!parentsOrDescendants.containsKey(x)) throw NoSuchElementException("Element $x is missing in the set.")
        if (!parentsOrDescendants.containsKey(y)) throw NoSuchElementException("Element $y is missing in the set.")
        val xRoot = findRoot(x)
        val yRoot = findRoot(y)
        if (xRoot == yRoot) return false
        val xSize = sizes.getValue(xRoot)
        val ySize = sizes.getValue(yRoot)
        val parentRoot = if (xSize < ySize) yRoot else xRoot
        val childRoot = if (xSize < ySize) xRoot else yRoot
        val parentLeaf = parentsOrDescendants.getValue(parentRoot)
        val childLeaf = parentsOrDescendants.getValue(childRoot)
        children[parentLeaf] = childRoot
        parentsOrDescendants[parentRoot] = childLeaf
        parentsOrDescendants[childRoot] = parentRoot
        sizes[parentRoot] = xSize + ySize
        sizes.remove(childRoot)
        return true
    }
    override fun sizeOf(x: E): Int {
        if (!parentsOrDescendants.containsKey(x)) throw NoSuchElementException("Element $x is missing in the set.")
        return sizes.getValue(findRoot(x))
    }
    override fun members(x: E): Set<E> {
        if (!parentsOrDescendants.containsKey(x)) throw NoSuchElementException("Element $x is missing in the set.")
        val members = mutableSetOf<E>()
        var member = findRoot(x)
        while (children[member] != member) {
            members.add(member)
            member = children.getValue(member)
        }
        members.add(member)
        return members
    }
    override fun allGroupMembers(): Map<E, Set<E>> {
        val groupMembers = mutableMapOf<E, Set<E>>()
        for (root in sizes.keys) {
            val members = mutableSetOf<E>()
            var member = root
            while (children[member] != member) {
                members.add(member)
                member = children.getValue(member)
            }
            members.add(member)
            groupMembers[root] = members
        }
        return groupMembers
    }
}
// class ImplementationMutableUnionFind<E> : MutableUnionFind<E> {
//     private val ancesters: MutableMap<E, E>
//     private val descendants: MutableMap<E, E>
//     private val sizes: MutableMap<E, Int>
//     constructor() {
//         ancesters = mutableMapOf<E, E>()
//         descendants = mutableMapOf<E, E>()
//         sizes = mutableMapOf<E, Int>()
//     }
// }
// abstract class AbstractIntUnionFind() : UnionFind<Int>, AbstractSet<Int>() {
    
// }
class IntUnionFind(override val size: Int) : AbstractUnionFind<Int>() {
    private val ancestersOrSizes = IntArray(size) { -1 }
    private val parentsOrDescendants = IntArray(size) { -it }
    private val nextRoots = IntArray(size) { if (it == size - 1) it else it + 1 }
    private var firstRoot = 0
    private var _groupCount = size
    override val roots: Set<Int> get() {
        val roots = mutableSetOf<Int>()
        var prevRoot = firstRoot
        var root = prevRoot
        roots.add(root)
        while (nextRoots[root] != root) {
            root = nextRoots[root]
            if (root >= 0) {
                nextRoots[prevRoot] = root
                prevRoot = root
                roots.add(root)
            }
            
        }
        return roots
    }
    override val groupCount: Int get() = _groupCount
    override fun iterator(): Iterator<Int> {
        return ancestersOrSizes.indices.iterator()
    }
    override fun contains(element: Int): Boolean {
        return 0 <= element && element < size
    }
    fun findRoot(x: Int): Int {
        checkElementIndex(x, size)
        if (ancestersOrSizes[x] < 0) return x
        ancestersOrSizes[x] = findRoot(ancestersOrSizes[x])
        return ancestersOrSizes[x]
    }
    override fun same(x: Int, y: Int): Boolean {
        checkElementIndex(x, size)
        checkElementIndex(y, size)
        return findRoot(x) == findRoot(y)
    }
    override fun unite(x: Int, y: Int): Boolean {
        checkElementIndex(x, size)
        checkElementIndex(y, size)
        val xRoot = findRoot(x)
        val yRoot = findRoot(y)
        if (xRoot == yRoot) return false
        val parentRoot = if (-xRoot < -yRoot) yRoot else xRoot
        val childRoot = if (-xRoot < -yRoot) xRoot else yRoot
        ancestersOrSizes[parentRoot] += ancestersOrSizes[childRoot]
        ancestersOrSizes[childRoot] = parentRoot
        val parentDescendant = -parentsOrDescendants[parentRoot]
        parentsOrDescendants[parentRoot] = parentsOrDescendants[childRoot]
        parentsOrDescendants[childRoot] = parentDescendant
        nextRoots[childRoot] = nextRoots[childRoot].inv()
        _groupCount--
        return true
    }
    override fun sizeOf(x: Int): Int {
        checkElementIndex(x, size)
        return -ancestersOrSizes[findRoot(x)]
    }
    override fun members(x: Int): Set<Int> {
        checkElementIndex(x, size)
        val root = findRoot(x)
        val members = mutableSetOf<Int>()
        for (member in 0 until size) {
            if (findRoot(member) == root) members.add(member)
        }
        return members
    }
    override fun allGroupMembers(): Map<Int, Set<Int>> {
        val groupMembers = mutableMapOf<Int, MutableSet<Int>>()
        for (member in 0 until size) {
            val root = findRoot(member)
            val members = groupMembers.getOrPut(root) { mutableSetOf<Int>() }
            members.add(member)
        }
        return groupMembers
    }
}
open class ArraySegmentTree<E> constructor(override val size: Int, private val identity: E, private val combine: (E, E) -> E) : AbstractList<E>() {
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
    open fun set(index: Int, value: E) {
        checkElementIndex(index, size)
        var count = nodeSize + index
        // var count = size + index
        node[count] = value
        while (count > 1) {
            count = count.shr(1)
            node[count] = combine(node[count.shl(1).or(0)], node[count.shl(1).or(1)])
        }
    }
    open fun query(fromIndex: Int, toIndex: Int): E {
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
    override fun subList(fromIndex: Int, toIndex: Int): ArraySegmentTree<E> = SubList(this, fromIndex, toIndex)
    private class SubList<E>(private val list: ArraySegmentTree<E>, private val fromIndex: Int, toIndex: Int) : ArraySegmentTree<E>(toIndex - fromIndex, list.identity, list.combine) {
        private var _size = 0
        init {
            checkRangeIndexes(fromIndex, toIndex, list.size)
            this._size = toIndex - fromIndex
        }
        override fun get(index: Int): E {
            checkElementIndex(index, _size)
            return list[fromIndex + index]
        }
        override fun set(index: Int, value: E) {
            checkElementIndex(index, _size)
            list.set(fromIndex + index, value)
        }
        override fun query(fromIndex: Int, toIndex: Int): E {
            checkRangeIndexes(fromIndex, toIndex, _size)
            return query(this.fromIndex + fromIndex, this.fromIndex + toIndex)
        }
        override val size: Int get() = _size
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
//     private fun checkElementIndex(index: Int, size: Int) {
//         if (index < 0 || index >= size) {
//             throw IndexOutOfBoundsException("index: $index, size: $size")
//         }
//     }
//     private fun checkRangeIndexes(fromIndex: Int, toIndex: Int, size: Int) {
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
fun <T> Iterable<T>.toArraySegmentTree(identity: T, combine: (T, T) -> T): ArraySegmentTree<T> {
    return ArraySegmentTree(this, identity, combine)
}
// import java.awt.Point
operator fun Point.component1(): Int = x
operator fun Point.component2(): Int = y
// import kotlin.random.Random
class XorShiftRandom : Random {
    private var x = 2463534242U
    constructor()
    constructor(seed: Int) {
        x = seed.toUInt()
        require(x != 0U) { "seed must be non-zero." }
    }
    override fun nextBits(bitCount: Int): Int =
        nextInt().takeUpperBits(bitCount)
    override fun nextInt(): Int {
        x = x xor x.shl(13)
        x = x xor x.shr(17)
        x = x xor x.shl(5)
        return x.toInt()
    }
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
    private fun Int.takeUpperBits(bitCount: Int): Int =
        this.ushr(32 - bitCount) and (-bitCount).shr(31)
    private fun checkRangeBounds(from: Int, until: Int) = require(until > from) { boundsErrorMessage(from, until) }
    private fun checkRangeBounds(from: Long, until: Long) = require(until > from) { boundsErrorMessage(from, until) }
    private fun boundsErrorMessage(from: Any, until: Any) = "Random range is empty: [$from, $until)."
}
class RandomizedSet<E>(private val random: Random = XorShiftRandom()) : AbstractMutableSet<E>() {
    private val indexes = mutableMapOf<E, Int>()
    private val values = mutableListOf<E>()
    override val size: Int get() = indexes.size
    override fun add(element: E): Boolean {
        if (indexes.containsKey(element)) {
            return false
        }
        values.add(element)
        indexes[element] = values.lastIndex
        return true
    }
    override fun contains(element: E): Boolean {
        return indexes.containsKey(element)
    }
    override fun iterator(): MutableIterator<E> {
        val iterator = indexes.iterator()
        var nextValue: E? = null
        return object : MutableIterator<E> {
            override fun hasNext(): Boolean {
                return iterator.hasNext()
            }
            override fun next(): E {
                return iterator.next().key.also { nextValue = it }
            }
            // 動作未確認
            override fun remove() {
                if (nextValue == null) {
                    throw IllegalStateException()
                }
                val index = indexes[nextValue]
                removeFromList(index!!)
                iterator.remove()
                nextValue = null
            }
        }
    }
    override fun remove(element: E): Boolean {
        val index = indexes[element]
        if (index == null) {
            return false
        }
        removeFromList(index)
        indexes.remove(element)
        return true
    }
    private fun removeFromList(index: Int) {
        val lastElement = values.last()
        values[index] = lastElement
        indexes[lastElement] = index
        // values.removeLast()
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
// class MutableMultiSet<T> {
//     private class Node<T>(var value: T) {
//         var left: Node<T>? = null
//         var right: Node<T>? = null
//         var height = 0
//         var size = 0
//         fun update(): Node<T> {
//             height = Math.max(left!!.height, right!!.height) + 1
//             size = left!!.size + right!!.size + 1
//             return this
//         }
//         fun rotateLeft(): Node<T> {
//             val newRoot = right!!
//             right = newRoot.left
//             newRoot.left = this
//             update()
//             return newRoot.update()
//         }
//         fun rotateRight(): Node<T> {
//             val newRoot = left!!
//             left = newRoot.right
//             newRoot.right = this
//             update()
//             return newRoot.update()
//         }
//         fun calcHeightDiff() = left!!.height - right!!.height
//         fun balance(): Node<T> {
//             val heightDiff = calcHeightDiff()
//             if (heightDiff == 2) {
//                 if (left!!.calcHeightDiff() < 0) left = left!!.rotateLeft()
//                 return rotateRight()
//             }
//             else if (heightDiff == -2) {
//                 if (right!!.calcHeightDiff() > 0) right = right!!.rotateRight()
//                 return rotateLeft()
//             }
//             else return update()
//         }
//     }
// }
// class MutableUnionFind<E> : AbstractMutableSet<E>() {
//     val map = mutableMapOf<E, MutableSet<E>>()
//     val groups = mutableMapOf<E, MutableSet<E>>()
//     override val size: Int
//         get() = map.size
//     override fun add(element: E): Boolean {
//         if (map.containsKey(element)) {
//             return false
//         }
//         map[element] = HashSet<E>(2).apply { add(element) }
//         groups[element] = HashSet<E>(2).apply { add(element) }
//         return true
//     }
//     override fun contains(element: E): Boolean {
//         return map.containsKey(element)
//     }
//     override fun iterator(): MutableIterator<E> {
//         return object : MutableIterator<E> {
//             val iterator = map.entries.iterator()
//             var nextEntry: MutableMap.MutableEntry<E, MutableSet<E>>? = null
//             override fun hasNext(): Boolean {
//                 return iterator.hasNext()
//             }
//             override fun next(): E {
//                 return iterator.next().also { nextEntry = it }.key
//             }
//             override fun remove() {
//                 val element = nextEntry!!.key
//                 val set = nextEntry!!.value
//                 set.remove(element)
//                 if (groups.remove(element) != null && set.isNotEmpty()) {
//                     groups[set.first()] = set
//                 }
//                 iterator.remove()
//                 nextEntry = null
//             }
//         }
//     }
//     override fun remove(element: E): Boolean {
//         val set = map[element]
//         if (set == null) {
//             return false
//         }
//         set.remove(element)
//         if (groups.remove(element) != null && set.isNotEmpty()) {
//             groups[set.first()] = set
//         }
//         map.remove(element)
//         return true
//     }
//     fun same(x: E, y: E): Boolean {
//         val xSet = map[x] ?: throw NoSuchElementException("Collection contains no element matching the x.")
//         val ySet = map[y] ?: throw NoSuchElementException("Collection contains no element matching the y.")
//         return xSet === ySet
//     }
//     fun unite(x: E, y: E): Boolean {
//         val xSet = map[x] ?: throw NoSuchElementException("Collection contains no element matching the x.")
//         val ySet = map[y] ?: throw NoSuchElementException("Collection contains no element matching the y.")
//         if (xSet === ySet) {
//             return false
//         }
//         val smallSet = if (xSet.size > ySet.size) ySet else xSet
//         val largeSet = if (xSet.size > ySet.size) xSet else ySet
//         val iterator = smallSet.iterator()
//         while (iterator.hasNext()) {
//             val element = iterator.next()
//             largeSet.add(element)
//             map[element] = largeSet
//             groups.remove(element)
//             iterator.remove()
//         }
//         return true
//     }
// }
// fun <T> Iterable<T>.toMutableUnionFind(): MutableUnionFind<T> {
//     return MutableUnionFind<T>().also { for (item in this) it.add(item) }
// }
// 未完成
fun binarySearch(from: Int = Int.MIN_VALUE, to: Int = Int.MAX_VALUE, predicate: (Int) -> Boolean): Int? {
    require(from <= to) { "from ($from) is greater than to ($to)." }
    val fromResult = predicate(from)
    val toResult = predicate(to)
    if (fromResult && toResult) {
        return from
    }
    if (!fromResult && !toResult) {
        return null
    }
    var ok = if (fromResult) from else to
    var ng = if (fromResult) to else from
    while (Math.abs(ng - ok) > 1) {
        val mid = if (ok xor ng < 0) (ok + ng).shr(1) else ok + (ng - ok).shr(1)
        if (predicate(mid)) {
            ok = mid
        }
        else {
            ng = mid
        }
    }
    return ok
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
class ArrayIntWightedSimpleGraph<V : Comparable<V>> : AbstractList<MutableMap<Int, V>> {
    override val size: Int
    private val graph: List<MutableMap<Int, V>>
    val vertices: IntRange get() = graph.indices
    constructor(size: Int) {
        this.size = size
        graph = List(size) { mutableMapOf<Int, V>() }
    }
    constructor(graph: Collection<Map<Int, V>>) {
        this.size = graph.size
        this.graph = graph.map { it.toMutableMap() }
    }
    constructor(size: Int, edges: Collection<Triple<Int, Int, V>>) {
        this.size = size
        graph = List(size) { mutableMapOf<Int, V>() }
        for ((from, to, weight) in edges) {
            fromRangeCheck(from)
            toRangeCheck(to)
            graph[from][to] = weight
        }
    }
    override fun get(index: Int): MutableMap<Int, V> {
        return graph.get(index)
    }
    fun containsEdge(from: Int, to: Int): Boolean {
        fromRangeCheck(from)
        toRangeCheck(to)
        return graph[from].containsKey(to)
    }
    fun containsVertex(index: Int): Boolean {
        return vertices.contains(index)
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
    fun toGraph(): ArrayIntWightedSimpleGraph<V> {
        return ArrayIntWightedSimpleGraph(this)
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
    internal fun sourceRangeCheck(source: Int) {
        when {
            source < 0 -> throw IndexOutOfBoundsException("source ($source) is less than zero.")
            source > size -> throw IndexOutOfBoundsException("source ($source) is greater than size ($size).")
        }
    }
}
fun ArrayIntWightedSimpleGraph<Long>.dijkstra(source: Int): Pair<List<Long?>, List<Int?>> {
    sourceRangeCheck(source)
    val distances = MutableList<Long?>(size) { null }
    val previouses = MutableList<Int?>(size) { null }
    val queue = PriorityQueue<Pair<Long, Int>>() { a, b -> 
        if (a.first != b.first) compareValues(a.first, b.first) else compareValues(a.second, b.second)
    }
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
