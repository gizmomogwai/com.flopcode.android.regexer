package com.flopcode.android.tools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class EditParametersActivity extends PreferenceActivity {

  private HashMap<String, Parameter> fParameters;

  public class HashMapSharedPreferences implements SharedPreferences {

    private ArrayList<OnSharedPreferenceChangeListener> fListeners = new ArrayList<OnSharedPreferenceChangeListener>();

    @Override
    public boolean contains(String key) {
      return fParameters.containsKey(key);
    }

    @Override
    public Editor edit() {
      return new Editor() {
        HashMap<String, Parameter> fToDo = new HashMap<String, Parameter>();

        @Override
        public Editor clear() {
          fToDo.clear();
          return this;
        }

        @Override
        public boolean commit() {
          fParameters.putAll(fToDo);
          recalcPreferenceTitles();
          return true;
        }

        @Override
        public void apply() {
          fParameters.putAll(fToDo);
          recalcPreferenceTitles();
        }


        @Override
        public Editor putBoolean(String key, boolean value) {
          return null;
        }

        @Override
        public Editor putFloat(String key, float value) {
          return null;
        }

        @Override
        public Editor putInt(String key, int value) {
          return null;
        }

        @Override
        public Editor putLong(String key, long value) {
          return null;
        }

        @Override
        public Editor putString(String key, String value) {
          Parameter p = fParameters.get(key);
          Parameter newP = new Parameter(p.getMin(), p.getMax(), new Float(value));
          fToDo.put(key, newP);
          return this;
        }

        @Override
        public Editor putStringSet(String key, Set<String> values) {
          return null;
        }

        @Override
        public Editor remove(String key) {
          fToDo.remove(key);
          return this;
        }

      };
    }


    @Override
    public Map<String, ?> getAll() {
      return fParameters;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
      return false;
    }

    @Override
    public float getFloat(String key, float defValue) {
      return 0;
    }

    @Override
    public int getInt(String key, int defValue) {
      return 0;
    }

    @Override
    public long getLong(String key, long defValue) {
      return 0;
    }

    @Override
    public String getString(String key, String defValue) {
      return "" + fParameters.get(key).getValue();
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
      return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
      fListeners.add(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
      fListeners.remove(listener);
    }

  }


  @SuppressWarnings("unchecked")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fParameters = (HashMap<String, Parameter>) getIntent().getSerializableExtra("parameters");
    PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);
    for (String key : fParameters.keySet()) {
      Parameter p = fParameters.get(key);
      EditTextPreference pref = new EditTextPreference(this);
      pref.setDialogTitle("Change value of " + key);
      pref.setKey(key);
      pref.setDialogMessage("" + p.getMin() + " < newValue < " + p.getMax());
      pref.setText("text" + key);
      pref.setTitle(key + "=");
      screen.addPreference(pref);
    }
    setPreferenceScreen(screen);
    recalcPreferenceTitles();
  }

  @Override
  public SharedPreferences getSharedPreferences(String name, int mode) {
    return new HashMapSharedPreferences();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      sendResultOk();
    }

    return super.onKeyDown(keyCode, event);
  }

  private void sendResultOk() {
    Intent res = new Intent();
    res.putExtra("parameters", fParameters);
    setResult(RESULT_OK, res);
  }

  private void recalcPreferenceTitles() {
    PreferenceScreen screen = getPreferenceScreen();
    if (screen != null) {
      for (int i = 0; i < screen.getPreferenceCount(); i++) {
        EditTextPreference pref = (EditTextPreference) screen.getPreference(i);
        Parameter p = fParameters.get(pref.getKey());
        pref.setSummary("" + p.getMin() + " <= " + p.getValue() + " <= " + p.getMax());
      }
    }
  }

}
