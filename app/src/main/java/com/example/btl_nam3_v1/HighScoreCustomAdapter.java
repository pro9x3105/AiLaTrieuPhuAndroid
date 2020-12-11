package com.example.btl_nam3_v1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreCustomAdapter extends BaseAdapter implements Filterable {
    private ArrayList<HighScore> listHighScore;
    private ArrayList<HighScore> listHighScoreFilter;
    private Activity context;
    private LayoutInflater inflater;
    private HighScoreCustomAdapter.ValueFilter valueFilter;


    public HighScoreCustomAdapter(Activity context, ArrayList<HighScore> listHighScore) {
        this.context = context;
        this.listHighScore = listHighScore;
        listHighScoreFilter = listHighScore;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getFilter();
    }
    public class ViewHolder{
        CheckBox checkBoxHighScore;
        TextView textViewIdHighScore,textViewHoTenHighScore,textViewScoreHighScore,textViewMoneyHighScore;
    }

    @Override
    public int getCount() {
        return listHighScore.size();
    }

    @Override
    public Object getItem(int position) {
        return listHighScore.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        HighScoreCustomAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new HighScoreCustomAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.activity_dong_highscore, null);
            holder.checkBoxHighScore = (CheckBox) convertView.findViewById(R.id.checkBoxHighScore) ;
            holder.textViewIdHighScore =  convertView.findViewById(R.id.textViewIdHighScore);
            holder.textViewHoTenHighScore =  convertView.findViewById(R.id.textViewHoTenHighScore);
            holder.textViewScoreHighScore =  convertView.findViewById(R.id.textViewScoreHighScore);
            holder.textViewMoneyHighScore =  convertView.findViewById(R.id.textViewMoneyHighScore);

            convertView.setTag(holder);
        }
        else {
            holder = (HighScoreCustomAdapter.ViewHolder) convertView.getTag();
        }
        holder.checkBoxHighScore.setFocusable(false);
        holder.checkBoxHighScore.setChecked(listHighScore.get(position).isSelected());
        holder.textViewIdHighScore.setText("" + listHighScore.get(position).getId_HighScore());
        holder.textViewHoTenHighScore.setText("" + listHighScore.get(position).getHoTen_HighScore());
        holder.textViewScoreHighScore.setText("" + listHighScore.get(position).getScore_HighScore());
        holder.textViewMoneyHighScore.setText("" + listHighScore.get(position).getMoney_HighScore());


        holder.checkBoxHighScore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isSelected) {
                listHighScore.get(position).setSelected(isSelected);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) { valueFilter= new HighScoreCustomAdapter.ValueFilter();
        }
        return valueFilter;
    }
    private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint!=null && constraint.length()>0){
                ArrayList<HighScore> filterList=new ArrayList<HighScore>();
                for(int i=0;i<listHighScoreFilter.size();i++){
                    if((listHighScoreFilter.get(i).getHoTen_HighScore().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        HighScore highScores = new HighScore();
                        highScores.setId_HighScore(listHighScoreFilter.get(i).getId_HighScore());
                        highScores.setHoTen_HighScore(listHighScoreFilter.get(i).getHoTen_HighScore());
                        highScores.setScore_HighScore(listHighScoreFilter.get(i).getScore_HighScore());
                        highScores.setMoney_HighScore(listHighScoreFilter.get(i).getMoney_HighScore());
                        highScores.setSelected(listHighScoreFilter.get(i).isSelected());
                        filterList.add(highScores);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }
            else{
                results.count=listHighScoreFilter.size();
                results.values=listHighScoreFilter;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listHighScore=(ArrayList<HighScore>) results.values;
            notifyDataSetChanged();
        }
    }
}
