package com.gles.rohit.ogldevandroid;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class OglDevAndroid extends ListActivity {
    OglDevProgramList mOglDevPgmList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOglDevPgmList  = new OglDevProgramList(this,getResources().getStringArray(R.array.OglDevPgmNames));
        setListAdapter(mOglDevPgmList);
    }

    @Override
    protected void onListItemClick(ListView list,View view, int position, long id){
        mOglDevPgmList.launchActivity(position);
    }
}
