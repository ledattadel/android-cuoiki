package com.ptithcm.group9.moneytor.services.notification.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ptithcm.group9.moneytor.data.model.UserPref;
import com.ptithcm.group9.moneytor.utils.PreferenceUtils;

public class BubbleDismissedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PreferenceUtils.putBoolean(context, UserPref.USER_WIDGET, false);
    }
}
