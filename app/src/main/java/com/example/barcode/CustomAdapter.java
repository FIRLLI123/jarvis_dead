package com.example.barcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Object> items;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    public CustomAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof String ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);

        if (convertView == null) {
            if (viewType == TYPE_HEADER) {
                convertView = LayoutInflater.from(context).inflate(R.layout.header_layout, parent, false);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            }
        }

        if (viewType == TYPE_HEADER) {
            TextView headerText = convertView.findViewById(R.id.header_name);
            headerText.setText((String) items.get(position));
        } else {
            TextView namaitemText = convertView.findViewById(R.id.namaitemlistsumstok2);
            TextView sisaStokText = convertView.findViewById(R.id.qtylistsumstok2);
            HashMap<String, String> item = (HashMap<String, String>) items.get(position);
            namaitemText.setText(item.get("namaitem"));
            sisaStokText.setText(item.get("sisa_stok"));
        }

        return convertView;
    }
}
