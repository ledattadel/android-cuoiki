package com.ptithcm.group9.moneytor.services.reminder;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ptithcm.group9.moneytor.data.model.UserPref;
import com.ptithcm.group9.moneytor.utils.NotificationUtils;
import com.ptithcm.group9.moneytor.utils.PreferenceUtils;

public class ReminderViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> reminderStatus;
    private int hour;
    private int minutes;
    final private int time;

    public ReminderViewModel(@NonNull Application application) {
        super(application);
        time = PreferenceUtils.getInt(
                application.getApplicationContext(),
                UserPref.USER_REMINDER,
                -1);
        if (time >= 0) {
            hour = time / 60;
            minutes = time % 60;
            reminderStatus = new MutableLiveData<>(true);
        } else {
            hour = 8;
            minutes = 0;
            reminderStatus = new MutableLiveData<>(false);
        }
    }

    public MutableLiveData<Boolean> getReminderStatus() {
        return reminderStatus;
    }

    public boolean hasAccept() {
        return time >= 0;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinutes(int min) {
        this.minutes = min;
    }

    public void saveReminder(Context context) {
        PreferenceUtils.putInt(context,
                UserPref.USER_REMINDER,
                hour * 60 + minutes);
        if (hasAccept())
            NotificationUtils.cancelNoteReminder(context);
        NotificationUtils.scheduleNoteReminder(context, hour, minutes, 1);
    }

    public void dismissReminder(Context context) {
        PreferenceUtils.putInt(context,
                UserPref.USER_REMINDER,
                -1);
        NotificationUtils.cancelNoteReminder(context);
    }

}
