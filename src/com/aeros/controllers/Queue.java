/*
 * Vancouver
 *
 * @version     2.0 Alpha 1
 * @author      Aeros Development
 * @copyright   2017, Vancouver
 *
 * @license     Apache 2.0
 */

package com.aeros.controllers;

import java.util.ArrayList;

public class Queue<T> {
    private ArrayList<String> _queueItems;

    public Queue() {
        _queueItems = new ArrayList<>();
    }

    public void addItem(String item) {
        _queueItems.add(item);
    }

    public String getItem(int num) {
        try {
            return _queueItems.get(num);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void printQueue() {
        System.out.println(_queueItems);
    }

    public int getSize() {
        return _queueItems.size();
    }
}
