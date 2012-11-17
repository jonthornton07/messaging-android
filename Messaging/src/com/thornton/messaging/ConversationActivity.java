package com.thornton.messaging;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ConversationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_conversation, menu);
        return true;
    }
}
