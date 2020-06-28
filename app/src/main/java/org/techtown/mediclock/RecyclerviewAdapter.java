package org.techtown.mediclock;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> implements Filterable {

    private ArrayList<Recent> mList;
    private ArrayList<Recent> filterList;
    private LayoutInflater mInflate;
    private Context mContext;

    public RecyclerviewAdapter(Context context, ArrayList<Recent> items) {
        this.filterList = items;
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public RecyclerviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new MyViewHolder(view);
    }

    private onItemListener mListener;
    public void setOnClickListener(onItemListener listener){
        mListener = listener;
    }

    public void dataSetChanged(ArrayList<Recent> exampleList){
        filterList = exampleList;
        notifyDataSetChanged();
    }

    @Override // 뷰 홀더가 필요한 위치에 할당 될 때, 어댑터는 onBindViewHolder() 함수를 호출
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.ITEM_NAME.setText(filterList.get(position).getITEM_NAME());
        holder.ENTP_NAME.setText(filterList.get(position).getENTP_NAME());
        holder.ETC_OTC_CODE.setText(filterList.get(position).getETC_OTC_CODE());

        if(mListener != null){
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mListener.onItemClicked(position);
                }
            });
        }
    }

    public Filter getFilter(){
        return exampleFilter;
    }

    @Override // Return the size of your dataset (invoked by the layout manager)
    public int getItemCount() { //전체 아이템 갯수 리턴.
        return this.filterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder { // 아이템 뷰를 저장하는 뷰홀더 클래스.
        private TextView ITEM_NAME, ENTP_NAME, ETC_OTC_CODE;
        View v;

        public MyViewHolder(View itemView) {
            super(itemView);
            ITEM_NAME = itemView.findViewById(R.id.item_name);
            ENTP_NAME = itemView.findViewById(R.id.entp_name);
            ETC_OTC_CODE = itemView.findViewById(R.id.etc_otc_code);
            v = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Recent arrayitem = filterList.get(pos);

                    Intent infointent = new Intent(mContext, Search_List.class);

                    infointent.putExtra("item_name", arrayitem.getITEM_NAME());
                    infointent.putExtra("entp_name", arrayitem.getENTP_NAME());
                    infointent.putExtra("etc_otc_code", arrayitem.getETC_OTC_CODE());
                    infointent.putExtra("item_permit_date", arrayitem.getITEM_PERMIT_DATE());
                    infointent.putExtra("ENTP_NO", arrayitem.getENTP_NO());
                    infointent.putExtra("bar_code", arrayitem.getBAR_CODE());
                    infointent.putExtra("item_seq", arrayitem.getITEM_SEQ());
                    infointent.putExtra("chart", arrayitem.getCHART());
                    infointent.putExtra("material_name", arrayitem.getMATERIAL_NAME());
                    infointent.putExtra("PACK_UNIT", arrayitem.getPACK_UNIT());
                    infointent.putExtra("PERMIT_KIND_NAME", arrayitem.getPERMIT_KIND_NAME());
                    infointent.putExtra("CANCEL_DATE", arrayitem.getCANCEL_DATE());
                    infointent.putExtra("MAKE_MATERIAL_FLAG", arrayitem.getMAKE_MATERIAL_FLAG());
                    infointent.putExtra("INDUTY_TYPE", arrayitem.getINDUTY_TYPE());
                    infointent.putExtra("CHANGE_DATE", arrayitem.getCHANGE_DATE());
                    infointent.putExtra("INGR_NAME", arrayitem.getINGR_NAME());
                    infointent.putExtra("EE_DOC_DATA", arrayitem.getEE_DOC_DATA());
                    infointent.putExtra("UD_DOC_DATA", arrayitem.getUD_DOC_DATA());
                    infointent.putExtra("NB_DOC_DATA", arrayitem.getNB_DOC_DATA());
                    infointent.putExtra("STORAGE_METHOD", arrayitem.getSTORAGE_METHOD());
                    infointent.putExtra("VALID_TERM", arrayitem.getVALID_TERM());


                    mContext.startActivity(infointent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });


        }
    }

    public interface onItemListener{
        void onItemClicked(int position);
    }

    public Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

//             if(barcodeinfo != null){
//                 constraint = barcodeinfo;
//             }

            String charString = constraint.toString();

            Log.e("CHAR String", charString);
            if (charString.isEmpty()) {
                filterList = mList;
            } else {
                ArrayList<Recent> filteringList = new ArrayList<>();
                // Log.e("CHAR String222", charString);
                for (Recent item : mList) {
                    if (item.getITEM_NAME().contains(charString.trim())) {//toLowerCase()., .toLowerCase()
                        filteringList.add(item);
                    }
                    if(item.getBAR_CODE().contains(charString.trim())){
                        filteringList.add(item);
                    }
                }
                Log.e("FilteringlistLength", String.valueOf(filteringList.size()));
                filterList = filteringList;
                Log.e("FilterListLength", String.valueOf(filterList.size()));
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (ArrayList<Recent>) results.values;
            Log.e("final filterList", String.valueOf(filterList.size()));

            notifyDataSetChanged();
        }
    };
}