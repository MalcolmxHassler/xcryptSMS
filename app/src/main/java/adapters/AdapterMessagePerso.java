package adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xcryptsms.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import models.MessagePerso;
import models.ModelContact;

public class AdapterMessagePerso extends RecyclerView.Adapter<AdapterMessagePerso.ViewHolder> {
    List<MessagePerso> mesMessage;
    Context context;
    //les modifications pour afficher les vues
    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;

    //mesMessage === chatList dans le tuto

    //Pour afficher les vues de mon application
    class MyHolder extends RecyclerView.ViewHolder{
        //views : les vues de nos conversations
        TextView messageTv, timeTv;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //init views : Pour initialiser les vues
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);

        }
    }


    public AdapterMessagePerso(Context context, List<MessagePerso> mesMessage) {
        this.mesMessage = mesMessage;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);
        //return new AdapterMessagePerso.ViewHolder(view);
        /*if (viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_moi, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_autre, parent, false);
        }*/
        //return new AdapterMessagePerso.ViewHolder(view);

        //La modification parraport au tutoriel

        //inflate layouts : row_chat_left.xml for receiver, row_chat_right.xml for sender

        // chat_moi == row_chat_right  // chat_autre == row_chat_left
        if(viewType == MSG_TYPE_RIGHT) {
                View view = LayoutInflater.from(context).inflate(R.layout.chat_moi, parent, false);
                return new ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_autre, parent, false);
            return new ViewHolder(view);
        }
           // return null;
        //fin de la modification parraport au tutoriel


    }

    //Jai aussi modifier ici
    @Override
    public void onBindViewHolder(@NonNull AdapterMessagePerso.ViewHolder holder, int position) {
        holder.message.setText(mesMessage.get(position).getMessage());
        holder.date.setText(mesMessage.get(position).getDate());
       // holder.image.setImageResource(R.drawable.ic_default_img);

        //debut de la modification
       // String message = mesMessage.get(position).getMessage();
        //String timestamp = mesMessage.get(position).getDate();
        //convert time stamp to dd/mm/yyyy hh:mm am/pm
       // Calendar cal = Calendar.getInstance(Locale.FRENCH);
        //cal.setTimeInMillis(Long.parseLong(timestamp));
        //String dateTime = DateFormat.format("dd/MM/YYYY hh:mm aa", cal).toString();
        //set Data
        //holder.message.setText(message);
        //holder.date.setText(mesMessage.get(position).getDate());
        //holder.date.setText(dateTime);


    }

    @Override
    public int getItemCount() {
      //  return 0; //Jai aussi modifier ici
        return mesMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView message;
        TextView date;
        RelativeLayout rel;
        RelativeLayout globe;
        ImageView image;
        Button btn_sup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.imageProfile);
            message = itemView.findViewById(R.id.messageTv);
            date = itemView.findViewById(R.id.timeTv);
            //rel = itemView.findViewById(R.id.globe_message);
           // globe = (RelativeLayout) itemView.findViewById(R.id.globe_message);
            //btn_sup = (Button) itemView.findViewById(R.id.btn_sup1);
        }

        @Override
        public void onClick(View v) {

        }
    }

    //jai aussi modifier ici
    @Override
    public int getItemViewType(int position) {
       // if (mesMessage.get(position).getDirection() == 1) return 1;
        //else return 0;
        if(mesMessage.get(position).getDirection()==1) return MSG_TYPE_RIGHT;
        else {
            return MSG_TYPE_LEFT;
        }
    }
}

