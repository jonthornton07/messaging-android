package com.thornton.messaging.adapters;

import java.util.List;

import com.thornton.messaging.R;
import com.thornton.messaging.pojo.Contact;
import com.thornton.messaging.pojo.Message;
import com.thornton.messaging.providers.ContactsContentProvider;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class ConversationAdapter extends ArrayAdapter<Message>{
	
	private List<Message> messages;
	
	private Activity activity;
	
	private int layout;

	private Contact contact;

	public ConversationAdapter(final Context context, final int resource, final List<Message> messages, final Contact contact) {
		super(context, resource, messages);
		this.messages = messages;
		this.layout = resource;
		this.activity = (Activity)context;
		this.contact = contact;
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
		final QuickContactBadge contactBadge = (QuickContactBadge)view.findViewById(R.id.contact_image);
		final QuickContactBadge myBadge = (QuickContactBadge)view.findViewById(R.id.me_image);
		final TextView contactMessage = (TextView)view.findViewById(R.id.contact_message);
		final TextView myMessage = (TextView)view.findViewById(R.id.me_message);
		
		if(message.isToMe()){
			//TODO: show the image
			//contactBadge.setImageBitmap(ContactsContentProvider.fetchThumbnail(activity, contact.getImage()));
			contactBadge.assignContactFromPhone(contact.getPhoneNumber(), true);
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
