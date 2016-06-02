package com.example.advokat.cleanenergy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.entities.income.IncomeList;

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
        holder.textOperation.setText(income.getIncomeSources().getName());
        holder.textTypeOfOperation.setText(income.getIncomeTypes().getName());
        if (income.getProductTypesTypes() != null) {
            holder.textTypeOfProduct.setText(income.getProductTypesTypes().getName());
        }
        holder.textBuyer.setText(buyer);
        holder.textPayer.setText(income.getPayer().getName());
        holder.textMoney.setText(String.valueOf(income.getMoney()) + " грн");
        String measureUnit = "";
        if (income.getMeasureUnit() == null) {
            measureUnit = "qw";
        }
        holder.textCountAndDate.setText(String.format(Locale.getDefault(), "%.2f, %s, %s, %s", income.getAmount(), measureUnit /*income.getMeasureUnit().getName()*/, income.getIncomeDate(), locations));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, DetailsIncomeActivity.class);
                intent.putExtra(IncomeList.class.getName(), items.get(holder.getAdapterPosition()));
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textOperation, textTypeOfOperation, textTypeOfProduct, textBuyer, textPayer, textMoney, textCountAndDate; //textDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textOperation = (TextView) itemView.findViewById(R.id.text_operation);
            textTypeOfOperation = (TextView) itemView.findViewById(R.id.text_type_of_operation);
            textTypeOfProduct = (TextView) itemView.findViewById(R.id.text_type_of_product);
            textBuyer = (TextView) itemView.findViewById(R.id.text_buyer);
            textPayer = (TextView) itemView.findViewById(R.id.text_payer);
            textMoney = (TextView) itemView.findViewById(R.id.text_money);
            textCountAndDate = (TextView) itemView.findViewById(R.id.text_count_and_date);
            //textDescription = (TextView) itemView.findViewById(R.id.text_description);
        }
    }
}
