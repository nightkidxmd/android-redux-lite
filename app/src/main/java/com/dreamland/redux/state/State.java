package com.dreamland.redux.state;


import com.dreamland.redux.action.Action;

/**
 * Created by XMD on 16/6/14.
 */
abstract public class State<T extends Action> implements Cloneable {
    private T action;
    @Override
    public State<T> clone() {
        try {
            return (State<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public State<T> attachAction(T action){
        this.action = action;
        return this;
    }

    public T getAction(){
        return action;
    }

    public void init(){}

    @Override
    public String toString() {
        return "State{" +
                "action=" + action +
                '}';
    }
}

