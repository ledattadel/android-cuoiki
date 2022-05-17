package com.ptithcm.group9.moneytor.services.goal;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.ptithcm.group9.moneytor.data.local.AppViewModel;
import com.ptithcm.group9.moneytor.data.model.SpendGoal;
import java.util.List;

public class SpendGoalViewModel extends AppViewModel{
    private LiveData<List<SpendGoal>> allSpendGoals;

    public SpendGoalViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<SpendGoal>> getAllSpendGoals()
    {
        return allSpendGoals;
    }
    public void insertSpendGoal(SpendGoal spendGoal)
    {
        appRepository.insertSpendGoal(spendGoal);
        allSpendGoals = appRepository.getAllSpendGoals();
    }
}
