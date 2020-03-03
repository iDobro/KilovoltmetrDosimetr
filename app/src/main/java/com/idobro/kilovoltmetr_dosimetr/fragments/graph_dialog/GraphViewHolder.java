package com.idobro.kilovoltmetr_dosimetr.fragments.graph_dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.idobro.kilovoltmetr_dosimetr.R;
import com.idobro.kilovoltmetr_dosimetr.base.BaseViewHolder;
import com.idobro.kilovoltmetr_dosimetr.fragments.graph_dialog.GraphsAdapter.OnItemClickListener;
import com.idobro.kilovoltmetr_dosimetr.models.GraphsDates;
import com.idobro.kilovoltmetr_dosimetr.utils.StringUtils;

import butterknife.BindView;

class GraphViewHolder extends BaseViewHolder {
    @BindView(R.id.graphTextView)
    TextView graphTextView;

    private GraphViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    static GraphViewHolder create(ViewGroup parent) {
        return new GraphViewHolder(generateView(parent, R.layout.item_graph));
    }

    void bind(GraphsDates dateText, OnItemClickListener onItemClickListener) {
        graphTextView.setText(String.valueOf(dateText.getDate()));

        if (onItemClickListener != null)
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(String.valueOf(dateText.getDate())));
    }
}