package com.example.xcryptsms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapters.AdapterContact;
import models.ModelContact;


/**
 * A simple {@link Fragment} subclass.
 */

public class ContactFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterContact adapterContact;
    List<ModelContact> mesContacts;



    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapterContact = new AdapterContact(getContext(), getAllUsers(getContext()));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        //init recyclerview
        recyclerView = view.findViewById(R.id.contacts_recyclerView);
        //set its properties

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapterContact);
        //init user list
        mesContacts = new ArrayList<>();

        //get all users
        getAllUsers(getContext());

        return view;
    }

    //to get all contacts
    static List<ModelContact> getAllUsers(Context context) {
        List<ModelContact> mesContacts = new ArrayList<>();
        //get current users
       // ArrayList<ModelContact> arrayList = new ArrayList<ModelContact>();

        //get all contacts
        Cursor cursor_Android_contacts =null;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        //get all contacts
        try{
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
                                                ContactsContract.CommonDataKinds.Phone.NUMBER};
            cursor = contentResolver.query(uri, projection, null, null, null);
            //cursor_Android_contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        }catch (Exception e){
            Log.e("Error on contact",e.getMessage());
        }
        if(cursor.moveToFirst()){
            do {
                mesContacts.add(new ModelContact(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE)), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
            } while (cursor.moveToNext());
        }


    return mesContacts;
    }


//pour la 1e recherche
    static List<ModelContact> searchUsers(String quer ,Context context) {

        List<ModelContact> mesContacts = new ArrayList<>();
        Cursor cursor_Android_contacts =null;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        //get all contacts
        try{
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
            cursor = contentResolver.query(uri, projection, null, null, null);
            //cursor_Android_contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        }catch (Exception e){
            Log.e("Error on contact",e.getMessage());
        }

        if(cursor.moveToFirst()){
            do {
                mesContacts.add(new ModelContact(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE)), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
            } while (cursor.moveToNext());
        }

        return mesContacts;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    /*inflate option menu*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        //inflating menu
        inflater.inflate(R.menu.menu_main, menu);

        //hide le contenue du menu que on ne veut pas afficher
       menu.findItem(R.id.action_encrypt).setVisible(false);
        menu.findItem(R.id.action_appel).setVisible(false);

        //Search view
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        //search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Called when user press search button from keyboard
                //if search query is not empty then search
                if(!TextUtils.isEmpty(query.trim())){
                    //Search text contains text, Search it
                    searchUsers(query, getContext());

                }else{
                    //search text empty, get all users
                    getAllUsers(getContext());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //Called whenever user press any single letter
                //if search query is not empty then search
                if(!TextUtils.isEmpty(query.trim())){
                    //Search text contains text, Search it
                   searchUsers(query, getContext());


                }else{
                    //search text empty, get all users
                    getAllUsers(getContext());
                }
                return false;
            }
        });

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

    static Map<String, String> recupContact(Context context){
        Map<String, String> contact = new ArrayMap<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;
        try{
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
            cursor = contentResolver.query(uri, projection, null, null, null);
            //cursor_Android_contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        }catch (Exception e){
            Log.e("Error on contact",e.getMessage());
        }
        if(cursor.moveToFirst()){
            do {
                contact.put(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[+]237", "").replaceAll(" ", "").trim(), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE)));
            } while (cursor.moveToNext());
        }
        return contact;
    }

}

//Model class for recyclerview