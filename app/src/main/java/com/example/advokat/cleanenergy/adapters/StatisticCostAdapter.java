package com.example.advokat.cleanenergy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.entities.cost.SumMoney;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StatisticCostAdapter extends RecyclerView.Adapter<StatisticCostAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    private List<SumMoney> items = new ArrayList<>();

    public void addAll(Collection<? extends SumMoney> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public StatisticCostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        if (inflater == null) inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.item_statistic_cost, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SumMoney sumMoney = items.get(position);
        holder.textPayer.setText(sumMoney.getPayer().getName());
        holder.textTotalCost.setText(String.valueOf(sumMoney.getMoney()));
    }

    /*//@Override
    public void onBindViewHolder(final ExpenditureAdapter.ViewHolder holder, int position) {
        Expenditures expenditure = items.get(position);
        holder.textCategory.setText(String.valueOf(expenditure.getExpenditureTypesId().getName()));
        if (expenditure.getCurrentAssetsTypeId() != null) {
            holder.textTypeOfCost.setText(String.valueOf(expenditure.getCurrentAssetsTypeId().getName()));
        } else {
            holder.textTypeOfCost.setText(String.valueOf(expenditure.getComment()));
        }
        holder.textPayer.setText(String.valueOf(expenditure.getPayer().getName()));
        holder.textCountAndDate.setText(String.format(Locale.getDefault(), "%.2f, %s, %s", expenditure.getAmount(), expenditure.getMeasureUnit().getName(), expenditure.getExpenditureDate()));
        holder.textDescription.setText(expenditure.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsCostActivity.class);
                intent.putExtra(Expenditures.class.getName(), items.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textPayer, textTotalCost;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textPayer = (TextView) itemView.findViewById(R.id.statistic_payer);
            textTotalCost = (TextView) itemView.findViewById(R.id.total_cost);
        }
    }

}
