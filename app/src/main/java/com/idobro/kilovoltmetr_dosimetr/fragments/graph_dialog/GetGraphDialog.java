package com.idobro.kilovoltmetr_dosimetr.fragments.graph_dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idobro.kilovoltmetr_dosimetr.R;
import com.idobro.kilovoltmetr_dosimetr.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetGraphDialog extends BaseDialog implements GraphsAdapter.OnItemClickListener {
    @BindView(R.id.closeView)
    View closeView;

    @BindView(R.id.graphsRecyclerView)
    RecyclerView graphsRecyclerView;

    private OnGraphSelectedListener onGraphSelectedListener;
    private GraphsAdapter graphsAdapter;

    private GetGraphDialog(@NonNull Context context) {
        super(context);
        show();
    }

    public static void start(@NonNull Context context, OnGraphSelectedListener onGraphSelectedListener) {
        GetGraphDialog dialog = new GetGraphDialog(context);
        dialog.setOnGraphSelectedListener(onGraphSelectedListener);
        dialog.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_get_graph);
        ButterKnife.bind(this);

        initView();
        showGraphs();
    }

    private void initView() {
        if (graphsAdapter == null)
            graphsAdapter = new GraphsAdapter(this);

        graphsRecyclerView.setLayoutManager(new LinearLayoutManager(getOwnerActivity()));
        graphsRecyclerView.hasFixedSize();
        graphsRecyclerView.setAdapter(graphsAdapter);
    }

    private void showGraphs() {
        List<String> testGraphStrings = new ArrayList<>();
        testGraphStrings.add("qwer");
        testGraphStrings.add("asdf");
        testGraphStrings.add("zxcv");
        testGraphStrings.add("tyui");
        graphsAdapter.setGraphs(testGraphStrings);
    }

    @OnClick(R.id.closeView)
    void closeClick() {
        dismiss();
    }

    private void setOnGraphSelectedListener(OnGraphSelectedListener onGraphSelectedListener) {
        this.onGraphSelectedListener = onGraphSelectedListener;
    }

    @Override
    public void onItemClick(String testString) {
        Toast.makeText(getContext(), testString, Toast.LENGTH_SHORT).show();
    }

    public interface OnGraphSelectedListener {
        void onGraphSelected(int position);
    }
}