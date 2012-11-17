package com.thornton.messaging.adapters;

import java.util.List;

import com.thornton.messaging.R;
import com.thornton.messaging.pojo.Message;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConversationAdapter extends ArrayAdapter<Message>{
	
	private List<Message> messages;
	
	private Activity activity;
	
	private int layout;

	public ConversationAdapter(final Context context, final int resource, final List<Message> messages) {
		super(context, resource, messages);
		this.messages = messages;
		this.layout = resource;
		this.activity = (Activity)context;
	}
	
	public void addMessage(final Message message){
		messages.add(message);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent){
		View view = convertView;
		
		if(null == view){
			LayoutInflater inflator = activity.getLayoutInflater();
			view = inflator.inflate(layout, null);
		}
		
		final Message message = messages.get(position);
		final TextView contactBadge = (TextView)view.findViewById(R.id.contact_image);
		final TextView myBadge = (TextView)view.findViewById(R.id.me_image);
		final TextView contactMessage = (TextView)view.findViewById(R.id.contact_message);
		final TextView myMessage = (TextView)view.findViewById(R.id.me_message);
		
		if(message.isToMe()){
			contactBadge.setVisibility(View.VISIBLE);
			myBadge.setVisibility(View.GONE);
			contactMessage.setText(message.getMessage());
			contactMessage.setVisibility(View.VISIBLE);
			myMessage.setVisibility(View.GONE);
		}
		else{
			contactBadge.setVisibility(View.GONE);
			myBadge.setVisibility(View.VISIBLE);
			myMessage.setText(message.getMessage());
			myMessage.setVisibility(View.VISIBLE);
			contactMessage.setVisibility(View.GONE);
		}
		
		return view;
	}
}
