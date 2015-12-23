package com.kuvshinov.graph.core;

/**
 * @author Sergey Kuvshinov
 */
public class Node<T> {

    private T obj;

    public Node() {
        this(null);
    }

    public Node(T obj) {
        this.obj = obj;
    }

    public T getObject() {
        return this.obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
