package com.thornton.messaging.receivers;

import com.thornton.messaging.activity.ConversationActivity;
import com.thornton.messaging.pojo.Message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BaseReceiver{

	@Override
	public void onReceive(final Context context, final Intent intent){
		final Bundle bundle = intent.getExtras();
		if(null == bundle){return;}

		final Message message = extractMessage(bundle);
		toastNewMessage(context, message.getMessage());
		launchMessenger(context);
		alertNewMessage(context, message);
		abortBroadcast();
	}

	private Message extractMessage(final Bundle bundle) {
		final Object[] pdus = (Object[]) bundle.get("pdus");
		final SmsMessage[] smsMessage = new SmsMessage[pdus.length];

		String number = "";
		String text = "";
				
		for(int i = 0; i < smsMessage.length; i++){
			smsMessage[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			if(i == 0){
				number = smsMessage[i].getOriginatingAddress();
			}

			text += smsMessage[i].getMessageBody().toString();
		}
		return new Message(text, true, number);
	}

	private void alertNewMessage(final Context context, final Message message) {
		Intent i = new Intent();
		i.setAction("SMS_RECEIVED_ACTION");
		i.putExtra(SMS, message.getMessage());
		i.putExtra(NUMBER, message.getNumber());
		i.putExtra(IS_TO_ME, true);
		context.sendBroadcast(i);
	}

	private void launchMessenger(final Context context) {
		
		//TODO:  Instead create a notification that on click will send you to the conversation
		final Intent i = new Intent(context, ConversationActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);

	}

	private void toastNewMessage(final Context context, final String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		Log.d("Receiver", message);
	}
}
