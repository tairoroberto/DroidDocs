package br.com.trmasolucoes.droiddocs.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.trmasolucoes.droiddocs.db.DBCore;
import br.com.trmasolucoes.droiddocs.domain.Favorite;


public class FavoritesDao {

	private SQLiteDatabase db;

	public FavoritesDao(Context context) {
		DBCore dbCore = new  DBCore(context);
		db = dbCore.getWritableDatabase();
	}

	public void insert(Favorite favorite) {
		ContentValues values = new ContentValues();
		values.put("desc", favorite.getDesc());
		values.put("link", favorite.getLink());

		db.insert("favorites", null, values);
	}


	public void update(Favorite favorite) {
		ContentValues values = new ContentValues();
        values.put("desc", favorite.getDesc());
        values.put("link", favorite.getLink());

		db.update("favorites", values, "_id = ?", new String[]{"" + favorite.getId()});
	}


	public void delete(Favorite favorite) {
		db.delete("favorites", "_id = ?", new String[]{"" + favorite.getId()});
	}



	public  List<Favorite> search() {
		List<Favorite> list = new ArrayList<Favorite>();
		String[] columns = {"_id","desc","link"};
		Cursor cursor = db.query("favorites", columns, null, null, null, null, "_id");
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			do {
                Favorite favorite = new Favorite();
                favorite.setId(cursor.getLong(0));
                favorite.setDesc(cursor.getString(1));
                favorite.setLink(cursor.getString(2));

				list.add(favorite);
			} while (cursor.moveToNext());
		}
        cursor.close();
		return(list);
	}


    public Favorite getFavotiteById(long id) {
        Favorite favorite = new Favorite();

        String[] columns = {"_id", "desc", "link"};
        String where = "_id = ?";

        Cursor cursor = db.query("favorites", columns, where, new String[]{"" + id}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            favorite.setId(cursor.getLong(0));
            favorite.setDesc(cursor.getString(1));
            favorite.setLink(cursor.getString(2));

            cursor.close();
            return favorite;
        } else {
            cursor.close();
            return favorite;
        }
    }

	public Favorite getFavotiteByLink(String link) {
		Favorite favorite = new Favorite();

		String[] columns = {"_id", "desc", "link"};
		String where = "link = ?";

		Cursor cursor = db.query("favorites", columns, where, new String[]{link}, null, null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			favorite.setId(cursor.getLong(0));
			favorite.setDesc(cursor.getString(1));
			favorite.setLink(cursor.getString(2));

			cursor.close();
			return favorite;
		} else {
			cursor.close();
			return favorite;
		}
	}
}

	

