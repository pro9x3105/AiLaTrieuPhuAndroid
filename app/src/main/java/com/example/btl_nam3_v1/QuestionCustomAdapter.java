package com.example.btl_nam3_v1;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionCustomAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Question> listQuestion;
    private ArrayList<Question> listQuestionFilter;
    private Activity context;
    private LayoutInflater inflater;
    private ValueFilter valueFilter;


    public QuestionCustomAdapter(Activity context, ArrayList<Question> listQuestion) {
        this.context = context;
        this.listQuestion = listQuestion;
        listQuestionFilter = listQuestion;
        this.inflater = LayoutInflater.from(context);
        getFilter ();
    }
    public class ViewHolder{
        CheckBox checkBoxSelect;
        TextView textViewQuestionWithCheckBox;
    }

    @Override
    public int getCount() {
        return listQuestion.size();
    }

    @Override
    public Object getItem(int position) {
        return listQuestion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_dong_question_with_checkbox, null);
            holder.checkBoxSelect = (CheckBox) convertView.findViewById(R.id. checkBoxSelect) ;
            holder.textViewQuestionWithCheckBox =  convertView.findViewById(R.id.textViewQuestionWithCheckBox);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBoxSelect.setFocusable(false);
        holder.checkBoxSelect.setChecked(listQuestion.get(position).isSelected());
        holder.textViewQuestionWithCheckBox.setText("" + listQuestion.get(position).getCauHoi());

        holder.checkBoxSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isSelected) {
                listQuestion.get(position).setSelected(isSelected);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) { valueFilter= new ValueFilter();
        }
        return valueFilter;
    }
    private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String filterString = constraint.toString().toLowerCase().trim();


            if (constraint!=null && constraint.length()>0){
                final ArrayList<Question> filterList=new ArrayList<Question>();

                for(int i=0;i<listQuestionFilter.size();i++){
                    if(listQuestionFilter.get(i).getCauHoi().toLowerCase().contains(filterString)) {
                        Question questions = new Question();
                        questions.setSTT(listQuestionFilter.get(i).getSTT());
                        questions.setCauHoi(listQuestionFilter.get(i).getCauHoi());
                        questions.setAnswerTrue(listQuestionFilter.get(i).getAnswerTrue());
                        questions.setAnswerFake1(listQuestionFilter.get(i).getAnswerFake1());
                        questions.setAnswerFake2(listQuestionFilter.get(i).getAnswerFake2());
                        questions.setAnswerFake3(listQuestionFilter.get(i).getAnswerFake3());
                        questions.setSuggest(listQuestionFilter.get(i).getSuggest());
                        questions.setSelected(listQuestionFilter.get(i).isSelected());
                        filterList.add(questions);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                listQuestion = (ArrayList<Question>) results.values;
                notifyDataSetChanged();
            }
            else{
                notifyDataSetInvalidated();
            }
        }
    }
}
