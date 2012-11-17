package com.thornton.messaging.receivers;

import android.content.Context;
import android.content.Intent;

import com.thornton.messaging.activity.ConversationActivity;
import com.thornton.messaging.pojo.Message;

public class IntentReceiver extends BaseReceiver{
	
	private ConversationActivity activity;
	
	public IntentReceiver(final ConversationActivity activity){
		this.activity = activity;
	}

	@Override
	public void onReceive(final Context context, final Intent intent) {
		final String message = intent.getExtras().getString(SMS);
		final boolean isToMe = intent.getBooleanExtra(IS_TO_ME, false);
		final String number = intent.getStringExtra(NUMBER);
		activity.addMessage(new Message(message, isToMe, number));
	}
}
