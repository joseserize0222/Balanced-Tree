package org.jub.kotlin.hometask3

internal class AvlTreeList<K : Comparable<K>, V> : MutableAvlTreeMap<K, V>(), BalancedSearchTreeList<K, V> {
    private val innerList: List<V> by lazy { values.toList() }

    override fun iterator(): Iterator<V> = innerList.iterator()

    override fun listIterator(): ListIterator<V> = innerList.listIterator()

    override fun get(index: Int): V = innerList[index]

    override fun contains(element: V): Boolean = innerList.contains(element)

    override fun containsAll(elements: Collection<V>): Boolean = elements.all { value -> contains(value) }

    override fun indexOf(element: V): Int = innerList.indexOf(element)

    override fun lastIndexOf(element: V): Int = innerList.lastIndexOf(element)

    override fun listIterator(index: Int): ListIterator<V> = innerList.listIterator()

    override fun subList(fromIndex: Int, toIndex: Int): List<V> = innerList.subList(fromIndex, toIndex)
}

