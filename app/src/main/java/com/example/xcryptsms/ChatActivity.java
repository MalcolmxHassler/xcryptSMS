package com.example.xcryptsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.AdapterMessagePerso;
import models.MessagePerso;


public class ChatActivity extends AppCompatActivity {

    //view from xml
    Toolbar toolbar;
    //recycler View recup message
    RecyclerView recyclerView;
    ImageView profileIv;
    TextView nameTv, phoneTv;
    EditText messageEt;
    ImageButton sendBtn;
    AdapterMessagePerso messagePerso;
    List<MessagePerso> messagesT;

    String hisUid;
    String myUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // init views
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recyclerView);
        //profileIv = findViewById(R.id.profileIv);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);// ceci remplace le userStatus du tuto
        recupNomNum();
        messagesT = recupMessage(phoneTv.getText().toString(), getApplicationContext());
        messagePerso = new AdapterMessagePerso(getApplicationContext(), messagesT);
        recyclerView.setAdapter(messagePerso);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageEt = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);
        //fonction pour envoi message
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneTv.getText().toString().trim().equals(""))
                    Toast.makeText(getApplicationContext(), "veuillez entrer un numero correcte", Toast.LENGTH_SHORT).show();
                else if(messageEt.getText().equals(""))
                    Toast.makeText(getApplicationContext(), "veuillez entrer un message", Toast.LENGTH_SHORT).show();
                else{
                    SmsManager message = SmsManager.getDefault();
                    message.sendTextMessage(phoneTv.getText().toString(), null, messageEt.getText().toString(), null, null);
                    messageEt.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // hide searchview, as we dont need it here
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_newMessage).setVisible(false);
        menu.findItem(R.id.action_parametres).setVisible(false);
        menu.findItem(R.id.action_langage).setVisible(false);
        return super.onCreateOptionsMenu(menu);

        /* On clicking user from userlist we have passed that user's UID using intent */

       //Intent intent = getIntent();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void recupNomNum(){
        Intent intent = this.getIntent();
        if (intent == null){
            Toast.makeText(getApplicationContext(), "aucune donnee recue", Toast.LENGTH_SHORT).show();
        } else{
            String nom = intent.getExtras().getString("nom");
            String prenom = intent.getExtras().getString("numero");
            System.out.println(intent.getExtras().getString("nom")+"     "+intent.getExtras().getString("numero"));
            nameTv.setText(intent.getExtras().getString("nom"));
            phoneTv.setText(intent.getExtras().getString("numero"));
        }
    }

    public List<MessagePerso> recupMessage(String number, Context context){
        List<MessagePerso> messages = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        String receive_sms = "content://sms/inbox";
        List<String> listM = new ArrayList<>();
        String send_sms = "content://sms/sent";
        Cursor cursor1 = resolver.query(Uri.parse(receive_sms), null, "address = ?", new String[]{number}, null);
        Cursor cursor2 = resolver.query(Uri.parse(send_sms), null, "address = ?", new String[]{number}, null);

        int i= cursor1.getCount(), j=cursor2.getCount();
        if (cursor2 == null)
            Toast.makeText(context, "Impossible de recuperer les messages", Toast.LENGTH_SHORT).show();

        else if (cursor1.moveToLast() && cursor2.moveToLast()){
            do {
                //Ajout des messages envoyes
                if (j>0){
                    String numero1 = cursor2.getString(cursor2.getColumnIndex("address")).replaceAll("-", "");
                    String numero2 = numero1.replaceAll(" ", "");
                    numero2 = numero2.replaceAll("[+]237", "");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String indexDate = cursor2.getString(cursor2.getColumnIndex("date"));
                    Date date = new Date(Long.parseLong(indexDate));
                    indexDate = format.format(date);
                    messages.add(new MessagePerso(cursor2.getString(cursor2.getColumnIndex("body")), indexDate, 0));
                    j--;
                }
                //Ajout des messages recus
                if(i>0){

                    String numero1 = cursor1.getString(cursor1.getColumnIndex("address")).replaceAll("-", "");
                    String numero2 = numero1.replaceAll(" ", "");
                    numero2 = numero2.replaceAll("[+]237", "");
                    //Recuperation des messages recus sur par l'utilisateur
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String indexDate = cursor1.getString(cursor1.getColumnIndex("date"));
                    Date date = new Date(Long.parseLong(indexDate));
                    indexDate = format.format(date);

                    messages.add(new MessagePerso(cursor2.getString(cursor2.getColumnIndex("body")), indexDate, 1));

                    i--;
                }

                cursor1.moveToPrevious();
                cursor2.moveToPrevious();

                //Toast.makeText(getApplicationContext(), "nom: "+ cursor.getString(cursor.getColumnIndexOrThrow("address"))
                //+ " message: "+cursor.getString(cursor.getColumnIndexOrThrow("body")), Toast.LENGTH_LONG);
            }while ( i>0 || j>0);
        }
        return messages;
    }
}
