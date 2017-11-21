package com.dreamland.redux.action;

/**
 * Created by XMD on 16/6/17.
 * 耗时任务
 */
abstract public class AsyncAction<T> extends Action<T> {
    public AsyncAction(String action, T parameter) {
        super(action, parameter);
    }
}
