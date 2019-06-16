package com.example.jakmalltestproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class MyListAdapter extends ArrayAdapter<String> {
    private int layout;
    private ArrayList<String> data;
    private ArrayList<Integer> numbers;
    public MyListAdapter(Context context, int resource, ArrayList<Integer> numberData, ArrayList<String> stringData) {
        super(context, resource);
        data = stringData;
        numbers = numberData;
        layout = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = new ViewHolder();
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent,false);

            mainViewHolder.listNumber = convertView.findViewById(R.id.list_item_number);
            mainViewHolder.listText = convertView.findViewById(R.id.list_item_text);
            mainViewHolder.listButton = convertView.findViewById(R.id.list_item_button);
            mainViewHolder.listButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rollUpRow(position);
                }
            });
            convertView.setTag(mainViewHolder);
        } else {
            mainViewHolder = (ViewHolder) convertView.getTag();
        }
        mainViewHolder.listText.setText(data.get(position));
        mainViewHolder.listNumber.setText("Joke "+numbers.get(position));
        if(position == 0){
            mainViewHolder.listButton.setText("I Am Top");
        }

        return convertView;
    }

    private void rollUpRow(int position){
        for (int i = position; i > 0; --i){
            String temp = data.get(i);
            data.set(i, data.get(i-1));
            data.set(i-1, temp);

            int tempNum = numbers.get(i);
            numbers.set(i, numbers.get(i-1));
            numbers.set(i-1, tempNum);

        }
        this.notifyDataSetChanged();
    }
}
