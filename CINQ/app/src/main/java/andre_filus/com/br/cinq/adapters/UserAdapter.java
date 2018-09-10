package andre_filus.com.br.cinq.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.databinding.ItemUserBinding;
import andre_filus.com.br.cinq.models.User;

/**
 * Created by André Filus on 08/09/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    ArrayList<User> mUsersList = new ArrayList<>();
    UserClickListener mListener;

    public UserAdapter(ArrayList<User> mUsersList, UserClickListener mListener) {
        this.mUsersList = mUsersList;
        this.mListener = mListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent, false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User userItem = mUsersList.get(position);
        holder.mViewHolderBinding.txtName.setText(userItem.name);
        holder.mViewHolderBinding.txtEmail.setText(userItem.email);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ItemUserBinding mViewHolderBinding;
        public UserViewHolder(View itemView) {
            super(itemView);
            mViewHolderBinding = DataBindingUtil.bind(itemView);
            mViewHolderBinding.imgEdit.setOnClickListener(v -> mListener.onUserEditItemClick(mUsersList.get(getAdapterPosition())));
            mViewHolderBinding.imgDelete.setOnClickListener(v -> mListener.onUserDeleteItemClick(mUsersList.get(getAdapterPosition())));
        }
    }

    public void updateList(ArrayList<User> filtered){
        mUsersList = filtered;
        notifyDataSetChanged();
    }

    public interface UserClickListener {
        void onUserEditItemClick(User user);
        void onUserDeleteItemClick(User user);
    }

}
