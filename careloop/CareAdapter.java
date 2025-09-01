
package com.example.careloop;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

public class CareAdapter extends RecyclerView.Adapter<CareAdapter.CareViewHolder> {

    private ArrayList<String> careList;

    public CareAdapter(ArrayList<String> careList) {
        this.careList = careList;
    }

    @NonNull
    @Override
    public CareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_care, parent, false);
        return new CareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareViewHolder holder, int position) {
        String care = careList.get(position);
        holder.txtCareName.setText(care);
    }

    @Override
    public int getItemCount() {
        return careList.size();
    }

    static class CareViewHolder extends RecyclerView.ViewHolder {
        TextView txtCareName;

        public CareViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCareName = itemView.findViewById(R.id.tvRelation); // make sure your item_care.xml has this ID
        }
    }
}
