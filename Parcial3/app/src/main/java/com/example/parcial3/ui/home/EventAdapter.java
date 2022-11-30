package com.example.parcial3.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial3.R;
import com.example.parcial3.model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder>{

    private List<Event> mEvents;
    private OnItemClickListener onItemClickListener;

    public EventAdapter(List<Event> mEvents, OnItemClickListener onItemClickListener) {
        this.mEvents = mEvents;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mEvents.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public void setData(List<Event> events) {
        this.mEvents = events;
    }

    public interface OnItemClickListener {
        void onItemClickListener();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView date;
        private TextView description;
        private TextView latitude;
        private TextView longitude;
        private TextView userId;

        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            date = itemView.findViewById(R.id.tv_date);
            description = itemView.findViewById(R.id.tv_description);
            latitude = itemView.findViewById(R.id.tv_latitude);
            longitude = itemView.findViewById(R.id.tv_longitude);
            userId = itemView.findViewById(R.id.tv_user_id);
        }

        private void bind(Event event, OnItemClickListener onItemClickListener) {
            if (event != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(event.getImg(), 0, event.getImg().length);
                image.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false));
                date.setText(event.getDate());
                description.setText(event.getDescription());
                latitude.setText(event.getLatitude());
                longitude.setText(event.getLongitude());
                //userId.setText(event.getUserId());
            }
        }
    }
}
