package com.dreamland.redux.action;

/**
 * Created by XMD on 16/6/14.
 */
abstract public class Action<T> {


    private final String action;
    private final T parameter;
    public Action(String action, T parameter){
        this.action = action;
        this.parameter = parameter;
    }

    public String getAction(){
        return action;
    }

    public T getParameter(){
        return parameter;
    }

    @Override
    public String toString() {
        return "Action{" +
                "action='" + action + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
