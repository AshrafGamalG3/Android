package com.example.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {
    private ArrayList<Users> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage=FirebaseStorage.getInstance();
    Context context;
    Uri uri;
    final StorageReference reference=storage.getReference();

    public AdapterUser(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ArrayList<Users> getList() {
        return list;
    }

    public void setList(ArrayList<Users> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Users users=list.get(position);
        holder.UserName.setText(users.getUsername());
        Glide.with(holder.image.getContext()).load(users.getProfile1()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatsActivity.class);
                intent.putExtra("userId",users.getUserId());
                intent.putExtra("userName",users.getUsername());
                intent.putExtra("image",users.getProfile1());


                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView UserName;
        ImageView image;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
          UserName=itemView.findViewById(R.id.user_name);
          image=itemView.findViewById(R.id.image_user);

        }
    }
}
