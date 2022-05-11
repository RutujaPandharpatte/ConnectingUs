package com.example.connectingus.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectingus.R;
import com.example.connectingus.models.TempMsgModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TempMsgAdapter extends RecyclerView.Adapter
{
    ArrayList<TempMsgModel> tempMsgModels;
    Context context;

    public TempMsgAdapter(ArrayList<TempMsgModel> tempMsgModels, Context context) {
        this.tempMsgModels = tempMsgModels;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==1)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return  new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.receiver,parent,false);
            return  new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(tempMsgModels.get(position).getId()==1)
        {
            return 1;
        }
        else
        {
            return 2;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TempMsgModel tempMsgModel=tempMsgModels.get(position);
        if(tempMsgModel.isSelected())
            holder.itemView.setBackgroundResource(R.color.blue_background);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context.getApplicationContext(),"Long Pressed",Toast.LENGTH_SHORT).show();
                tempMsgModel.setSelected(!tempMsgModel.isSelected());
                //holder.itemView.setBackgroundColor(tempMsgModel.isSelected() ? Color.CYAN : Color.WHITE);
                if(tempMsgModel.isSelected())
                    holder.itemView.setBackgroundResource(R.color.blue_background);
                return false;
            }
        });
        if(holder.getClass()==SenderViewHolder.class)
        {
            ((SenderViewHolder)holder).senderMsg.setText(tempMsgModel.getMessage());
            Date date=new Date(tempMsgModel.getTimestamp());
            SimpleDateFormat formatTime=new SimpleDateFormat("hh:mm a");
            String time=formatTime.format(date);
            ((SenderViewHolder)holder).senderTime.setText(time);
        }
        else
        {
            ((ReceiverViewHolder)holder).receiverMsg.setText(tempMsgModel.getMessage());
            Date date=new Date(tempMsgModel.getTimestamp());
            SimpleDateFormat formatTime=new SimpleDateFormat("hh:mm a");
            String time=formatTime.format(date);
            ((ReceiverViewHolder)holder).receiverTime.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return tempMsgModels.size();
    }

    public  class ReceiverViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView receiverMsg;
        TextView receiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg=itemView.findViewById(R.id.textReceived);
            receiverTime=itemView.findViewById(R.id.textReceivedTime);
        }

        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(context.getApplicationContext(),"Position : "+getAdapterPosition(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public  class SenderViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView senderMsg;
        TextView senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.textSent);
            senderTime=itemView.findViewById(R.id.textSentTime);
        }
        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(context.getApplicationContext(),"Position : "+getAdapterPosition(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
