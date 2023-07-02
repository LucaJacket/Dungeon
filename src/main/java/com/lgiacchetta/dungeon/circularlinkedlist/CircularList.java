package com.lgiacchetta.dungeon.circularlinkedlist;

import java.util.Arrays;

public class CircularList<T> {
    private CircularListNode<T> head;
    private CircularListNode<T> tail;

    @SafeVarargs
    public CircularList(T...elements) {
        Arrays.stream(elements).forEach(this::addNode);
    }
    
    public void addNode(T data) {
        CircularListNode<T> newNode = new CircularListNode<>(data);
        if (head == null) {
            head = newNode;
        }
        else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
        }
        tail = newNode;
        tail.setNext(head);
        head.setPrev(tail);
    }

    public CircularListNode<T> findNode(T data) {
        CircularListNode<T> current = head;
        if (head != null) {
            do {
                if (current.getData().equals(data))
                    return current;
                current = current.getNext();
            } while (current != head);
        }
        // otherwise add node and return tail
        addNode(data);
        return tail;
    }
}
