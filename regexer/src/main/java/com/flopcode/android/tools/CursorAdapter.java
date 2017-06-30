package com.flopcode.android.tools;

import android.database.Cursor;
import android.widget.BaseAdapter;

public abstract class CursorAdapter extends BaseAdapter {

  private final Cursor fCursor;

  public CursorAdapter(Cursor c) {
    fCursor = c;
  }
  
  public Cursor getCursor() {
    return fCursor;
  }

  @Override
  public int getCount() {
    return getCursor().getCount();
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    // TODO Auto-generated method stub
    return 0;
  }
  
}
