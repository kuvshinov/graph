package com.kuvshinov.graph.core;

/**
 * @author Sergey Kuvshinov
 */
public class Edge<T, E> {

    private Node<T> first;

    private Node<T> second;

    private E relationType;

    public Edge() {
        this(null, null, null);
    }

    public Edge(Node<T> first, Node<T> second) {
        this(first, second, null);
    }

    public Edge(Node<T> first, Node<T> second, E relationType) {
        this.first = first;
        this.second = second;
        this.relationType = relationType;
    }



}
