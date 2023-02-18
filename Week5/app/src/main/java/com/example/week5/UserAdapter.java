package com.example.week5;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    public UserAdapter(@NonNull Context context, int resource, List<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_row_list, parent, false);
            Viewholder vh = new Viewholder();
            vh.textViewname = convertView.findViewById(R.id.textViewname);
            vh.textViewage = convertView.findViewById(R.id.textViewage);
            convertView.setTag(vh);
        }
        User user = getItem(position);
        Viewholder vh = (Viewholder) convertView.getTag();
        vh.textViewname.setText(user.name);
        Log.d("demo", "getView: " + user.age);
      //  vh.textViewage.setText(user.age);
        return  convertView;
    }

    private static class Viewholder{
        TextView textViewname ;
        TextView textViewage ;
    }

}
