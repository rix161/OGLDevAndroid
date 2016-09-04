package com.gles.rohit.ogldevandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gles.rohit.Tutorial1.Tutorial1;
import com.gles.rohit.Tutorial10.Tutorial10;
import com.gles.rohit.Tutorial11.Tutorial11;
import com.gles.rohit.Tutorial2.Tutorial2;
import com.gles.rohit.Tutorial3.Tutorial3;
import com.gles.rohit.Tutorial4.Tutorial4;
import com.gles.rohit.Tutorial5.Tutorial5;
import com.gles.rohit.Tutorial6.Tutorial6;
import com.gles.rohit.Tutorial7.Tutorial7;
import com.gles.rohit.Tutorial8.Tutorial8;
import com.gles.rohit.Tutorial9.Tutorial9;

import java.util.zip.Inflater;

/**
 * Created by Rohith on 03-09-2016.
 */
public class OglDevProgramList extends ArrayAdapter<String> {
    Context mContext;
    LayoutInflater mInflator;
    String []mResource;
    public OglDevProgramList(Context context, String[] resource) {
        super(context,R.layout.ogldev_program_list,resource);
        mContext = context;
        mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        View mView = mInflator.inflate(R.layout.ogldev_program_list,parent,false);
        TextView pgmTitle = (TextView) mView.findViewById(R.id.textviewPrmTitle);
        TextView pgmDesc = (TextView) mView.findViewById(R.id.textviewpgmDesc);
        String strings[] = mResource[pos].split(":");
        pgmTitle.setText(strings[0]);
        pgmDesc.setText(strings[1]);
        return mView;
    }


    public void launchActivity(int position) {
        Intent intent;
        if(position>mResource.length)
            return;

        switch (position){
            case 0:
                intent = new Intent(mContext, Tutorial1.class);
                mContext.startActivity(intent);
                break;

            case 1:
                intent = new Intent(mContext, Tutorial2.class);
                mContext.startActivity(intent);
                break;

            case 2:
                intent = new Intent(mContext, Tutorial3.class);
                mContext.startActivity(intent);
                break;

            case 3:
                intent = new Intent(mContext, Tutorial4.class);
                mContext.startActivity(intent);
                break;
            case 4:
                intent = new Intent(mContext, Tutorial5.class);
                mContext.startActivity(intent);
                break;
            case 5:
                intent = new Intent(mContext, Tutorial6.class);
                mContext.startActivity(intent);
                break;
            case 6:
                intent = new Intent(mContext, Tutorial7.class);
                mContext.startActivity(intent);
                break;

            case 7:
                intent = new Intent(mContext, Tutorial8.class);
                mContext.startActivity(intent);
                break;

            case 8:
                intent = new Intent(mContext, Tutorial9.class);
                mContext.startActivity(intent);
                break;

            case 9:
                intent = new Intent(mContext, Tutorial10.class);
                mContext.startActivity(intent);
                break;

            case 10:
                intent = new Intent(mContext, Tutorial11.class);
                mContext.startActivity(intent);
                break;

            default:
                Toast.makeText(mContext,"Invalid Selection",Toast.LENGTH_SHORT).show();
        }
    }
}
