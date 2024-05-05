package org.jub.kotlin.hometask3

internal class MutableAvlTreeIterator<K : Comparable<K>, V>(private val data: MutableAvlTreeMap<K, V>) :
    MutableIterator<MutableMap.MutableEntry<K, V>> {
    private var cursor: AvlNode<K, V>? = if (data.isNotEmpty()) data.minimumNode() else null
    private var lastRet: AvlNode<K, V>? = null
    private var expectedModCount: Int = data.modCount

    override fun hasNext(): Boolean = cursor != null

    override fun next(): MutableMap.MutableEntry<K, V> {
        checkForCommodification()
        if (hasNext()) {
            val result: AvlNode<K, V> = cursor ?: throw ConcurrentModificationException()
            lastRet = cursor
            cursor = cursor?.getNext()
            return result
        } else {
            checkForCommodification()
            error("Out of bounds")
        }
    }

    override fun remove() {
        checkForCommodification()
        lastRet?.also {
            data.remove(lastRet?.key!!) ?: throw ConcurrentModificationException()
            expectedModCount = data.modCount
            lastRet = null
        } ?: error("Illegal operation")
    }

    private fun checkForCommodification() {
        if (expectedModCount != data.modCount) {
            throw ConcurrentModificationException()
        }
    }
}
