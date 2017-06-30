package com.flopcode.android.tools;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.flopcode.android.tools.ImageGallery.DetailProvider;

/**
 * works with cursors that contain MediaStore.Images.ImageColumns.DATA
 */
public class AndroidMediaDetailProvider extends DetailProvider {
  private final Cursor fCursor;

  public AndroidMediaDetailProvider(Cursor c) {
    super(true);
    fCursor = c;
  }

  @Override
  public Uri getUri(int pos) {
    if (fCursor.moveToPosition(pos)) {
      return Uri.parse(fCursor.getString(fCursor.getColumnIndex(  MediaStore.Images.ImageColumns.DATA)));
    }
    return null;
  }

}
