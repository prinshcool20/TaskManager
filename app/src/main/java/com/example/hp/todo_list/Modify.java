package com.example.hp.todo_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Modify extends AppCompatActivity {
    String mMessage;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Intent intent=getIntent();
        mMessage=intent.getStringExtra(Intent_Constants.INTENT_MESSAGE_DATA);
        position=intent.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);

        EditText message=(EditText) findViewById(R.id.modifyText);
        message.setText(mMessage);
    }
    public void saveButtonClicked(View view){
        AlertDialog.Builder mAlert=new AlertDialog.Builder(Modify.this);
        mAlert.setMessage("Do you Really want to Modify??").setCancelable(true)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newMessage=((EditText)findViewById(R.id.modifyText)).getText().toString();
                        Intent intent= new Intent();
                        intent.putExtra(Intent_Constants.INTENT_CHANGED_DATA,newMessage);
                        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION,position);
                        setResult(Intent_Constants.INTENT_RESULT_CODE,intent);
                        finish();
                    }
                })
                .setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(Modify.this,MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    }
                });
        AlertDialog alert=mAlert.create();
        alert.setTitle("Make Change");
        alert.show();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Modify.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();


    }
}

