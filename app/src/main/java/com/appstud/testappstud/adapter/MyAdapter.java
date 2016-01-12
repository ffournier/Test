package com.appstud.testappstud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstud.testappstud.database.MyContact;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.appstud.testappstud.Constant;
import com.appstud.testappstud.R;
import com.appstud.testappstud.network.Contacts;

/**
 * Adapter to display a content
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyRecyclerViewHolder> {

    // declaration
    private Context mContext;
    private List<MyContact> mItems;
    private boolean mIsActionAdd;
    private IListener mListener;

    /**
     * Constructor
     * @param context
     * @param contacts
     * @param listener
     */
    public MyAdapter(Context context, List<Contacts.Contact> contacts, IListener listener) {
        mContext = context;
        mItems = convertContact(contacts);
        mIsActionAdd = true;
        mListener = listener;
    }

    /**
     * Constructor
     * @param context
     * @param contacts
     * @param isActionAdd
     * @param listener
     */
    public MyAdapter(Context context, List<MyContact> contacts, boolean isActionAdd, IListener listener) {
        mContext = context;
        mItems = contacts;
        mIsActionAdd = isActionAdd;
        mListener = listener;
    }

    /**
     * onvert List
     * @param contacts
     * @return
     */
    private ArrayList<MyContact> convertContact(List<Contacts.Contact> contacts) {
        ArrayList<MyContact> result = new ArrayList<>();
        if (contacts != null && contacts.size() >= 1) {
            for (Contacts.Contact contact : contacts) {
                result.add(new MyContact(null, contact.lastname + " " + contact.firstname, contact.status, contact.hisface));
            }
        }
        return result;
    }



    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_contact, null);
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, final int position) {
        final MyContact item = mItems.get(position);
        if (item != null) {
            holder.mTvName.setText(item.getName());
            holder.mTvStatus.setText(item.getStatus());

            Glide.with(mContext).load(Constant.URL_ADDRESS + item.getHisFace()).into(holder.mIvImage);

            if (mIsActionAdd) {
                holder.mIvAction.setImageResource(android.R.drawable.ic_menu_add);
            } else {
                holder.mIvAction.setImageResource(android.R.drawable.ic_menu_delete);
            }
            holder.mIvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(item, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public MyContact getItem(int position) {
        return mItems.get(position);
    }

    public void deleteItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * RecyclerView
     */
    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        // Declaration
        public ImageView mIvImage;
        public TextView mTvName;
        public TextView mTvStatus;
        public ImageView mIvAction;


        /**
         * Constructor
         * @param itemView
         */
        public MyRecyclerViewHolder(View itemView) {
            super(itemView);

            mIvImage = (ImageView) itemView.findViewById(R.id.iv_contact_image);
            mTvName = (TextView) itemView.findViewById(R.id.tv_contact_name);
            mTvStatus = (TextView) itemView.findViewById(R.id.tv_contact_status);
            mIvAction = (ImageView) itemView.findViewById(R.id.iv_contact_action);
        }
    }
}
