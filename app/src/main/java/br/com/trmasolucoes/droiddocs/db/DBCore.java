package br.com.trmasolucoes.droiddocs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCore extends SQLiteOpenHelper{
	private static final String NOME_DB = "DroidDocs_db";
	private static final int VERSAO_DB = 1;

	public DBCore(Context context) {
		super(context, NOME_DB, null, VERSAO_DB);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

        db.execSQL("create table favorites("
                + "_id integer primary key autoincrement,"
                + "desc text not null,"
                + "link text not null);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table favorites");
		onCreate(db);		
	}
	
}
