package org.jub.kotlin.hometask3

internal open class MutableAvlTreeMap<K : Comparable<K>, V> : AvlTree<K, V>(), MutableBalancedSearchTreeMap<K, V> {
    override val size get() = sizeTree
    var modCount: Int = 0
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> =
        object : AbstractMutableSet<MutableMap.MutableEntry<K, V>>() {
            override val size: Int get() = this@MutableAvlTreeMap.size

            override fun add(element: MutableMap.MutableEntry<K, V>): Boolean = throw UnsupportedOperationException()

            override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> =
                MutableAvlTreeIterator(this@MutableAvlTreeMap)

            override fun remove(element: MutableMap.MutableEntry<K, V>): Boolean =
                this@MutableAvlTreeMap.remove(element.key, element.value)

            override fun contains(element: MutableMap.MutableEntry<K, V>): Boolean =
                this@MutableAvlTreeMap.containsEntry(element)
        }
    override val keys: MutableSet<K> = object : AbstractMutableSet<K>() {
        override val size: Int get() = this@MutableAvlTreeMap.size

        override fun add(element: K): Boolean = throw UnsupportedOperationException()

        override fun iterator(): MutableIterator<K> = KeyIterator(MutableAvlTreeIterator(this@MutableAvlTreeMap))

        override fun remove(element: K): Boolean = this@MutableAvlTreeMap.remove(element) != null

        override fun contains(element: K): Boolean = this@MutableAvlTreeMap.containsKey(element)
        private inner class KeyIterator<K>(private val iterator: MutableIterator<MutableMap.MutableEntry<K, *>>) :
            MutableIterator<K> {
            override fun next(): K = iterator.next().key
            override fun hasNext(): Boolean = iterator.hasNext()
            override fun remove() = iterator.remove()
        }
    }
    override val values: MutableCollection<V> = object : AbstractMutableCollection<V>() {
        override val size: Int get() = this@MutableAvlTreeMap.size

        override fun iterator(): MutableIterator<V> = ValueIterator(MutableAvlTreeIterator(this@MutableAvlTreeMap))

        override fun add(element: V): Boolean = throw UnsupportedOperationException()

        override fun remove(element: V): Boolean {
            for ((key, value) in entries) {
                if (value == element) {
                    this@MutableAvlTreeMap.remove(key)
                    return true
                }
            }
            return false
        }

        override fun contains(element: V): Boolean = this@MutableAvlTreeMap.containsValue(element)

        private inner class ValueIterator<V>(val iterator: MutableIterator<MutableMap.MutableEntry<*, V>>) :
            MutableIterator<V> {
            override fun next(): V = iterator.next().value

            override fun hasNext(): Boolean = iterator.hasNext()

            override fun remove() = iterator.remove()
        }
    }

    override fun put(key: K, value: V): V? {
        modCount++
        val res = get(key)
        insert(key, value)
        return res
    }

    override fun putAll(from: Map<out K, V>) {
        for ((key, value) in from) {
            put(key, value)
        }
    }

    override fun get(key: K): V? = getNode(key)?.value

    override fun remove(key: K): V? {
        val res = get(key)
        modCount++
        return res?.also { delete(key) }
    }

    override fun remove(key: K, value: V): Boolean {
        val res = get(key)
        modCount++
        if (res != null && res == value) {
            remove(key)
            return true
        }
        return false
    }

    override fun clear() {
        deleteAll()
        modCount = 0
    }

    override fun containsValue(value: V): Boolean {
        for (v in values) {
            if (v == value) {
                return true
            }
        }
        return false
    }

    override fun containsKey(key: K): Boolean = getNode(key) != null

    fun containsEntry(element: MutableMap.MutableEntry<K, V>): Boolean {
        val res = getNode(element.key)
        return res != null && res.value == element.value
    }

    fun contains(key: K): Boolean = getNode(key) != null

    override fun isEmpty(): Boolean = super.isEmpty()

    override fun merge(other: MutableBalancedSearchTreeMap<out K, out V>): MutableBalancedSearchTreeMap<K, V> {
        require(other.keys.max() > this.keys.max()) {
            Error("Invalid state, the size of the passed value is smaller")
        }
        putAll(other)
        return this
    }

    override fun toString(): String = entries.joinToString(prefix = "{", postfix = "}")
}
