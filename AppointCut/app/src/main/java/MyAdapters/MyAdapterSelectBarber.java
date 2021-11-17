package MyAdapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointcut.R;

import java.util.ArrayList;
import java.util.List;

import DataModels.DataModelSelectBarber;
import DataModels.DataModelSelectServices;

public class MyAdapterSelectBarber extends RecyclerView.Adapter<MyAdapterSelectBarber.MyViewHolder> {

    private ArrayList<DataModelSelectBarber> list;
    private int checkPosition = -1;

    public MyAdapterSelectBarber(ArrayList<DataModelSelectBarber> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapterSelectBarber.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_selectbarber, parent, false);
        return new MyAdapterSelectBarber.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterSelectBarber.MyViewHolder holder, int position) {
        holder.barberName.setText(list.get(position).getNamess());
        holder.barberSpecialty.setText(list.get(position).getSpecialty());
        holder.myProfilePics.setImageResource(list.get(position).getProfilePic());
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView barberName, barberSpecialty;
        ImageView myProfilePics;
        ConstraintLayout selectBarberRowLayout;
        CardView cardViewBarber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barberName = itemView.findViewById(R.id.barberName);
            barberSpecialty = itemView.findViewById(R.id.barberSpecialty);
            myProfilePics = itemView.findViewById(R.id.myProfilePics);
            selectBarberRowLayout = itemView.findViewById(R.id.selectBarberRowLayout);
            cardViewBarber = itemView.findViewById(R.id.cardViewBarber);
        }

        void bind(final DataModelSelectBarber dataModelSelectBarber) {
            if (checkPosition == -1) {
                cardViewBarber.setBackgroundColor(Color.WHITE);
            } else {
                if (checkPosition == getAdapterPosition()) {
                    cardViewBarber.setBackgroundColor(Color.parseColor("#FFFFBB33"));
                } else {
                    cardViewBarber.setBackgroundColor(Color.WHITE);
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardViewBarber.setBackgroundColor(Color.parseColor("#FFFFBB33"));
                    if (checkPosition != getAdapterPosition()) {
                        notifyItemChanged(checkPosition);
                        checkPosition = getAdapterPosition();
                    }
                }
            });
        }
    }
    public DataModelSelectBarber getSelected() {
        if (checkPosition != -1) {
            return list.get(checkPosition);
        }
        return null;
    }
}