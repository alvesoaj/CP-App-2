package br.com.bloum.android.contentprovider.app2;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class App2 extends Activity {
	
	public int test = 5;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavascriptInterface(this), "Android");
        
        //getContentResolver().delete( Password.Passwords.CONTENT_URI, null, null );
        
        String data = "<html><head>"
        	+ "<link href='css.css' rel='stylesheet' type='text/css' />"
			+ "<script type='text/javascript' src='jquery.js'></script>"
			+ "<script type='text/javascript' src='js.js'></script>"
			+ "</head><body>"
			+ "<div id='content1'>"
			+ "<div id='insert' class='button'>Inserir</div>"
			+ "<div id='read' class='button'>Listar</div>"
			+ "</div>"
			+ "<div id='content2'>"
			+ "Aplicação<br>"
			+ "<input type='text' id='insert_application' /><br>"
			+ "Usuário<br>"
			+ "<input type='text' id='insert_user' /><br>"
			+ "Senha<br>"
			+ "<input type='text' id='insert_password' /><br>"
			+ "<div class='clear_float'></div>"
			+ "<div id='button_insert' class='button'>Inserir</div>"
			+ "<div id='button_back' class='button'>Voltar</div>"
			+ "<div class='clear_float'></div>"
			+ "</div>"
			+ "<div id='content3'>"
			+ "</div>"
			+ "</body></html>";
        webView.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "utf-8", null);
        
        //getContentResolver().delete( Password.Passwords.CONTENT_URI, null, null );
        
        /*
        ContentValues values = new ContentValues();
        values.put( Password.Passwords.APPLICATION, "gmail" );
        values.put( Password.Passwords.USER, "ricardo.krieg" );
        values.put( Password.Passwords.PASSWORD, "123456" );
        
        //Uri uri = getContentResolver().insert( Password.Passwords.CONTENT_URI, values );
        getContentResolver().delete( Password.Passwords.CONTENT_URI, null, null );
        
        Cursor passwords = managedQuery( Password.Passwords.CONTENT_URI, null, null, null, null );
        if ( passwords.moveToFirst() ){
        	do {
        		String appName = passwords.getString( passwords.getColumnIndex(Password.Passwords.APPLICATION));
        		Log.v("ContentProvider", appName);
        	} while ( passwords.moveToNext() );
        } else {
        	Log.v("ContentProvider", "Nenhum password encontrado");
        }
        passwords.close();
        */
    }
}