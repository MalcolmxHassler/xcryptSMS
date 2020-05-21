package com.example.xcryptsms;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.MessagePerso;
import models.ModelContact;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    private  static MessageFragment inst;
    ArrayList<String> smsMessageList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    static Map<String, List<MessagePerso>> messagesT;

    public static MessageFragment instance() {return  inst;}

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    // @Override
    //  public void onCreate(@Nullable Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_message);
    //    smsListView = (ListView) smsListView.findViewById(R.id.SMSList);

    //  }



    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        smsListView = (ListView) view.findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, smsMessageList);
        smsListView.setAdapter(arrayAdapter);
        List<ModelContact> contact = ContactFragment.getAllUsers(getContext());
        messagesT = new ArrayMap<>();

        //smsListview.setOnclickListener(this)

        //Add SMS Read Permission At Runtime
        //TODO : If permission is not GRANTED
        if(ContextCompat.checkSelfPermission(getActivity().getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED){
            //TODO : If permission GRANTED
            refreshSmsInbox();
        }else{
            //TODO : Then set the permission
            final  int  REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_CONTACTS", "android.permission.READ_SMS", "android.permission.SEND_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        return  view;
    }

    public void refreshSmsInbox(){
        List<String> listAdress = new ArrayList<>();
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"),null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("Body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if(indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        Map<String, String> contact = ContactFragment.recupContact(getContext());

        arrayAdapter.clear();
        do{

            String str;
            String nom = contact.get(smsInboxCursor.getString(indexAddress).replaceAll("[+]237", "").replaceAll(" ", "").trim());

            if(nom == null)
                nom = smsInboxCursor.getString(indexAddress);
            if (!listAdress.contains(smsInboxCursor.getString(indexAddress))) {

                if (smsInboxCursor.getString(indexBody).length() < 10)
                    str = "De : " + nom + "\n" + smsInboxCursor.getString(indexBody)+ "\n";
                else if (smsInboxCursor.getString(indexBody).length() >= 10 && smsInboxCursor.getString(indexBody).length() < 20)
                    str = "De : " + nom + "\n" + smsInboxCursor.getString(indexBody).substring(0, 10)+ "\n";
                else
                    str = "De : " + nom + "\n" + smsInboxCursor.getString(indexBody).substring(0, 20)+ "\n";
                arrayAdapter.add(str);
            }
            listAdress.add(smsInboxCursor.getString(indexAddress));

        }while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage){
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id){
        try {
            String[] smsMessages = smsMessageList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for(int i=1; i<smsMessages.length; i++ ){
                smsMessage += smsMessages[i];
            }
            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(getContext(),smsMessageStr, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*inflate option menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //inflating
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*handle menu item clicks*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //get item id
        //for parameters, language and New Discussion
        int id = item.getItemId();
        if(id == R.id.action_newMessage){

        }
        return super.onOptionsItemSelected(item);
    }



}
