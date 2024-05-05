package org.jub.kotlin.hometask3

internal open class AvlTree<K : Comparable<K>, V> : AvlTreeBase<K, V>(), BalancedSearchTree<K, V> {
    override var root: AvlNode<K, V>? = null
    override var sizeTree: Int = 0
    override val height: Int
        get() = getHeight(this.root)

    fun insert(key: K, value: V) {
        root = super.innerInsert(this.root, key, value)
    }

    fun delete(key: K): Boolean {
        getNode(key) ?: return false
        root = super.innerDelete(this.root, key)
        sizeTree--
        return true
    }

    protected fun getNode(key: K): AvlNode<K, V>? = super.innerGetKey(this.root, key)

    protected fun deleteAll() {
        root = null
        sizeTree = 0
    }

    protected open fun isEmpty(): Boolean = this.root == null
    private fun maximumNode(): AvlNode<K, V> = root?.maxNode() ?: error("Empty tree")

    internal fun minimumNode(): AvlNode<K, V> = root?.minNode() ?: error("Empty tree")

    override fun maximumKey(): K = maximumNode().key

    override fun minimumKey(): K = minimumNode().key

    override fun maximumValue(): V = maximumNode().value

    override fun minimumValue(): V = minimumNode().value
}
