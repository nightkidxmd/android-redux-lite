package com.dreamland.redux;


import android.support.annotation.NonNull;

import com.dreamland.redux.state.State;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by XMD on 2016/6/22.
 */
public class StateTree<S extends State>{

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Map<String,S> states = new ConcurrentHashMap<>();

    private StateTree() {
    }

    void updateState(@NonNull S value){
        readWriteLock.writeLock().lock();
        states.put(value.getClass().getSimpleName(),value);
        readWriteLock.writeLock().unlock();
    }

    public void reinit(){
        for(S state:states.values()){
            state.init();
        }
    }

    public <T extends State> T getState(@NonNull Class<T> clazz){
        readWriteLock.readLock().lock();
        try{
            T ret = clazz.cast(states.get(clazz.getSimpleName()));
            return ret == null?null: clazz.cast(ret.clone());
        }finally {
            readWriteLock.readLock().unlock();
        }

    }

    public  void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        Collection<S> collection =states.values();
        for (S state:collection){
            writer.println(state.toString());
        }
    }

//----------------------Builder--------------------------------------------

    public static class Builder<S extends State> {
        private StateTree<S> state;
        public Builder(){
            state = new StateTree<>();
        }
        public Builder add(@NonNull S value){
            state.updateState(value);
            return this;
        }
        public StateTree build(){
            return state;
        }
    }


}
