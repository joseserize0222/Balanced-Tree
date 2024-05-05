package org.jub.kotlin.hometask3

internal data class AvlNode<K : Comparable<K>, V>(override var key: K, override var value: V) :
    MutableMap.MutableEntry<K, V> {
    var left: AvlNode<K, V>? = null
        set(child) {
            field = child
            child?.parent = this
        }
    var right: AvlNode<K, V>? = null
        set(child) {
            field = child
            child?.parent = this
        }
    var parent: AvlNode<K, V>? = null
    var height: Int = 1

    override fun setValue(newValue: V): V = value.also { value = newValue }

    fun getNext(): AvlNode<K, V>? = this.right?.minNode() ?: run {
        var temp: AvlNode<K, V> = this
        while (temp.parent != null) {
            if ((temp.parent?.key ?: error("concurrent modification")) > temp.key)
                break
            temp = temp.parent ?: error("concurrent modification")
        }
        temp.parent
    }

    fun minNode(): AvlNode<K, V> {
        var current: AvlNode<K, V> = this
        while (true)
            current = current.left ?: break
        return current
    }

    fun maxNode(): AvlNode<K, V> {
        var current: AvlNode<K, V> = this
        while (true)
            current = current.right ?: break
        return current
    }

    override fun toString(): String = "$key=$value"
}

internal abstract class AvlTreeBase<K : Comparable<K>, V> {
    protected abstract var root: AvlNode<K, V>?
    protected abstract var sizeTree: Int
    protected fun getHeight(node: AvlNode<K, V>?): Int = node?.height ?: 0

    private fun updateHeight(node: AvlNode<K, V>) {
        node.height = 1 + maxOf(getHeight(node.left), getHeight(node.right))
    }

    private fun safeCall(node: AvlNode<K, V>?): AvlNode<K, V> = node ?: error("illegal state of the tree")

    private fun balanceFactor(node: AvlNode<K, V>?): Int = getHeight(node?.left) - getHeight(node?.right)

    private fun leftRotate(root: AvlNode<K, V>): AvlNode<K, V> {
        val newRoot = safeCall(root.right)
        val subtree = newRoot.left
        newRoot.parent = root.parent
        newRoot.left = root
        root.right = subtree
        updateHeight(root)
        updateHeight(newRoot)
        return newRoot
    }

    private fun rightRotate(root: AvlNode<K, V>): AvlNode<K, V> {
        val newRoot = safeCall(root.left)
        val subtree = newRoot.right
        newRoot.parent = root.parent
        newRoot.right = root
        root.left = subtree
        updateHeight(root)
        updateHeight(newRoot)
        return newRoot
    }

    private fun balance(node: AvlNode<K, V>): AvlNode<K, V> {
        updateHeight(node)
        val balance = balanceFactor(node)

        return when {
            balance > 1 -> {
                if (balanceFactor(node.left) >= 0) {
                    rightRotate(node)
                } else {
                    node.left = leftRotate(safeCall(node.left))
                    rightRotate(node)
                }
            }

            balance < -1 -> {
                if (balanceFactor(node.right) <= 0) {
                    leftRotate(node)
                } else {
                    node.right = rightRotate(safeCall(node.right))
                    leftRotate(node)
                }
            }
            else -> node
        }
    }

    protected fun innerDelete(root: AvlNode<K, V>?, key: K): AvlNode<K, V>? = root?.let {
        var newRoot: AvlNode<K, V>? = root
        when {
            key < root.key -> newRoot?.left = innerDelete(root.left, key)
            key > root.key -> newRoot?.right = innerDelete(root.right, key)
            else -> {
                if (root.left == null || root.right == null) {
                    val temp: AvlNode<K, V>? = root.left ?: root.right
                    temp?.parent = root.parent
                    newRoot = temp
                } else {
                    val temp = safeCall(root.right).minNode()
                    root.key = temp.key
                    root.value = temp.value
                    root.right = innerDelete(root.right, root.key)
                    newRoot = root
                }
            }
        }
        newRoot?.let { balance(newRoot) }
    }

    protected fun innerInsert(
        root: AvlNode<K, V>?,
        key: K,
        value: V,
    ): AvlNode<K, V> = root?.let {
        when {
            key < root.key -> root.left = innerInsert(root.left, key, value)
            key > root.key -> root.right = innerInsert(root.right, key, value)
            else -> {
                root.value = value
            }
        }
        balance(root)
    } ?: AvlNode(key, value).also { sizeTree++ }

    protected fun innerGetKey(root: AvlNode<K, V>? = this.root, key: K): AvlNode<K, V>? = root?.let {
        when {
            key < root.key -> innerGetKey(root.left, key)
            key > root.key -> innerGetKey(root.right, key)
            else -> root
        }
    }
}
