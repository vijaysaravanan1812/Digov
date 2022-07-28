package com.example.digov;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BillFragment extends Fragment {

    Database database;

    //firebase
    DatabaseReference databaseReference ;
    FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_bill , container , false);

        TextView total = myView.findViewById(R.id.total);

        //list view
        ListView item_list = myView.findViewById(R.id.ItemList);

        //Name and phone
        TextView Name = myView.findViewById(R.id.Name);
        TextView Phone = myView.findViewById(R.id.Phone);
        Button Submit = myView.findViewById(R.id.Submit);


        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance("https://digov-28280-default-rtdb.firebaseio.com/").getReference().child("Delivery");

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = new Database(getActivity());

                List<Item> list = new ArrayList<>(database.getEveryone());
                String delim = "\n";
                StringBuilder sb = new StringBuilder();

                int i = 0;
                while (i < list.size() - 1)
                {
                    sb.append(list.get(i));
                    sb.append(delim);
                    i++;
                }
                sb.append(list.get(i));

                String res = sb.toString();
                Delivery delivery = new Delivery(Name.getText().toString() , Phone.getText().toString() , res , database.GetSum());
                databaseReference.push().setValue(delivery.toString());
                database.delete();
                view(item_list , total);
                Toast.makeText(getActivity() , "Bill Added" , Toast.LENGTH_LONG).show();

            }
        });

        view(item_list , total );
        
        //Button to clear and view the data
        Button clear_button = myView.findViewById(R.id.clear_button);
        clear_button.setOnClickListener(v -> {
            database.delete();
            view(item_list , total);
        });

        return myView;
    }


    public void view(ListView command_list , TextView total)
    {
        database = new Database(getActivity());
        ArrayAdapter<Item> ItemAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, database.getEveryone());
        command_list.setAdapter(ItemAdapter);
        String sum = database.GetSum();
        total.setText("Total : " + sum);

    }
}
