package com.karolina.androidu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShoppingListActivity extends AppCompatActivity {

    @BindView(R.id.itemName_et)
    EditText itemName;
    @BindView(R.id.itemList)
    ListView itemList;
    @BindView(R.id.itemSpinner)
    Spinner itemSpinner;
    private List<String> spinnerItems;   //do łączenia z ListView poprzez adapter
    private List<String> listItems;           //lista z checkbox'ami
    private Set<String> spinnerSetItems;
    private Set<String> listSetItems;
    private static final String List_Item_Key = "List item key";
    private static final String Spiner_Items_key = "List spinner key";
    private static final String Shopping_List_Key = "Shopping list key";
    private ShoppingListArrayAdapter listAdapter;
    private ArrayAdapter<String> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/
       /**************    jak klikamy dodaj    **************/
        fab.setOnClickListener((view)-> {
            if (itemName.getText() != null  &&  ! itemName.getText().toString().trim().isEmpty() && listSetItems.add(itemName.getText().toString())){
                listItems.add(itemName.getText().toString());
                itemName.setText("");
                //itemName.setText(""+spinnerItems.get(position));
                listAdapter.notifyDataSetChanged();  //na adapterze wywołujemy metodę powiadamiającą, że zmieniły się dane
                //Toast.makeText(this,"click on a bag to retrive",Toast.LENGTH_LONG).show();
               // itemList.invalidate();
            }
        });

        SharedPreferences sp = getSharedPreferences(Shopping_List_Key, MODE_PRIVATE);
        /************ odczytanie list zakupów ***********************************/
        listSetItems = sp.getStringSet(List_Item_Key, new ArraySet<String>());
        spinnerSetItems = sp.getStringSet(Spiner_Items_key, new ArraySet<String>());
        listItems = new ArrayList<>(listSetItems);
        spinnerItems = new ArrayList<>(spinnerSetItems);
        /*       ************    */
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, spinnerItems);
        itemSpinner.setAdapter(spinnerAdapter);
        Toast.makeText(this,"click on a bag to retrive",Toast.LENGTH_LONG).show();
        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if ( ! spinnerItems.get(position).equals("")) {
                    itemName.setText(""+spinnerItems.get(position));
                    spinnerItems.remove(position);
                    spinnerAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        listAdapter =new ShoppingListArrayAdapter<>(this,R.layout.row_shopping_list ,listItems, spinnerAdapter, spinnerItems, itemSpinner);
                                                             /*android.R.layout.simple_list_item_1*/  //  gdybyśmy użyli SET'a listItem.toArray(new String[0]toArray(new String[0] casting do stringa i potem do tablicy - potrzebujemy tablice stringów
        itemList.setAdapter(listAdapter);  //ustawiamy adapter na nasza liste



    }

    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = getSharedPreferences(Shopping_List_Key, MODE_PRIVATE).edit();
        listSetItems = new ArraySet<>(listItems);      //zapisujemy liste
        spinnerSetItems = new ArraySet<>(spinnerItems);
        /***************   zapisanie listy zakupów ****************/
        editor.putStringSet(List_Item_Key, listSetItems);
        editor.putStringSet(Spiner_Items_key, spinnerSetItems);
        editor.commit();
        super.onPause();
    }
    /**
     *
     if (message != null){
     Toast.makeText(this, message, Toast.LENGTH_LONG).show();

     }
     **/

}
