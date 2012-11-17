package com.thornton.messaging.receivers;

import com.thornton.messaging.activity.ConversationActivity;

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

		final String message = extractMessage(bundle);
		toastNewMessage(context, message);
		launchMessenger(context);
		alertNewMessage(context, message);
		abortBroadcast();
	}

	private String extractMessage(final Bundle bundle) {
		String message = "SMS from ";
		final Object[] pdus = (Object[]) bundle.get("pdus");
		final SmsMessage[] messages = new SmsMessage[pdus.length];

		for(int i = 0; i < messages.length; i++){
			messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			if(i == 0){
				message += messages[i].getOriginatingAddress();
				message += ": ";
			}

			message += messages[i].getMessageBody().toString();
		}
		return message;
	}

	private void alertNewMessage(final Context context, final String message) {
		Intent i = new Intent();
		i.setAction("SMS_RECEIVED_ACTION");
		i.putExtra(SMS, message);
		i.putExtra(IS_TO_ME, true);
		context.sendBroadcast(i);
	}

	private void launchMessenger(final Context context) {
		final Intent i = new Intent(context, ConversationActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);

	}

	private void toastNewMessage(final Context context, final String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		Log.d("Receiver", message);
	}
}
