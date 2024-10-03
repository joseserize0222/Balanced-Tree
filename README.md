[![Build](https://github.com/cscenter/kotlin-avl/actions/workflows/HW3.yml/badge.svg)](https://github.com/cscenter/kotlin-avl/actions/workflows/HW3.yml)

# Project: Balanced Search Tree (BST)

This project is part of my learning and familiarization with object-oriented programming (OOP) in Kotlin, where I implemented a generic balanced search tree (BST), allowing the choice between three types of self-balancing trees: AVL tree, Cartesian tree, and Splay tree.

## Features

- **Interface Implementation**: The tree implements the [`Map`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/), [`MutableMap`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/), and [`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/) interfaces, providing an efficient and flexible data structure. Additionally, I optionally implemented the [`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/) interface, writing my own tests to verify its behavior.
  
- **Automatic Balancing**: The underlying structure of the tree maintains proper balance, ensuring an average or amortized time complexity of $O(\log(n))$ for node insertion and deletion operations.

- **Two Generic Parameters**: The tree accepts comparable keys and generic values. Specifically, the `maximumValue` and `minimumValue` functions return the value associated with the maximum and minimum keys, respectively, but not necessarily the actual maximum or minimum value.

- **Separate `Node` Class**: The structure includes a separate `Node` class, which facilitates tree operations management and improves code modularity.

- **Iterator Support**: Iterators are automatically invalidated when the underlying tree is modified, adhering to the expected behavior of mutable collections in Kotlin. This ensures that operations such as removing elements during iteration are handled correctly.

## Extended Functionality

The implementation of the balanced tree serves as the foundation for fulfilling the functionalities required by the `Map`, `MutableMap`, and `List` interfaces. Most of the code for `Map` and `MutableMap` is shared to maximize efficiency and reduce redundancy.

Additionally, when implementing the `entries`, `keys`, and `values` properties in `MutableMap`, I ensured that these reflect changes directly in the tree structure, without storing a separate copy of the data.

## Iterators and Concurrent Modifications

I implemented mechanisms to ensure that iterators are invalidated if the tree structure changes during their use, preventing exceptions such as `ConcurrentModificationException`. This functionality is aligned with the expected behavior in Kotlin's mutable collections.

## Project Goal

The main goal of this project was to deepen my understanding of the fundamental principles of object-oriented programming in Kotlin, as well as to explore the efficient implementation of self-balancing data structures. I wrote tests to verify the correct behavior of the tree in various scenarios and ensured that the code followed best practices for visibility and modularity, using modifiers like `sealed` and `internal` where necessary.

## Requirements and Testing

The project was designed to follow Kotlin's best practices, including the use of tools like `detekt` and `diktat` to ensure code quality. These analyses can be run with the following commands:

- To run `detekt`: `gradlew customDetekt`
- To run `diktat`: `gradlew diktatCheck`

The generated reports can be found in the `build/reports` directory.

