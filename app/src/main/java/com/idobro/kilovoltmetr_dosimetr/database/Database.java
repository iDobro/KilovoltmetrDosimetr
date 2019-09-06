package com.idobro.kilovoltmetr_dosimetr.database;

import android.content.Context;
import android.os.Handler;

import androidx.room.Room;

import com.idobro.kilovoltmetr_dosimetr.database.core.AppDatabase;
import com.idobro.kilovoltmetr_dosimetr.database.core.DatabaseManager;
import com.idobro.kilovoltmetr_dosimetr.database.dao.ChartDao;
import com.idobro.kilovoltmetr_dosimetr.database.entities.Chart;
import com.idobro.kilovoltmetr_dosimetr.viewmodel.ResponseCallback;

import java.util.List;

public class Database implements DatabaseManager {
    private AppDatabase db;
    private ChartDao chartDao;
    private Handler handler = new Handler();

    public Database(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "kilovoltmetrDb")
        .fallbackToDestructiveMigration().build();
        this.chartDao = chartDao;
    }

    @Override
    public void addNewChart(Chart chart) {
        new Thread(()->{
            List<Chart> charts = db.chartDao().getAll();
            if (charts.size() >= 30) {
                Chart firstEmp = db.chartDao().getFirstInsert();
                db.chartDao().delete(firstEmp);
            }
            db.chartDao().insert(chart);
        }).start();
    }

    @Override
    public void getLastChart(ResponseCallback<Chart> callback) {
        Chart chart = db.chartDao().getLastInsert();
        handler.post(()->callback.onSuccess(chart));
    }

    @Override
    public void deleteAllChart() {
        new Thread(() -> {
            List<Chart> charts = db.chartDao().getAll();
            db.chartDao().deleteAll(charts);
        }).start();
    }

    @Override
    public void getChartRecordsNumber(ResponseCallback<Integer> callback) {
        new Thread(() -> {
            int count = db.chartDao().getAll().size();
            handler.post(()->callback.onSuccess(count));
        }).start();
    }
}
