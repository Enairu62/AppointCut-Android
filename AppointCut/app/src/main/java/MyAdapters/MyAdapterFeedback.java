package MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointcut.R;

import java.util.ArrayList;

import DataModels.DataModelFeedback;

public class MyAdapterFeedback extends RecyclerView.Adapter<MyAdapterFeedback.MyViewHolder>{

    private ArrayList<DataModelFeedback> arrayFeedBack;

    public MyAdapterFeedback(ArrayList<DataModelFeedback> arrayFeedBack){
        this.arrayFeedBack = arrayFeedBack;
    }

    @NonNull
    @Override
    public MyAdapterFeedback.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_feedback, parent, false);
        return new MyAdapterFeedback.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterFeedback.MyViewHolder holder, int position) {
        holder.feedbackDate.setText(arrayFeedBack.get(position).getArrayDate());
        holder.feedbackName.setText(arrayFeedBack.get(position).getFeedbackName());
        holder.feedbackRating.setRating(arrayFeedBack.get(position).getFeedbackRating());
        holder.feedbackContent.setText(arrayFeedBack.get(position).getFeedbackContent());
        holder.feedbackRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // clickListener.onItemClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayFeedBack.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView feedbackDate, feedbackName, feedbackContent;
        RatingBar feedbackRating;
        ConstraintLayout feedbackRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feedbackDate = itemView.findViewById(R.id.feedbackDate);
            feedbackName = itemView.findViewById(R.id.feedbackName);
            feedbackRating = itemView.findViewById(R.id.feedbackRating);
            feedbackContent = itemView.findViewById(R.id.feedbackContent);
            feedbackRowLayout = itemView.findViewById(R.id.feedbackRowLayout);
        }
    }
   /*public interface ItemClickListener{

        public void onItemClick(DataModelBarberShop dataModelBarberShop);
    }*/
}
