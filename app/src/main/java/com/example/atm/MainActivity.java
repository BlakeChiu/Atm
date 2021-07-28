package com.example.atm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atm.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOGIN =100;
    private static final String TAG =MainActivity.class.getSimpleName();
    boolean logon=false;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private List<Function> funtions;

//    String[] functions=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!logon){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_LOGIN);
//            startActivity(intent);
        }

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Recycler
        setupFunctions();


        RecyclerView recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true); //設定固定大小
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));  //告訴他未來的每一列要怎麼展示
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));  //告訴她未來以格狀顯示並且欄數3
        //Adapter
//        FuntionAdapter adapter=new FuntionAdapter(this);  //這樣一來this這個物件利用建構子方式傳到Adaper類別當中
        IconAdapter adapter=new IconAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setupFunctions() {
        funtions = new ArrayList<>();
        String[] funcs=getResources().getStringArray(R.array.functions);
        funtions.add(new Function(funcs[0],R.drawable.func_transaction));
        funtions.add(new Function(funcs[1],R.drawable.func_balance));
        funtions.add(new Function(funcs[2],R.drawable.func_finance));
        funtions.add(new Function(funcs[3],R.drawable.func_contacts));
        funtions.add(new Function(funcs[4],R.drawable.func_exit));
    }

    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder> {

        @NonNull
        @NotNull
        @Override
        public IconHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {  //這時他應該回傳一個內部沒有資料的ViewHolder物件
            View view= getLayoutInflater().inflate(R.layout.item_icon,parent,false);
            return new IconHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull MainActivity.IconAdapter.IconHolder holder, int position) { //當畫面打算顯示第幾筆資料時
            Function function=funtions.get(position);
            holder.nameText.setText(function.getName());
            holder.iconImage.setImageResource(function.getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {  //當按下5個功能其中一個時會自動呼叫ocClick
                @Override
                public void onClick(View v) {
                    itemClicked(function);
                }
            });
        }

        @Override
        public int getItemCount() {   //當Recyler元件還沒顯示時這會先被呼叫，目的要知道他身上有幾個資料(1)
            return funtions.size();
        }

        public class IconHolder extends RecyclerView.ViewHolder{
            ImageView iconImage;
            TextView nameText;
            public IconHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                iconImage=itemView.findViewById(R.id.item_icon);
                nameText=itemView.findViewById(R.id.item_name);
            }
        }
    }

    private void itemClicked(Function function) {
        Log.d(TAG, "itemClicked: "+function.getName());
        switch (function.getIcon()) {
            case R.drawable.func_transaction:
                break;
            case R.drawable.func_balance:
                break;
            case R.drawable.func_finance:
                Intent finance=new Intent(this,FinanceActivity.class);
                startActivity(finance);
                break;
            case R.drawable.func_contacts:
                Intent contacts=new Intent(this,ContactActivity.class);
                startActivity(contacts);
                break;
            case R.drawable.func_exit:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_LOGIN){
            if(resultCode!=RESULT_OK){
                finish();
            }
        }
    }

    /*ActivityResultLauncher<Intent> launcher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK){
                        Log.d(TAG, "RESULT OK");
                    }else
                        finish();
                }
            });*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}