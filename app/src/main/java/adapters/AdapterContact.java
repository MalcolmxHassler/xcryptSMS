package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import models.ModelContact;

import com.example.xcryptsms.ChatActivity;
import com.example.xcryptsms.R;

import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.MyHolder> {

    Context context;
    List<ModelContact> mesContacts;

    //constructor
    public AdapterContact(Context context, List<ModelContact> userList) {
        this.context = context;
        this.mesContacts = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate  layout(row_contact.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);
        return new MyHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //get data
        final String hisUID = mesContacts.get(position).getUid();
        String userImage = mesContacts.get(position).getImage();
        String userName = mesContacts.get(position).getName();
        final String userPhone = mesContacts.get(position).getPhone();

        //set data
        holder.mNameTv.setText(userName);
        holder.mPhoneTv.setText(userPhone);
        try{
            //Picasso.get().load(userImage)
                  //  .placeholder(R.drawable.ic_default_img)
                   // .into(holder.mAvatarTv);

        }catch (Exception e){

        }

        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("hisUid", hisUID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesContacts.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{

        TextView mNameTv, mPhoneTv;
        LinearLayout cont;

         public MyHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            //init views
             cont = itemView.findViewById(R.id.monconteneur);
             mNameTv = itemView.findViewById(R.id.nameTv);
             mPhoneTv = itemView.findViewById(R.id.numberTv);

             cont.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(context, ChatActivity.class);
                     intent.putExtra("nom", mNameTv.getText());
                     intent.putExtra("numero", mPhoneTv.getText());
                     context.startActivity(intent);
                 }
             });
        }
    }
}
