package com.thornton.messaging.providers;

import com.thornton.messaging.pojo.Contact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

public class ContactsContentProvider {
	
	private static final Uri URI = PhoneLookup.CONTENT_FILTER_URI;
	
	private static final String[] PHOTO_BITMAP_PROJECTION = new String[] {ContactsContract.CommonDataKinds.Photo.PHOTO};

	
	private ContactsContentProvider(){}
	
	public static Contact getContact(final Context context, final String phoneNumber){
		final ContentResolver cr = context.getContentResolver();
		final Uri uri = Uri.withAppendedPath(URI, Uri.encode(phoneNumber));
		final Cursor c = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_ID}, null, null, null);
		
		if(null == c || !c.moveToFirst()){return new Contact("Unknown", phoneNumber, -1);}
		
		final String displayName = c.getString(c.getColumnIndex(PhoneLookup.DISPLAY_NAME));
		final int image = c.getInt(c.getColumnIndex(PhoneLookup.PHOTO_ID));
		
		c.close();
		
		//TODO: Handle no image
		//TODO: Handle no display name
		
		return new Contact(displayName, phoneNumber, image);
	}
	
	public static Bitmap fetchThumbnail(final Context context, final int thumbnailId) {
		final ContentResolver cr = context.getContentResolver();
	    final Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
	    final Cursor c = cr.query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);

        Bitmap thumbnail = null;
        if (c.moveToFirst()) {
            final byte[] thumbnailBytes = c.getBlob(0);
            if (thumbnailBytes != null) {
                thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);
            }
        }
        c.close();
        return thumbnail;
	}
}
