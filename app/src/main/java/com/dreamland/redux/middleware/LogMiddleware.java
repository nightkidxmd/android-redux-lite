package com.dreamland.redux.middleware;


import android.util.Log;

import com.dreamland.redux.Store;
import com.dreamland.redux.action.Action;
import com.dreamland.redux.state.State;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by tuyou on 16/6/17.
 * Append to last two
 */
public class LogMiddleware<A extends Action> implements Middleware<A> {
    public LogMiddleware() {
        try{
            Store.getInstance().subscribe(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dispatch(Store store, A action, Next<A> next) {
        Log.d("LogMiddleware","--> " + action.toString());
        next.dispatch(action);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onStateChange(State state){
       Log.w("LogMiddleware","onStateChange " + state.toString());
    }
}