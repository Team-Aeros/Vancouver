package com.aeros.controllers;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> implements Runnable {

    private ArrayList<T> _queueItems;

    private boolean _inWorkingQueue;

    private static final int QUEUE_SIZE = 5;

    public Queue() {
        _queueItems = new ArrayList<>();
        _inWorkingQueue = false;
    }

    public synchronized void addItem(T item) {
        System.out.println("Method addItem() invoked");

        if (_queueItems.size() >= QUEUE_SIZE) {
            prepareToEnterWorkingQueue();
            List<T> items = _queueItems.subList(0, QUEUE_SIZE - 1);
            _queueItems = new ArrayList<>();

            new Thread(new Runnable() {
                public synchronized void run() {
                    ArrayList<T> workingQueue = new ArrayList<>(items);

                    for (T queueItem : workingQueue) {
                        System.out.println("I'm doing something");
                    }

                    setInWorkingQueue(false);
                }
            }).start();
        }

        _queueItems.add(item);
    }

    private synchronized void setInWorkingQueue(boolean value) {
        _inWorkingQueue = value;
    }

    private synchronized void prepareToEnterWorkingQueue() {
        while (_inWorkingQueue);
        setInWorkingQueue(true);
    }

    public void run() {
        // This is a really cool comment
    }
}
