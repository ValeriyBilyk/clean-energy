package com.example.advokat.cleanenergy.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advokat.cleanenergy.R;
import com.example.advokat.cleanenergy.activities.DetailsCostActivity;
import com.example.advokat.cleanenergy.entities.cost.Expenditures;
import com.example.advokat.cleanenergy.rest.ApiClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        final Expenditures expenditure = items.get(position);
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
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
                                ApiClient.retrofit().getMainService().deleteIncome(expenditure.getId())
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
        });
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
