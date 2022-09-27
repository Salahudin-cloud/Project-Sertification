package com.example.sertifikasi.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sertifikasi.R;
import com.example.sertifikasi.database.DataConverter;
import com.example.sertifikasi.database.Entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewAdapter> {

    private List<User> list;
    private Context context ;
    private RecyclerViewInterface recyclerViewInterface;

    public UserAdapter(Context context , List<User> list){
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list ,parent ,
                false );
        return  new ViewAdapter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {
            holder.profil.setImageBitmap(DataConverter.byteToImage(list.get(position).image));
            holder.nama.setText("nama : " + list.get(position).name);
            holder.alamat.setText("Alamat : " + list.get(position).alamat);
            holder.noHp.setText("No Hp : " + list.get(position).noHp);
            holder.username.setText("Username : " +list.get(position).username);
            holder.password.setText("Password: " +list.get(position).password);
            holder.kelamin.setText("Jenis Kelamin : " +list.get(position).jenisKelamin);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface RecyclerViewInterface{
        void onClick(int position);
    }

    public void setRecyclerViewInterface(RecyclerViewInterface recyclerViewInterface){
        this.recyclerViewInterface = recyclerViewInterface;
    }


    class ViewAdapter  extends RecyclerView.ViewHolder {

        TextView nama, alamat , noHp , username , password, kelamin;
        ImageView profil ;
        ImageButton edit , delete;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.labelNameList);
            alamat = itemView.findViewById(R.id.labelAlamatList);
            noHp = itemView.findViewById(R.id.labelHpList);
            username = itemView.findViewById(R.id.labelUsernameList);
            password = itemView.findViewById(R.id.labelPasswordList);
            kelamin = itemView.findViewById(R.id.labelKelaminList);

            profil = itemView.findViewById(R.id.imageProfil);
            delete = itemView.findViewById(R.id.deleteBtn);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null ) {
                        recyclerViewInterface.onClick(getLayoutPosition());
                    }
                }
            });

        }
    }
}
