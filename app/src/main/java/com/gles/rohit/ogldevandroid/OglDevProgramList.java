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
import com.gles.rohit.Tutorial12.Tutorial12;
import com.gles.rohit.Tutorial13.Tutorial13;
import com.gles.rohit.Tutorial14.Tutorial14;
import com.gles.rohit.Tutorial15.Tutorial15;
import com.gles.rohit.Tutorial16.Tutorial16;
import com.gles.rohit.Tutorial16B.Tutorial16B;
import com.gles.rohit.Tutorial17.Tutorial17;
import com.gles.rohit.Tutorial18.Tutorial18;
import com.gles.rohit.Tutorial19.Tutorial19;
import com.gles.rohit.Tutorial2.Tutorial2;
import com.gles.rohit.Tutorial20.Tutorial20;
import com.gles.rohit.Tutorial21.Tutorial21;
import com.gles.rohit.Tutorial22.Tutorial22;
import com.gles.rohit.Tutorial23.Tutorial23;
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

            case 11:
                intent = new Intent(mContext, Tutorial12.class);
                mContext.startActivity(intent);
                break;

            case 12:
                intent = new Intent(mContext, Tutorial13.class);
                mContext.startActivity(intent);
                break;

            case 13:
                intent = new Intent(mContext, Tutorial14.class);
                mContext.startActivity(intent);
                break;

            case 14:
                intent = new Intent(mContext, Tutorial15.class);
                mContext.startActivity(intent);
                break;

            case 15:
                intent = new Intent(mContext, Tutorial16.class);
                mContext.startActivity(intent);
                break;

            case 16:
                intent = new Intent(mContext, Tutorial16B.class);
                mContext.startActivity(intent);
                break;

            case 17:
                intent = new Intent(mContext, Tutorial17.class);
                mContext.startActivity(intent);
                break;

            case 18:
                intent = new Intent(mContext, Tutorial18.class);
                mContext.startActivity(intent);
                break;

            case 19:
                intent = new Intent(mContext, Tutorial19.class);
                mContext.startActivity(intent);
                break;

            case 20:
                intent = new Intent(mContext, Tutorial20.class);
                mContext.startActivity(intent);
                break;

            case 21:
                intent = new Intent(mContext, Tutorial21.class);
                mContext.startActivity(intent);
                break;

            case 22:
                intent = new Intent(mContext, Tutorial22.class);
                mContext.startActivity(intent);
                break;

            case 23:
                intent = new Intent(mContext, Tutorial23.class);
                mContext.startActivity(intent);
                break;

            default:
                Toast.makeText(mContext,"Invalid Selection",Toast.LENGTH_SHORT).show();
        }
    }
}
