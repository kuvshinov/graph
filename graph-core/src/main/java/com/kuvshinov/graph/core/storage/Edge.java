package com.kuvshinov.graph.core.storage;

import com.kuvshinov.graph.core.graph.node.Node;

/**
 * @author Sergey Kuvshinov
 */
public class Edge<E> {

    private Node first;

    private Node second;

    private E relationType;

    public Edge() {
        this(null, null, null);
    }

    public Edge(Node first, Node second) {
        this(first, second, null);
    }

    public Edge(Node first, Node second, E relationType) {
        this.first = first;
        this.second = second;
        this.relationType = relationType;
    }



}
