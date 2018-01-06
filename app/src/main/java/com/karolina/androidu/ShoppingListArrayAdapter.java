package com.karolina.androidu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Karolina on 02/01/2018.
 */

public class ShoppingListArrayAdapter<String> extends ArrayAdapter {
    private int resource;
    private List<String> objects;
    private Context context;
    ArrayAdapter<String> spinnerAdapter;
    List<String> spinnerItems;
    Spinner spiner;
    public ShoppingListArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);   //metoda super musi być pierwsza
        this.resource = resource;
        this.context = context;
        this.objects = objects;
    }

    public ShoppingListArrayAdapter(@NonNull Context context, int resource, @NonNull List objects, ArrayAdapter<String> spinnerAdapter, List<String> spinnerItems, Spinner spiner) {
        this(context, resource, objects);
        this.spinnerAdapter = spinnerAdapter;
        this.spinnerItems = spinnerItems;
        this.spiner = spiner;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflaterL = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflaterL.inflate(R.layout.row_shopping_list, parent, false);
        TextView name = rowView.findViewById(R.id.tv_name);
        name.setText(""+ objects.get(position));
        CheckBox selected = rowView.findViewById(R.id.cb_selected);
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    spinnerItems.add(objects.get(position));
                    spinnerItems.remove("");

                    spinnerItems.add((String) "");
                    spiner.setSelection(spinnerItems.indexOf(""));
                    spinnerAdapter.notifyDataSetChanged();  //na adapterze wywołujemy metodę powiadamiającą, że zmieniły się dane
                    objects.remove(position);
                    ShoppingListArrayAdapter.super.notifyDataSetChanged();
            }
        });
        return rowView;
    }
}

