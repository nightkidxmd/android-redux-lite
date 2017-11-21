package com.dreamland.redux.middleware;


import android.support.annotation.NonNull;

import com.dreamland.redux.action.Action;


/**
 * Created by XMD on 16/6/15.
 */
public interface Next<A extends Action> {
    void dispatch(@NonNull A action);
}
