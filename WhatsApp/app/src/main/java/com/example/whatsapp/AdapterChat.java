package com.example.whatsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterChat extends RecyclerView.Adapter {
    ArrayList<MassageData> massageList;
    Context context;
    String  recieverId;
    String  senderId;

    int Sender=1;
    int Recevier=2;
    public AdapterChat(ArrayList<MassageData> massageList, Context context, String recieverId) {
        this.massageList = massageList;
        this.context = context;
        this.recieverId = recieverId;
    }


    public AdapterChat(ArrayList<MassageData> massageList, Context context) {
        this.massageList = massageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==Sender) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_recycler, parent, false);
            return new SenderHolderChat(view);
        }
        else{
            View view=LayoutInflater.from(context).inflate(R.layout.reciever_recycler,parent,false);
            return new ReciverHolderChat(view);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (massageList.get(position).getUserId().equals(FirebaseAuth.getInstance().getUid())){

            return Sender;
        }
        else{
            return Recevier;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MassageData massageData=massageList.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete For Me").setMessage("Are you sure ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String sender=FirebaseAuth.getInstance().getUid()+recieverId;
                                database.getReference().child("Chats").child(sender)
                                 .child(massageData.getMassageId()).setValue("Massage Deleted");
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });

        if (holder.getClass()==SenderHolderChat.class){
            ( (SenderHolderChat) holder).senderMassage.setText(massageData.getMassage());

        }
        else{
            (  (ReciverHolderChat) holder).recieverMassage.setText(massageData.getMassage());
        }
    }

    @Override
    public int getItemCount() {
        return massageList==null?0:massageList.size();
    }
    public class ReciverHolderChat extends RecyclerView.ViewHolder{

        TextView recieverMassage,reciverTime;
        public ReciverHolderChat(@NonNull View itemView) {
            super(itemView);
            recieverMassage=itemView.findViewById(R.id.receiver_text);
            reciverTime=itemView.findViewById(R.id.receiver_time);

        }
    }

    public class SenderHolderChat extends RecyclerView.ViewHolder{
        TextView senderMassage,senderTime;
        public SenderHolderChat(@NonNull View itemView) {
            super(itemView);
            senderMassage=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.sender_time);

        }
    }
}
