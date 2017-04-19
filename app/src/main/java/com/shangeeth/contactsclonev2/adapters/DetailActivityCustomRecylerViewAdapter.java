package com.shangeeth.contactsclonev2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangeeth.contactsclonev2.R;
import com.shangeeth.contactsclonev2.db.ContactsDataTable;
import com.shangeeth.contactsclonev2.jdo.SecondaryContactsJDO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 18/04/17.
 */

public class DetailActivityCustomRecylerViewAdapter extends RecyclerView.Adapter<DetailActivityCustomRecylerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SecondaryContactsJDO> mContactsJDOs;
    private boolean mIsNewCategory = true;
    private String mLastDisplayedType = "";

    public DetailActivityCustomRecylerViewAdapter(Context mContext, ArrayList<SecondaryContactsJDO> contactsPOJOs) {
        this.mContext = mContext;
        this.mContactsJDOs = contactsPOJOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater lInflater = LayoutInflater.from(parent.getContext());
        View lView = lInflater.inflate(R.layout.rec_view_detail_item, parent, false);
        ViewHolder lViewHolder = new ViewHolder(lView);

        return lViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String lData = mContactsJDOs.get(position).getData();

        if (lData != null && !lData.equals("")) {

             /*
                Displaying buttons based on the type
             */

            String type = mContactsJDOs.get(position).getType();

            if (type.equalsIgnoreCase(ContactsDataTable.Type.PHONE)) {

                holder.mCallIV.setVisibility(View.VISIBLE);
                holder.mMessageIV.setVisibility(View.VISIBLE);

            } else if (type.equalsIgnoreCase(ContactsDataTable.Type.EMAIL)) {

                holder.mEmailIV.setVisibility(View.VISIBLE);

            } else if (type.equalsIgnoreCase(ContactsDataTable.Type.WEBSITE)) {
                holder.mWebsiteIV.setVisibility(View.VISIBLE);
            }


            /*
                Hiding the type if it is the repeated type
             */


            if (mLastDisplayedType.equals(type)) {
                mIsNewCategory = false;
            } else {
                mIsNewCategory = true;
            }
            mLastDisplayedType = type;
            if (mIsNewCategory) {

                if (type.length() > 1) {
                    type = type.substring(0, 1).toUpperCase() + type.substring(1, type.length());
                } else {
                    type = type.toUpperCase();
                }
                holder.mTypeTV.setText(type);

            } else {
                holder.mTypeTV.setVisibility(View.GONE);
            }

            if (type.equalsIgnoreCase(ContactsDataTable.Type.ADDRESS) || type.equalsIgnoreCase("Organization")) {

                try {
                    StringBuilder lBuilder = new StringBuilder();

                    JSONObject lObject = new JSONObject(lData);

                    JSONArray jsonArray = lObject.names();

                    holder.mDataTV.setLines(jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++){
                        lBuilder.append(lObject.getString(jsonArray.get(i).toString())).append("\n");
                    }

                    lData = lBuilder.toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            holder.mDataTV.setText(lData);

        }
    }


    @Override
    public int getItemCount() {
        return mContactsJDOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTypeTV;
        TextView mDataTV;
        ImageView mCallIV;
        ImageView mEmailIV;
        ImageView mMessageIV;
        ImageView mWebsiteIV;

        public ViewHolder(View itemView) {

            super(itemView);

            mTypeTV = (TextView) itemView.findViewById(R.id.type_tv);
            mDataTV = (TextView) itemView.findViewById(R.id.data_tv);
            mCallIV = (ImageView) itemView.findViewById(R.id.call_iv);
            mMessageIV = (ImageView) itemView.findViewById(R.id.message_iv);
            mEmailIV = (ImageView) itemView.findViewById(R.id.email_iv);
            mWebsiteIV = (ImageView) itemView.findViewById(R.id.website_iv);

            mCallIV.setOnClickListener(this);
            mMessageIV.setOnClickListener(this);
            mEmailIV.setOnClickListener(this);
            mWebsiteIV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.call_iv:
                    Intent lCallIntent = new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + mDataTV.getText().toString()));
                    v.getContext().startActivity(lCallIntent);
                    break;
                case R.id.message_iv:
                    Intent lMessageIntent = new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("smsto:" + mDataTV.getText().toString()));
                    v.getContext().startActivity(lMessageIntent);
                    break;
                case R.id.email_iv:
                    Intent lEmailIntent = new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:" + mDataTV.getText().toString()));
                    v.getContext().startActivity(lEmailIntent);
                    break;
                case R.id.website_iv:
                    String data = mDataTV.getText().toString();
                    if (!data.contains("http://")) {
                        data = "http://" + data;
                    }
                    Intent lWebsiteIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(data));
                    v.getContext().startActivity(lWebsiteIntent);
                    break;
            }
        }
    }
}
