package com.lgiacchetta.dungeon.circularlinkedlist;

public class CircularListNode<T> {
    private final T data;
    private CircularListNode<T> next;
    private CircularListNode<T> prev;

    public CircularListNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public CircularListNode<T> getNext() {
        return next;
    }

    public void setNext(CircularListNode<T> next) {
        this.next = next;
    }

    public CircularListNode<T> getPrev() {
        return prev;
    }

    public void setPrev(CircularListNode<T> prev) {
        this.prev = prev;
    }
}
