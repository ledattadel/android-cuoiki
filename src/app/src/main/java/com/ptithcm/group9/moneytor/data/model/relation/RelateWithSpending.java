package com.ptithcm.group9.moneytor.data.model.relation;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.ptithcm.group9.moneytor.data.model.Relate;
import com.ptithcm.group9.moneytor.data.model.Spending;

import java.util.List;

public class RelateWithSpending {
    @Embedded public Relate relate;
    @Relation(
            parentColumn = "rel_id",
            entityColumn = "spending_id",
            associateBy = @Junction(SpendingRelateCrossRef.class)
    )
    public List<Spending> spends;
}
