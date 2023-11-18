package com.example.navdraw.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navdraw.R;

import java.util.List;

import javax.security.auth.callback.Callback;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    Context context;
    List<UserRoomModel> data;

    Callback callback;


    public UserAdapter(Context context, List<UserRoomModel> items) {
        this.context = context;
        this.data = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {

        int position = holder.getAdapterPosition();

        if(holder instanceof ViewHolder ){

            UserRoomModel item = data.get(position);
            ((ViewHolder) holder).nameView.setText(item.getName());
            ((ViewHolder) holder).genderView.setText(item.getGender());
            ((ViewHolder) holder).natView.setText(item.getNat());
            //((ViewHolder) holder).cbMarried.setChecked(item.isMarried());
            ((ViewHolder) holder).cbSelected.setCheckMarkDrawable(item.isMarried() ? R.drawable.ic_radio_button_checked_24 : R.drawable.ic_radio_button_unchecked_24);
          // ((ViewHolder) holder).cbMarried.setId(position);
            //((ViewHolder) holder).bind(item);

            /*((ViewHolder) holder).cbMarried.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((ViewHolder) holder).cbMarried.isChecked())
                        data.get(item.getId()).setMarried(true);
                    else
                        data.get(item.getId()).setMarried(false);
                }
            });*/

           /* ((ViewHolder) holder).cbMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item.setMarried(!item.isMarried());
                    notifyItemChanged(position);


                    //int tag = (Integer)((ViewHolder) holder).cbMarried.getTag();
//                    Toast.makeText(context.getApplicationContext(), String.valueOf(((ViewHolder) holder).cbMarried.getId()), Toast.LENGTH_SHORT).show();
//                    if(b)
//                        data.get(((ViewHolder) holder).cbMarried.getId()).setMarried(true);
//                    else
//                        data.get(((ViewHolder) holder).cbMarried.getId()).setMarried(false);
                }
            });
*/

            ((ViewHolder) holder).cbSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setMarried(!item.isMarried());
                    notifyItemChanged(position);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return  data.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView,genderView, natView, userView;
        CheckBox cbMarried;
        CheckedTextView cbSelected;


       /*void bind(UserRoomModel s) {
            // Set the text



            // Listen to changes (i.e. when the user checks or unchecks the box)
            cbMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Invoke the callback
                    if (callback != null) {
                        callback.onCheckedChanged(isChecked);
                        //data.get(s.getId()).setMarried(isChecked);
                    }

                }
            });
        }*/
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name);
            genderView = itemView.findViewById(R.id.gender);
            natView = itemView.findViewById(R.id.nat);
            userView = itemView.findViewById(R.id.User);
           //cbMarried = itemView.findViewById(R.id.marriedCheck);
           cbSelected = itemView.findViewById(R.id.cbSelected);

        }

    }

}
