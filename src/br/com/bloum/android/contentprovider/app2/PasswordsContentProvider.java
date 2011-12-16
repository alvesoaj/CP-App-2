package br.com.bloum.android.contentprovider.app2;

import java.util.HashMap;

import br.com.bloum.android.contentprovider.app2.Password.Passwords;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class PasswordsContentProvider extends ContentProvider {

	@SuppressWarnings("unused")
	private static final String TAG = "PasswordsContentProvider";
	private static final String DATABASE_NAME = "passwords.db";
	private static final int DATABASE_VERSION = 1;
	private static final String PASSWORDS_TABLE_NAME = "passwords";
	public static final String AUTHORITY = "br.com.bloum.contentprovider.app2.PasswordsContentProvider";
	private static final UriMatcher uriMatcher;
	private static final int PASSWORDS = 1;
	private static HashMap<String, String> passwordsProjectionMap;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + PASSWORDS_TABLE_NAME + " ( "
					+ Passwords.PASSWORD_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ Passwords.APPLICATION + " VARCHAR(255), "
					+ Passwords.USER + " VARCHAR(255), " + Passwords.PASSWORD
					+ " VARCHAR(255) );");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + PASSWORDS_TABLE_NAME);
			onCreate(db);
		}

	}

	private DatabaseHelper dbHelper;

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;

		switch (uriMatcher.match(uri)) {
		case PASSWORDS:
			count = db.delete(PASSWORDS_TABLE_NAME, where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case PASSWORDS:
			return Passwords.CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (uriMatcher.match(uri) != PASSWORDS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowID = db.insert(PASSWORDS_TABLE_NAME, Passwords.APPLICATION,
				values);
		if (rowID > 0) {
			Uri passwordUri = ContentUris.withAppendedId(Passwords.CONTENT_URI,
					rowID);
			getContext().getContentResolver().notifyChange(passwordUri, null);
			return passwordUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {
		case PASSWORDS:
			qb.setTables(PASSWORDS_TABLE_NAME);
			qb.setProjectionMap(passwordsProjectionMap);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;

		switch (uriMatcher.match(uri)) {
		case PASSWORDS:
			count = db.update(PASSWORDS_TABLE_NAME, values, where, whereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PASSWORDS_TABLE_NAME, PASSWORDS);

		passwordsProjectionMap = new HashMap<String, String>();
		passwordsProjectionMap
				.put(Passwords.PASSWORD_ID, Passwords.PASSWORD_ID);
		passwordsProjectionMap
				.put(Passwords.APPLICATION, Passwords.APPLICATION);
		passwordsProjectionMap.put(Passwords.USER, Passwords.USER);
		passwordsProjectionMap.put(Passwords.PASSWORD, Passwords.PASSWORD);
	}

}
