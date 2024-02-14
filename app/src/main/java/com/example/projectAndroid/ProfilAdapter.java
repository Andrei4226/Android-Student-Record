package com.example.projectAndroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectAndroid.data.Profil;
import com.example.seminar_5.R;

import java.util.List;

public class ProfilAdapter extends ArrayAdapter<Profil>
{
    public ProfilAdapter(@NonNull Context context, @NonNull List<Profil> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            // deserialization
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_profil,parent,false);

        }
        ImageView ivProfil = convertView.findViewById(R.id.iv_profil);
        TextView tvName = convertView.findViewById(R.id.tv_profil_name);
        TextView tvEmail = convertView.findViewById(R.id.tv_profil_email);
        TextView tvAge = convertView.findViewById(R.id.tv_profil_age);

        Profil profil = getItem(position);

        if(profil.isIntegrally())
        {
            ivProfil.setImageResource(R.drawable.user_admin);
            convertView.setBackgroundColor(Color.CYAN);
            ivProfil.setBackgroundColor(Color.GREEN);
        }
        else {
            ivProfil.setImageResource(R.drawable.user_normal);
            convertView.setBackgroundColor(Color.YELLOW);
            ivProfil.setBackgroundColor(Color.MAGENTA);
        }


        tvName.setText(profil.getName());
        tvEmail.setText(profil.getEmail());
        tvAge.setText(String.valueOf(profil.getAge()));

        return convertView;
    }
}
