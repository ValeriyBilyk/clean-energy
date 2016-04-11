package com.example.advokat.cleanenergy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.activities.DetailsActivity;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ExpenditureAdapter extends RecyclerView.Adapter<ExpenditureAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    private List<Expenditures> items = new ArrayList<>();

    public void addAll(Collection<? extends Expenditures> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ExpenditureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        if (inflater == null) inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.item_expenditure, parent, false));
    }

    @Override
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
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Expenditures.class.getName(), items.get(holder.getAdapterPosition()));
                context.startActivity(intent);

                // TODO: 4/7/16 put into activity
//                Expenditure expenditure = getIntent().getParcelableExtra(Expenditure.class.getName());
//                boolean isEditing = expenditure != null;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textCategory, textTypeOfCost, textPayer, textCountAndDate, textDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textCategory = (TextView) itemView.findViewById(R.id.text_category);
            textTypeOfCost = (TextView) itemView.findViewById(R.id.text_type_of_cost);
            textPayer = (TextView) itemView.findViewById(R.id.text_payer);
            textCountAndDate = (TextView) itemView.findViewById(R.id.text_count_and_date);
            textDescription = (TextView) itemView.findViewById(R.id.text_description);
        }
    }
}
