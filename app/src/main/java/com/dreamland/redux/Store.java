package com.dreamland.redux;


import com.dreamland.redux.action.Action;
import com.dreamland.redux.action.SyncAction;
import com.dreamland.redux.annotation.ReducerClass;
import com.dreamland.redux.dispatcher.Dispatcher;
import com.dreamland.redux.middleware.Middleware;
import com.dreamland.redux.middleware.Next;
import com.dreamland.redux.state.State;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by XMD on 16/6/15.
 *
 */
public class Store {
    private StateTree currentState;
    private List<Next<Action>> middleWares;

    private static Store mInstance;



    private Store(){

    }

    public static Store getInstance(){
        if(mInstance == null){
            synchronized (Store.class){
                if(mInstance == null){
                    mInstance = new Store();
                }
            }
        }
        return mInstance;
    }

    public void init(StateTree initState, Middleware... middlewares){
        _init(initState,middlewares);
    }

    private void _init(StateTree initState, Middleware... middlewares){
        Dispatcher.getInstance().register(this);
//------------------------------------------------------------
        this.middleWares = new ArrayList<>();
        // 首先把最后通知订阅者的部分抽象成Next加入
        this.middleWares.add(new Next<Action>() {
            @Override
            public void dispatch(Action action) {
                _dispatch(action);
            }
        });

        // 逆序建立中间件的链表关系 next-->next-->next
        if(middlewares != null){
            for (int i = middlewares.length - 1; i >= 0; i--) {
                // 将中间件逐条加入
                final Middleware<Action> proxy = middlewares[i];
                final Next<Action> next = this.middleWares.get(0);
                this.middleWares.add(0, new Next<Action>() {
                    @Override
                    public void dispatch(Action action) {
                        proxy.dispatch(Store.this, action, next);
                    }
                });
            }
        }
        currentState = initState;
//------------------------------------------------------------
    }

    /**
     * using {@link #getInstance()}
     * @param initState
     * @param middlewares
     */
    @Deprecated
    public Store(StateTree initState, Middleware... middlewares) {
        _init(initState,middlewares);
    }
    public void subscribe(Object o){
        Dispatcher.getInstance().register(o);
    }

    public void unsubscribe(Object o){
        Dispatcher.getInstance().unregister(o);
    }
    public void dispatch(Action action) {Dispatcher.getInstance().post(action);}

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    synchronized public void onDispatch(Action action){
        middleWares.get(0).dispatch(action);
    }

//--------------------------------------------------------------

    public <T extends State> T getState(Class<T> clazz) {
        return clazz.cast(currentState.getState(clazz));
    }

    // ------------------------------------------------------------------- private area

    private void _notifyStateChanged(State state) {
        Dispatcher.getInstance().post(state);
    }

    synchronized private void _dispatch(Action action) {
        //只将同步action发送给Reducer
        if(action instanceof SyncAction){
            Reducer reducer = _getReducer(action);
            if(reducer != null){
                State newstate = reducer.call(action, currentState);
                if(newstate != null){
                    currentState.updateState(newstate);
                    _notifyStateChanged(newstate.clone().attachAction(action));
                }
            }
        }
    }

    private Reducer _getReducer(Action action){
        Class[] reducerClazzes = action.getClass().getAnnotation(ReducerClass.class).value();
        Reducer reducer = null;
        if(reducerClazzes.length > 1){
            Reducer[] reducers = new Reducer[reducerClazzes.length];
            for(int i=0;i< reducerClazzes.length;i++){
                try {
                    reducers[i] = (Reducer) reducerClazzes[i].getConstructor().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            reducer = CombinedReducers.from(reducers);
        }else if(reducerClazzes.length == 1){
            try {
                reducer = (Reducer) reducerClazzes[0].getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }else{
            throw new IllegalArgumentException("You forget to specify your reducer!!!");
        }
        return reducer;
    }

    public void reinitState(){
        currentState.reinit();
    }

    public  void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        currentState.dump(fd,writer,args);
    }
}



