package br.com.bloum.android.contentprovider.app2;

import android.net.Uri;
import android.provider.BaseColumns;

public class Password {

	public Password(){
	}
	
	public static final class Passwords implements BaseColumns{
		private Passwords(){
		}
		
		public static final Uri CONTENT_URI = Uri.parse("content://"+PasswordsContentProvider.AUTHORITY+"/passwords");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.bloum.passwords";
		
		public static final String PASSWORD_ID = "_id";
		public static final String APPLICATION = "application";
		public static final String USER = "user";
		public static final String PASSWORD = "password";
	}
	
}
