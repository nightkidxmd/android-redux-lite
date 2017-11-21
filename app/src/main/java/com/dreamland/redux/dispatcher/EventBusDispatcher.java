package com.dreamland.redux.dispatcher;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by XMD on 2016/5/5.
 */
public class EventBusDispatcher extends Dispatcher {
    protected EventBusDispatcher() {
        super();
    }
    @Override
    public void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
