package com.flopcode.android.tools;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ViewSwitcher.ViewFactory;

public class ImageGallery {

  private final ImageSwitcher fSwitcher;
  private final Gallery fGallery;
  private final Activity fActivity;
  private DetailProvider fProvider;
  public static class DetailProvider {
    private final boolean fProvidesUris;
    public DetailProvider(boolean providesUris) {
      fProvidesUris = providesUris;
    }
    boolean providesUris() {
      return fProvidesUris;
    }
    public Uri getUri(int pos) {
      throw new UnsupportedOperationException();
    }
    public Drawable getDrawable(int pos) {
      throw new UnsupportedOperationException();
    }
  }

  public interface ThumbnailAdapter extends SpinnerAdapter {
  }
  
  public ImageGallery(final Activity activity, final ImageSwitcher switcher, final Gallery gallery) {
    fActivity = activity;
    fSwitcher = switcher;
    fGallery = gallery;

    fSwitcher.setFactory(new ViewFactory() {
      public View makeView() {
        ImageView i = new ImageView(fActivity);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
        return i;
      }
    });
    fGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
        if (fProvider.providesUris()) {
          fSwitcher.setImageURI(fProvider.getUri(pos));
        } else {
          fSwitcher.setImageDrawable(fProvider.getDrawable(pos));
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
      }
    });
  }

  public void setAdapter(SpinnerAdapter adapter, DetailProvider provider) {
    fGallery.setAdapter(adapter);
    fProvider = provider;
  }

}
