package com.example.advokat.cleanenergy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.activities.DetailsCostActivity;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CostAdapter extends RecyclerView.Adapter<CostAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    private List<Expenditures> items = new ArrayList<>();

    public void addAll(Collection<? extends Expenditures> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        if (inflater == null) inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.item_expenditure, parent, false));
    }

    @Override
    public void onBindViewHolder(final CostAdapter.ViewHolder holder, int position) {
        final Expenditures expenditure = items.get(position);
        holder.textCategory.setText(String.valueOf(expenditure.getExpenditureTypesId().getName()));
        if (expenditure.getCurrentAssetsTypeId() != null) {
            holder.textTypeOfCostAndCountAndMeasure.setText(String.format(Locale.getDefault(), "%s, %.0f %s"
                    , expenditure.getCurrentAssetsTypeId().getName()
                    , expenditure.getAmount()
                    , expenditure.getMeasureUnit().getName()));
        } else {
            holder.textTypeOfCostAndCountAndMeasure.setText(String.format(Locale.getDefault(), "%s, %.0f %s"
                    , expenditure.getComment()
                    , expenditure.getAmount()
                    , expenditure.getMeasureUnit().getName()));
        }
        holder.textPayerAndCount.setText(String.format(Locale.getDefault(), "%s, %.0f грн"
                , expenditure.getPayer().getName()
                , expenditure.getMoney()));

        holder.textDescription.setText(expenditure.getDescription());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String s = simpleDateFormat.format(expenditure.getExpenditureDate());
        holder.textDate.setText(s);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsCostActivity.class);
//                intent.putExtra(Expenditures.class.getName(), items.get(holder.getAdapterPosition()));
                intent.putExtra(Expenditures.class.getName(), items.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);

            }
        });

       /* holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Ви дійсно бажаєте видалити витрату?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(context);
                                progressDialog.setCancelable(true);
                                progressDialog.setMessage("Loading");
                                progressDialog.show();
                                ApiClient.retrofit().getMainService().deleteExpenditure()
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    int adapterPosition = holder.getAdapterPosition();
                                                    items.remove(adapterPosition);
                                                    notifyItemRemoved(adapterPosition);
//                                                    notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                                                }
                                                progressDialog.cancel();
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                progressDialog.cancel();
                                            }
                                        });
                            }
                        }).setNegativeButton(android.R.string.cancel, null)
                        .show();
                return true;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void sortByDate() {
        Collections.sort(items, new Comparator<Expenditures>() {
            @Override
            public int compare(Expenditures lhs, Expenditures rhs) {
                return Double.compare(lhs.getAmount(), rhs.getAmount());
            }
        });
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        /*  TextView textCategory, textTypeOfCost, textPayer, textCountAndDate, textDescription;*/
        TextView textCategory, textTypeOfCostAndCountAndMeasure, textPayerAndCount, textDescription, textDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textCategory = (TextView) itemView.findViewById(R.id.text_category);
            textTypeOfCostAndCountAndMeasure = (TextView) itemView.findViewById(R.id.text_type_of_cost_count_measure);
            textPayerAndCount = (TextView) itemView.findViewById(R.id.text_payer_count);
            textDescription = (TextView) itemView.findViewById(R.id.text_description);
            textDate = (TextView) itemView.findViewById(R.id.text_date);
        }
    }
}
