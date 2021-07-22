package com.example.atm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class FuntionAdapter extends RecyclerView.Adapter<FuntionAdapter.FunctionViewHolder> {

    private final String[] functions;
    Context context;
    public FuntionAdapter(Context context){
        this.context=context;   //讓他儲存起來
        functions=context.getResources().getStringArray(R.array.functions);  //再透過context得到functions字串陣列
    }
    @NonNull
    @NotNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) { //讓他取得ViewHolder物件
        View view= LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_1,parent,false);
        return new FunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FuntionAdapter.FunctionViewHolder holder, int position) {  //設定正確的資料長相
        holder.nameText.setText(functions[position]);
    }

    @Override
    public int getItemCount() {  //提供筆數
        return functions.length;
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        public FunctionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameText=itemView.findViewById(android.R.id.text1);
        }
    }
}
