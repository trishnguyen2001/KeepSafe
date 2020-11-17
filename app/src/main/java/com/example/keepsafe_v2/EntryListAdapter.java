package com.example.keepsafe_v2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class EntryListAdapter extends ArrayAdapter<Node> {

    private Context mContext;
    private int mResource;

    public EntryListAdapter(@NonNull Context context, int resource, @NonNull Node[] objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = getItem(position).title,
                user = getItem(position).user,
                pw = getItem(position).pw;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitle = convertView.findViewById(R.id.textView1),
                tvUser = convertView.findViewById(R.id.textView2),
                tvPw = convertView.findViewById(R.id.textView3);

        tvTitle.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        tvTitle.setTextSize(20);
        tvTitle.setText("    " + title);

        tvUser.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        tvUser.setTextSize(15);
        tvUser.setText("user: [" + user + "]");

        tvPw.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        tvPw.setTextSize(15);
        tvPw.setText("pw:    [" + pw + "]");

        return convertView;
    }
}
