package com.example.hp.todo_list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    ListView mList;
    ArrayList<String> mArraylist;
    ArrayAdapter<String> mArrayAdapter;
    EditText mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar=findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("ToDo List");


        mList = (ListView) findViewById(R.id.listview);
        mTask = (EditText) findViewById(R.id.to_do_text);
        mArraylist = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mArraylist);
        mList.setAdapter(mArrayAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder mAlert = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("What do you want to do??")
                        .setCancelable(true)
                        .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, Modify.class);
                                intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA, mArraylist.get(position).toString());
                                intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
                                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
                            }
                        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mArraylist.remove(position);
                                // mArraylist.add(position,"");
                                mArrayAdapter.notifyDataSetChanged();
                                SetData();
                               /* try {
                                    PrintWriter file = new PrintWriter(openFileOutput("ToDo-list.txt", Context.MODE_PRIVATE));
                                    for (String data : mArraylist) {
                                        file.println(data);
                                    }
                                    file.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();

                                }*/
                            }
                        });
                AlertDialog alert = mAlert.create();
                alert.setTitle("Select Option");
                alert.show();


            }
        });


        try {
            Scanner sc = new Scanner(openFileInput("ToDo-list.txt"));
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                mArrayAdapter.add(data);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.reminder_btn) {
            Intent settingIntint = new Intent(MainActivity.this, ReminderActivity.class);
            startActivity(settingIntint);

        }

        return true;
    }
   public void onClick(View view){
       String mMessage= mTask.getText().toString();
       if(mMessage.equals("")){
           Toast.makeText(this,"There is nothig to Enter",Toast.LENGTH_LONG).show();
       }else {
           mArraylist.add(mMessage);
           mArrayAdapter.notifyDataSetChanged();
           SetData();
          /* try{
               PrintWriter file= new PrintWriter(openFileOutput("ToDo-list.txt", Context.MODE_PRIVATE));
               for(String data:mArraylist){
                   file.println(data);
               }
               file.close();
           }catch (FileNotFoundException e){
               e.printStackTrace();

           }*/
           mTask.setText("");
       }

   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Intent_Constants.INTENT_RESULT_CODE){
            String mMessage=data.getStringExtra(Intent_Constants.INTENT_CHANGED_DATA);
            int position=data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);
            mArraylist.remove(position);
            mArraylist.add(position,mMessage);
            mArrayAdapter.notifyDataSetChanged();


        }
    }
    public void  SetData(){
        try{
            PrintWriter file= new PrintWriter(openFileOutput("ToDo-list.txt", Context.MODE_PRIVATE));
            for(String data:mArraylist){
                file.println(data);
            }
            file.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();

        }
    }


   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            PrintWriter file= new PrintWriter(openFileOutput("ToDo-list.txt", Context.MODE_PRIVATE));
            for(String data:mArraylist){
                file.println(data);
            }
            file.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();

        }
        finish();
    }*/
    }





