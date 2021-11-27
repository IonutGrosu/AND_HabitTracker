package io.github.ionutgrosu.and_habittracker.viewModels;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.utils.Utils;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {

    private ArrayList<User> requestingUsers;
    private Activity parentActivity;

    public FriendRequestAdapter(ArrayList<User> requestingUsers, Activity parentActivity){
        this.requestingUsers = requestingUsers;
        this.parentActivity = parentActivity;
    }

    public void setRequestingUsers (ArrayList<User> users){
        requestingUsers = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.friend_request_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.usernameView.setText(requestingUsers.get(position).getUsername());
        Utils.loadProfilePicture(parentActivity, holder.profilePic, requestingUsers.get(position).getUid());
    }

    @Override
    public int getItemCount() {
        return requestingUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic;
        TextView usernameView;
        ImageButton acceptBtn;
        ImageButton declineBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePicFriendRequestContainer);
            usernameView = itemView.findViewById(R.id.usernameFriendRequestContainer);
            acceptBtn = itemView.findViewById(R.id.acceptBtnFriendRequestContainer);
            declineBtn = itemView.findViewById(R.id.declineBtnFriendRequestContainer);
        }
    }
}
