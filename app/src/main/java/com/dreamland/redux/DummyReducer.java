package com.dreamland.redux;


import com.dreamland.redux.action.Action;
import com.dreamland.redux.state.DummyState;

/**
 * Created by XMD on 2016/6/23.
 */
public class DummyReducer implements Reducer<Action,DummyState> {
    @Override
    public DummyState call(Action action, StateTree state) {
        return new DummyState();
    }
}
