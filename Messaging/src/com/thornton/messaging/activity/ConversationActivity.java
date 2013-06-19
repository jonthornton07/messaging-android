package com.thornton.messaging.activity;

import java.util.ArrayList;
import com.thornton.messaging.R;
import com.thornton.messaging.adapters.ConversationAdapter;
import com.thornton.messaging.pojo.Contact;
import com.thornton.messaging.pojo.Message;
import com.thornton.messaging.providers.ContactsContentProvider;
import com.thornton.messaging.receivers.BaseReceiver;
import com.thornton.messaging.receivers.DeliveryReceiver;
import com.thornton.messaging.receivers.IntentReceiver;
import com.thornton.messaging.receivers.SendReceiver;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class ConversationActivity extends Activity {


	private PendingIntent sendPendingIntent, deliveredPendingIntnent;
	
	private IntentFilter filter;
	
	private BaseReceiver intentReceiver, sentReceiver, deliveredReceiver;

	private ListView messages;
	
	private ConversationAdapter adapter;
	
	private Contact contact;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        
        final Intent intent = this.getIntent();
        
        Bundle bundle = intent.getExtras();
        if(null != bundle){
        	setContactInfo(bundle);
        }
        else{
        	contact = ContactsContentProvider.getContact(this, "18155928212");
        }
        
        messages = (ListView)findViewById(R.id.messages);
        adapter = new ConversationAdapter(this, R.layout.message_item, new ArrayList<Message>(), contact);
        messages.setAdapter(adapter);
        
        //TODO: for testing
        //TODO: insert a random number here
        //addMessage(new Message("Hey", true,  ));
        
        sendPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(BaseReceiver.SENT), 0);
        deliveredPendingIntnent = PendingIntent.getBroadcast(this, 0, new Intent(BaseReceiver.DELIVERED), 0);
        
        filter = new IntentFilter();
        filter.addAction(BaseReceiver.SMS_RECEIVED_ACTION);
        
        intentReceiver = new IntentReceiver(this);
        registerReceiver(intentReceiver, filter);
    }
    
    private void setContactInfo(Bundle bundle) {
		final String name = bundle.getString(Contact.NAME);
		final String number = bundle.getString(Contact.NUMBER);
		final int image = bundle.getInt(Contact.IMAGE);
		
		contact = new Contact(name, number, image);
	}

	@Override
    public void onResume(){
    	super.onResume();
     	
    	sentReceiver = new SendReceiver();
    	deliveredReceiver = new DeliveryReceiver();
    	
    	registerReceiver(sentReceiver, new IntentFilter(BaseReceiver.SENT));
    	registerReceiver(deliveredReceiver, new IntentFilter(BaseReceiver.DELIVERED));
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	unregisterReceiver(sentReceiver);
    	unregisterReceiver(deliveredReceiver);
    }

   

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_conversation, menu);
        return true;
    }

	public void sendMessage(View v){
    	final SmsManager manager = SmsManager.getDefault();
    	final EditText message = (EditText) findViewById(R.id.message);
    	
    	if(TextUtils.isEmpty(message.getText())){return;}
    	manager.sendTextMessage(contact.getPhoneNumber(), null, message.getText().toString(), sendPendingIntent, deliveredPendingIntnent);
    	addMessage(new Message(message.getText().toString(), false, null));
    	message.setText("");
    }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		unregisterReceiver(intentReceiver);
	}

	public void addMessage(Message message) {
		adapter.add(message);
	}
}
