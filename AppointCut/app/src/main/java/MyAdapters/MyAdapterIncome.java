package MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointcut.R;

import java.util.List;

import DataModels.DataModelIncome;

public class MyAdapterIncome extends RecyclerView.Adapter<MyAdapterIncome.MyViewHolder>{

    private List<DataModelIncome> list;

    public MyAdapterIncome(List<DataModelIncome> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapterIncome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_income, parent, false);
        return new MyAdapterIncome.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterIncome.MyViewHolder holder, int position) {
        holder.incomeDate.setText(list.get(position).getIncomeDate());
        holder.incomeService.setText(list.get(position).getIncomeService());
        holder.incomeServicePrice.setText(String.valueOf(list.get(position).getIncomeServicePrice()));
        holder.incomeTotalPrice.setText(String.valueOf(list.get(position).getIncomeTotalPrice()));
        holder.incomeAmount.setText(String.valueOf(list.get(position).getIncomeAmount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView incomeDate, incomeService, incomeServicePrice, incomeTotalPrice,incomeAmount;
        ConstraintLayout incomeRowLayout, serviceList;
        ImageButton arrow;
        boolean isArrowUpImage= false;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            incomeDate = itemView.findViewById(R.id.txtIncomeDate);
            incomeService = itemView.findViewById(R.id.txtIncomeService);
            incomeServicePrice = itemView.findViewById(R.id.txtIncomeServicePrice);
            incomeTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
            incomeAmount = itemView.findViewById(R.id.txtIncomeAmount);
            incomeRowLayout = itemView.findViewById(R.id.incomeRowLayout);

            arrow = itemView.findViewById(R.id.imageArrow);
            serviceList = itemView.findViewById(R.id.constraintLayoutIncome);

            serviceList.setVisibility(View.GONE);
            arrow.setImageResource(R.drawable.ic_arrow_down);


            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isArrowUpImage) {
                        serviceList.setVisibility(View.VISIBLE);
                        arrow.setImageResource(R.drawable.ic_arrow_up);
                        isArrowUpImage= false;
                    }
                    else{
                        serviceList.setVisibility(View.GONE);
                        arrow.setImageResource(R.drawable.ic_arrow_down);
                        isArrowUpImage= true;
                    }

                }
            });
        }
    }
}
