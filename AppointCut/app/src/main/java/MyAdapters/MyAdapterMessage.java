package MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointcut.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import DataModels.DataModelMessages;

public class MyAdapterMessage extends RecyclerView.Adapter<MyAdapterMessage.MyViewHolder> {

    private ArrayList <DataModelMessages> list;
    private MyAdapterMessage.ItemClickListener clickListener;

    public MyAdapterMessage(ArrayList <DataModelMessages> list, MyAdapterMessage.ItemClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyAdapterMessage.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_message, parent, false);
        return new MyAdapterMessage.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterMessage.MyViewHolder holder, int position) {
        holder.messageName.setText(list.get(position).getNamess());
        holder.messageContent.setText(list.get(position).getMessage());
        holder.messageTime.setText(list.get(position).getTime());
        holder.myProfilePics.setImageResource(list.get(position).getProfilePic());
        holder.messageRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<DataModelMessages> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView messageName, messageContent, messageTime;
        ImageView myProfilePics;
        ConstraintLayout messageRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messageName = itemView.findViewById(R.id.messageName);
            messageContent = itemView.findViewById(R.id.messageContent);
            messageTime = itemView.findViewById(R.id.txtMessageTime);
            myProfilePics = itemView.findViewById(R.id.myProfilePics);
            messageRowLayout = itemView.findViewById(R.id.messageRowLayout);
        }
    }
    public interface ItemClickListener{

        public void onItemClick(DataModelMessages dataModelMessages);
    }
}
