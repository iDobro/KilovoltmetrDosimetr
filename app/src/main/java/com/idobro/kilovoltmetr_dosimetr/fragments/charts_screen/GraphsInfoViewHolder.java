package com.idobro.kilovoltmetr_dosimetr.fragments.charts_screen;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idobro.kilovoltmetr_dosimetr.R;
import com.idobro.kilovoltmetr_dosimetr.base.BaseViewHolder;

import butterknife.BindView;

public class GraphsInfoViewHolder extends BaseViewHolder {
    @BindView(R.id.vChartColor)
    View vChartColor;

    @BindView(R.id.tvInfo)
    TextView tvInfo;

    private GraphsInfoViewHolder(View itemView) {
        super(itemView);
    }

    static GraphsInfoViewHolder create(ViewGroup parent) {
        return new GraphsInfoViewHolder(generateView(parent, R.layout.item_chart_info));
    }

    public void bind(InfoItem item) {
        if (!item.getColor().isEmpty())
            vChartColor.setBackgroundColor(Color.parseColor(item.getColor()));

        if (!item.getText().isEmpty())
            tvInfo.setText(item.getText());
    }
}