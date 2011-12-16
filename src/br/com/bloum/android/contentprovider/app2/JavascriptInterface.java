package br.com.bloum.android.contentprovider.app2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class JavascriptInterface {
	Context mContext;
	
	public JavascriptInterface( Context c ) {
		mContext = c;
	}
	
	public void insert( String application, String user, String password ){
		
		if ( application.length() == 0 || user.length() == 0 || password.length() == 0 )
			return;
		
		ContentValues values = new ContentValues();
        values.put( Password.Passwords.APPLICATION, application );
        values.put( Password.Passwords.USER, user );
        values.put( Password.Passwords.PASSWORD, password );
        
        @SuppressWarnings("unused")
		Uri uri = mContext.getContentResolver().insert( Password.Passwords.CONTENT_URI, values );
	}
	
	public String read(){
		Cursor passwords = mContext.getContentResolver().query( 
				Password.Passwords.CONTENT_URI, null, null, null, null );
		
		String response = "";
		
        if ( passwords.moveToFirst() ){
        	response = "[";
        	do {
        		String application = passwords.getString( passwords.getColumnIndex(Password.Passwords.APPLICATION));
        		String user = passwords.getString( passwords.getColumnIndex(Password.Passwords.USER));
        		String password = passwords.getString( passwords.getColumnIndex(Password.Passwords.PASSWORD));
        		
        		response = response.concat( "{\"application\":\""+application+"\", " );
        		response = response.concat( "\"user\":\""+user+"\", " );
        		response = response.concat( "\"password\":\""+password+"\"}, " );
        	} while ( passwords.moveToNext() );
        	response = response.substring(0, response.length()-2);
        	response = response.concat("]");
        	
        	Log.v("ContentProvider", response);
        } else {
        	response = "Nenhum password";
        }
        
        passwords.close();
		
		return response;
	}
}
