package com.dreamland.redux.dispatcher;

/**
 * Created by XMD on 2016/5/5.
 */
abstract public class Dispatcher {
    private static Dispatcher instance;

    protected Dispatcher(){}
    synchronized public static Dispatcher getInstance(){
        if(instance == null){
            instance = new EventBusDispatcher();
        }
        return instance;
    }

    /**
     * 注册订阅者
     * @param subscriber
     */
    abstract public void register(Object subscriber);

    /**
     * 反注册订阅者
     * @param subscriber
     */
    abstract public void unregister(Object subscriber);

    /**
     * 发消息
     * @param event
     */
    abstract public void post(Object event);
}
