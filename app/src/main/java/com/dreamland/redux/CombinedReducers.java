package com.dreamland.redux;

/**
 * Created by tuyou on 16/6/15.
 */


import com.dreamland.redux.action.Action;
import com.dreamland.redux.state.State;

import java.util.Arrays;
import java.util.List;


public class CombinedReducers<A extends Action, S extends State> implements Reducer<A, S> {

    private final List<? extends Reducer<A, S>> reducers;

    /**
     * 用于combine的reducer只能处理同一块区域的state
     * @param reducers
     * @param <A>
     * @param <S>
     * @return
     */
    public static <A extends Action, S extends State> CombinedReducers<A, S> from(Reducer<A, S>... reducers) {
        return new CombinedReducers<>(Arrays.asList(reducers));
    }

    public CombinedReducers(List<? extends Reducer<A, S>> reducers) {
        this.reducers = reducers;
    }

    @Override
    public S call(A action, StateTree rootState) {
        S newState = null;
        for (Reducer<A, S> reducer : reducers) {
            S state = reducer.call(action, rootState);
            if(state != null){
                rootState.updateState(state);
                newState = state;
            }
        }
        return newState;
    }

}

