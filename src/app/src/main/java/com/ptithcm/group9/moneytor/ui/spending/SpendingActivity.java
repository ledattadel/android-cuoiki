package com.ptithcm.group9.moneytor.ui.spending;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ptithcm.group9.moneytor.R;
import com.ptithcm.group9.moneytor.data.model.FilterState;
import com.ptithcm.group9.moneytor.data.model.Spending;
import com.ptithcm.group9.moneytor.databinding.ActivitySpendingBinding;
import com.ptithcm.group9.moneytor.services.options.Category;
import com.ptithcm.group9.moneytor.services.options.FilterViewModel;
import com.ptithcm.group9.moneytor.ui.analysis.AnalysisActivity;
import com.ptithcm.group9.moneytor.ui.base.NoteBaseActivity;
import com.ptithcm.group9.moneytor.utils.DateTimeUtils;
import com.ptithcm.group9.moneytor.utils.FilterSelectUtils;

import java.util.ArrayList;
import java.util.List;

public class SpendingActivity extends NoteBaseActivity<ActivitySpendingBinding> {

    private ActivitySpendingBinding binding;
    private List<Spending> spendings;
    private SpendingAdapter spendingAdapter;
    private Context context;
    private SearchView searchView;
    private FilterSelectUtils filterSelectUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_spending;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        this.context = this.getApplicationContext();
        this.setTitle(getString(R.string.toolbar_goal_spending));
        initializeViews();

        filterViewModel = new ViewModelProvider(this).get(FilterViewModel.class);
        filterViewModel.getAllSpending().observe(this, spendingList -> {
            spendings = spendingList;
            spendingAdapter.setSpending(spendings);
        });

        filterViewModel.setFilterState(new FilterState());
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddSpendingActivity.class);
            startActivity(intent);
        });
        setAnalyzeButton();

    }

    private void initializeViews() {
        filterSelectUtils = new FilterSelectUtils(this);
        spendingAdapter = new SpendingAdapter(this, spendings);
        binding.spendingList.setAdapter(spendingAdapter);
        binding.spendingList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAnalyzeButton() {
        binding.analyzeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AnalysisActivity.class);
            intent.putExtra("filter_state", filterViewModel.getFilterState());
            startActivity(intent);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void showDialog() {
        AlertDialog alertDialog = filterSelectUtils.createMainDialog();
        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionFilter) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Spending> filter(String text) {
        List<Spending> filteredList = new ArrayList<>();
        for (Spending item : spendings) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    // for test filter, should be delete
    public void testFilter() {
        List<String> cats = new ArrayList<>();
        cats.add(Category.FOOD_AND_DRINK.getId());
        //filterViewModel.setFilterState(new FilterState(cats, DateTimeUtils.getDateInMillis(9, 12, 2021), DateTimeUtils.getDateInMillis(22, 12, 2021)));
        filterViewModel.setFilterState(new FilterState(cats, DateTimeUtils.getMillisByDate(10, 12, 2021), DateTimeUtils.getMillisByDate(22, 12, 2021)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.spending_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                spendingAdapter.filterList(filter(s));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                spendingAdapter.filterList(filter(s));
                return false;
            }
        });
        return true;
    }

}