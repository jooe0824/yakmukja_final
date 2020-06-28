package org.techtown.mediclock;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmListAdapter extends RecyclerView.Adapter <AlarmListAdapter.AlarmListViewHolder> {

    private ArrayList<AlarmListFromDB> alarmList;

    public class AlarmListViewHolder extends  RecyclerView.ViewHolder{
        protected TextView set_alarm_name;
        protected TextView set_day;

        public AlarmListViewHolder (View view){
            super(view);
            this.set_alarm_name = (TextView) view.findViewById(R.id.put_set_name);
            this.set_day = (TextView) view.findViewById(R.id.put_set_day);
        }
    }

    public AlarmListAdapter(ArrayList<AlarmListFromDB> list){
        this.alarmList = list;
    }

    @Override
    public AlarmListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.alarm_list_adapter, viewGroup,false);
        AlarmListViewHolder alarmListViewHolder = new AlarmListViewHolder(view);

        return alarmListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmListAdapter.AlarmListViewHolder viewholder, int position) {
        viewholder.set_alarm_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.set_day.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);


        viewholder.set_alarm_name.setText(alarmList.get(position).getSet_alarm_name());
        viewholder.set_day.setText(alarmList.get(position).getSet_day());

    }

    @Override
    public int getItemCount() {
        return (null != alarmList ? alarmList.size() : 0);
    }
}
