package com.example.advokat.cleanenergy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.activities.DetailsIncomeActivity;
import com.example.advokat.cleanenergy.entities.income.IncomeList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    private List<IncomeList> items = new ArrayList<>();

    public void addAll(Collection<? extends IncomeList> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public IncomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        if (inflater == null) inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.item_income, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IncomeList income = items.get(position);

        String buyer, locations;
        if (income.getBuyerId() == null) {
            buyer = income.getBuyer();
            locations = income.getLocations().getName();
        } else {
            buyer = income.getBuyerId().getName();
            locations = income.getBuyerId().getLocations().getName();
        }

        int id = (int) income.getIncomeSources().getId();
        switch(id) {
            case 1:
                holder.textOperation.setText(income.getIncomeSources().getName());
                holder.textTypeOfOperation.setText(income.getIncomeTypes().getName());
                holder.textTypeOfProduct.setText(income.getProductTypesTypes().getName());
                holder.textCountAndWeight.setText(String.format(Locale.getDefault(), "%.0f пакетів по %d %s",
                        income.getAmount(),
                        income.getBags(),
                        income.getMeasureUnit().getName()));

                break;
            default:
                holder.textOperation.setText(income.getIncomeSources().getName());
                holder.textTypeOfOperation.setText(income.getIncomeTypes().getName());
                holder.textTypeOfProduct.setText("без продажу");
                holder.textCountAndWeight.setText(String.format(Locale.getDefault(), "%.0f", income.getAmount()));
                break;
        }

        holder.textBuyerAndLocation.setText(String.format(Locale.getDefault(), "%s, %s", buyer, locations));
        holder.textPayerAndCost.setText(String.format(Locale.getDefault(), "%s, %.0f грн",
                income.getPayer().getName(),
                income.getMoney()));
        holder.textDescription.setText(income.getComment());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String s = simpleDateFormat.format(income.getIncomeDate());
        holder.textDate.setText(s);

        /*if (income.getProductTypesTypes() != null) {
            holder.textTypeOfProduct.setText(income.getProductTypesTypes().getName());
        }

        holder.textCountAndWeight.setText(String.format(Locale.getDefault(), "%.0f пакетів по %d %s",
                income.getAmount(),
                income.getBags(),
                income.getMeasureUnit().getName()));

        holder.textBuyerAndLocation.setText(String.format(Locale.getDefault(), "%s, %s", buyer, locations));
        holder.textPayerAndCost.setText(String.format(Locale.getDefault(), "%s, %.0f грн",
                income.getPayer().getName(),
                income.getMoney()));

        holder.textDescription.setText(income.getComment());
        holder.textDate.setText(income.getIncomeDate().toString());*/
       /* holder.textBuyer.setText(buyer);
        holder.textPayer.setText(income.getPayer().getName());
        holder.textMoney.setText(String.valueOf(income.getMoney()) + " грн");
        String measureUnit = "";
        if (income.getMeasureUnit() == null) {
            measureUnit = "qw";
        }
        holder.textCountAndDate.setText(String.format(Locale.getDefault(), "%.2f, %s, %s, %s", income.getAmount(), measureUnit*/ /*income.getMeasureUnit().getName()*//*, income.getIncomeDate(), locations));*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsIncomeActivity.class);
                intent.putExtra(IncomeList.class.getName(), items.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textOperation, textTypeOfOperation, textTypeOfProduct, textCountAndWeight, textBuyerAndLocation, textPayerAndCost, textDescription, textDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textOperation = (TextView) itemView.findViewById(R.id.text_operation);
            textTypeOfOperation = (TextView) itemView.findViewById(R.id.text_type_of_operation);
            textTypeOfProduct = (TextView) itemView.findViewById(R.id.text_type_of_product);
            textCountAndWeight = (TextView) itemView.findViewById(R.id.text_count_and_weight);
            textBuyerAndLocation = (TextView) itemView.findViewById(R.id.text_buyer_and_location);
            textPayerAndCost = (TextView) itemView.findViewById(R.id.text_payer_and_cost);
            textDescription = (TextView) itemView.findViewById(R.id.text_description_income);
            textDate = (TextView) itemView.findViewById(R.id.text_date_income);
        }
    }
}
