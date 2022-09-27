package com.example.sertifikasi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sertifikasi.R;
import com.example.sertifikasi.database.DataConverter;
import com.example.sertifikasi.database.Entity.User;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewAdapterClient> {

    private List<User> listClient;
    private Context context;
    private ClickBtn click;


    public ClientAdapter(Context context, List<User> listClient) {
        this.context = context;
        this.listClient = listClient;
    }

    @NonNull
    @Override
    public ClientAdapter.ViewAdapterClient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list2 ,parent ,
                false );
        return  new ViewAdapterClient(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ViewAdapterClient holder, int position) {
        holder.profilClient.setImageBitmap(DataConverter.byteToImage(listClient.get(position).image));
        holder.namaClient.setText("nama : " + listClient.get(position).name);
        holder.alamatClient.setText("Alamat : " + listClient.get(position).alamat);
        holder.noHpClient.setText("No Hp : " + listClient.get(position).noHp);
        holder.usernameClient.setText("Username : " +listClient.get(position).username);
        holder.passwordClient.setText("Password: " +listClient.get(position).password);
        holder.kelaminClient.setText("Jenis Kelamin : " +listClient.get(position).jenisKelamin);
    }

    @Override
    public int getItemCount() {
        return listClient.size();
    }

    public interface ClickBtn {
        void onClick(int position);
    }

    public void setRecyclerViewInterface(ClickBtn click) {
        this.click = click;
    }

    static class ViewAdapterClient extends RecyclerView.ViewHolder {

        TextView namaClient, alamatClient , noHpClient , usernameClient , passwordClient, kelaminClient;
        ImageView profilClient ;


        public ViewAdapterClient(@NonNull View itemView) {
            super(itemView);

            namaClient = itemView.findViewById(R.id.labelNameListClient);
            alamatClient = itemView.findViewById(R.id.labelAlamatListClient);
            noHpClient = itemView.findViewById(R.id.labelHpListClient);
            usernameClient = itemView.findViewById(R.id.labelUsernameListClient);
            passwordClient = itemView.findViewById(R.id.labelPasswordListClient);
            kelaminClient = itemView.findViewById(R.id.labelKelaminListClient);

            profilClient = itemView.findViewById(R.id.imageProfilClient);


        }
    }

}
