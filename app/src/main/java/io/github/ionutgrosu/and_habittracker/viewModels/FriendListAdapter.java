package io.github.ionutgrosu.and_habittracker.viewModels;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.ionutgrosu.and_habittracker.R;
import io.github.ionutgrosu.and_habittracker.models.User;
import io.github.ionutgrosu.and_habittracker.utils.Utils;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private ArrayList<User> friends;
    private Activity parentActivity;

    private FriendListManager friendListManager;

    public FriendListAdapter(ArrayList<User> friends, Activity parentActivity) {
        this.friends = friends;
        this.parentActivity = parentActivity;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setFriendListManager(FriendListManager friendListManager) {
        this.friendListManager = friendListManager;
    }

    @NonNull
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.friend_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.ViewHolder holder, int position) {
        holder.usernameView.setText(friends.get(position).getUsername());
        Utils.loadProfilePicture(parentActivity, holder.profilePic, friends.get(position).getUid());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic;
        TextView usernameView;
        Button removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePicFriendListContainer);
            usernameView = itemView.findViewById(R.id.usernameFriendListContainer);
            removeBtn = itemView.findViewById(R.id.removeBtnFriendListContainer);

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    friendListManager.removeFriend(friends.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface FriendListManager {
        void removeFriend(User user);
    }
}
