package com.flopcode.android.tools;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * works with cursors that contain MediaStore.Images.ImageColumns._ID
 */
public class AndroidMediaThumbnailAdapter extends CursorAdapter {
  private Activity fActivity;

  public AndroidMediaThumbnailAdapter(Cursor cursor, Activity a) {
    super(cursor);
    fActivity = a;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView i = new ImageView(fActivity);
    getCursor().requery();

    if (convertView == null) {
      getCursor().moveToPosition(position);
      int id = getCursor().getInt(getCursor().getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID));
      
      String[] projection = {
          MediaStore.Images.ImageColumns._ID,
          MediaStore.Images.ImageColumns.DATA
      };
      String selection = MediaStore.Images.Thumbnails.KIND + "=" + MediaStore.Images.Thumbnails.MICRO_KIND + " AND " + MediaStore.Images.Thumbnails.IMAGE_ID + "=" + id;
      Cursor c = fActivity.managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, selection, null, null);
      if (c.moveToFirst()) {
        i.setImageURI(Uri.parse(c.getString(c.getColumnIndex(  MediaStore.Images.ImageColumns.DATA))));
      }
      c.close();
    }
    return i;
  }
  
}
