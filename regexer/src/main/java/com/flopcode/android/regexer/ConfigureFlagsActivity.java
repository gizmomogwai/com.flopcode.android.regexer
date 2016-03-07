package com.flopcode.android.regexer;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.flopcode.android.regexer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class ConfigureFlagsActivity extends Activity {

	static final String FLAGS = "flags";
	private int fFlags;
	private HashMap<Integer, CheckBox> fMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fFlags = getIntent().getExtras().getInt(FLAGS);

		setContentView(R.layout.configure_flags);
		fMap = new HashMap<Integer, CheckBox>();
		addCheckboxForFlag(Pattern.CANON_EQ, R.id.canon_eq);
		addCheckboxForFlag(Pattern.CASE_INSENSITIVE, R.id.case_insensitive);
		addCheckboxForFlag(Pattern.COMMENTS, R.id.comments);
		addCheckboxForFlag(Pattern.DOTALL, R.id.dotall);
		addCheckboxForFlag(Pattern.LITERAL, R.id.literal);
		addCheckboxForFlag(Pattern.MULTILINE, R.id.multiline);
		addCheckboxForFlag(Pattern.UNICODE_CASE, R.id.unicode_case);
		addCheckboxForFlag(Pattern.UNIX_LINES, R.id.unix_lines);
		Button b = (Button)findViewById(R.id.ok);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent res = new Intent();
				res.putExtra(FLAGS, getFlags());
				setResult(Activity.RESULT_OK, res);
				finish();
			}

			
		});
		updateCheckboxes();
	}

	private int getFlags() {
		int res = 0;
		for (Integer key : fMap.keySet()) {
			if (fMap.get(key).isChecked()) {
				res = res | key.intValue();
			}
		}
		return res;
	}

	private void updateCheckboxes() {
		for (Integer key : fMap.keySet()) {
			fMap.get(key).setChecked((fFlags & key.intValue()) != 0);
		}
	}

	private void addCheckboxForFlag(int flag, int viewId) {
		fMap.put(flag, (CheckBox) findViewById(viewId));
	}

	
}
