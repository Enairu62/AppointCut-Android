package MyAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import online.appointcut.R;

import java.util.List;

import DataModels.DataModelSelectBarber;

public class MyAdapterSelectBarber extends RecyclerView.Adapter<MyAdapterSelectBarber.MyViewHolder> {

    private List<DataModelSelectBarber> list;
    // private MyAdapterSelectBarber.ItemClickListener clickListener;

    public MyAdapterSelectBarber(List<DataModelSelectBarber> list) {
        this.list = list;
        // this.clickListener = clickListener;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView barberName, barberSpecialty;
        ImageView myProfilePics;
        ConstraintLayout selectBarberRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            barberName = itemView.findViewById(R.id.barberName);
            barberSpecialty = itemView.findViewById(R.id.barberSpecialty);
            myProfilePics = itemView.findViewById(R.id.myProfilePics);
            selectBarberRowLayout = itemView.findViewById(R.id.selectBarberRowLayout);

        }
    }

    public interface ItemClickListener {

        public void onItemClick(DataModelSelectBarber dataModelSelectBarber);
    }
}