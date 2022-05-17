package com.ptithcm.group9.moneytor.data.local;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class AppViewModel extends AndroidViewModel {
    protected AppRepository appRepository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

}
