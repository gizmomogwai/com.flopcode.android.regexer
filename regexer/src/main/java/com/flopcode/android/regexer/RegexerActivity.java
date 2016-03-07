package com.flopcode.android.regexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.EditText;

import com.flopcode.android.regexer.R;
import com.flopcode.android.tools.IOHelper;

public class RegexerActivity extends Activity {
	private static final int REQUEST_CODE = 17;
	private EditText fTextInput;
	private EditText fPatternInput;
	private WebView fOutput;
	private String fHead;
	private int fFlags;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		fFlags = pref.getInt(ConfigureFlagsActivity.FLAGS, 0);

		setContentView(R.layout.main);
		fTextInput = (EditText) findViewById(R.id.text_input);
		fPatternInput = (EditText) findViewById(R.id.pattern_input);
		try {
			fHead = IOHelper.getContent(getAssets().open("head.html"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		addEditTextListener(fTextInput);
		addEditTextListener(fPatternInput);

		fOutput = (WebView) findViewById(R.id.output);
		fOutput.getSettings().setJavaScriptEnabled(true);

		update();
	}

	private void addEditTextListener(EditText textInput) {
		textInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				update();
			}
		});
	}

	private void update() {
		try {
			final String pattern = fPatternInput.getText().toString();
			Pattern p = Pattern.compile(pattern, fFlags);
			final String input = fTextInput.getText().toString();
			Matcher match = p.matcher(input);
			putIntoWebView(format(match, input));
		} catch (Exception e) {
			putIntoWebView(format(e));
		}

	}

	private void putIntoWebView(String format) {
		fOutput.loadDataWithBaseURL("fake://url/whats/up", format, "text/html",
				"utf-8", "fake://did/not/work");
	}

	private String format(Exception e) {
		final String message = e.getMessage();
		String replace = message.replaceAll(" ", "&nbsp;");
		replace = replace.replaceAll("\n", "<br />");
		return "<html>" + fHead + "<body class=\"error\"><code>" + replace
				+ "</code><body></html>";
	}

	private String format(Matcher match, String input) throws Exception {
		if (match.matches()) {
			List<Group> groups = new ArrayList<Group>();
			for (int i = 0; i <= match.groupCount(); i++) {
				groups.add(new Group(match, i, input));
			}

			StringBuilder res = new StringBuilder();
			res.append("<html>");
			res.append(fHead);
			res.append("<body>");

			res.append("<p>matched ").append(makeLink(groups.get(0))).append(
					"</p><p>");
			int currentIdx = 0;
			for (Group g : groups) {
				if (g.fIdx != 0) {
					res.append(input.substring(currentIdx, g.fStart));
					res.append(makeLink(g));
					currentIdx = g.fEnd;
				}
			}
			if (currentIdx < input.length()) {
				res.append(input.substring(currentIdx, input.length()));
			}
			res.append("</p>");

			res.append("<table>");
			res
					.append("<tr><th>idx</th><th>content</th><th>start</th><th>end</th></tr>");
			for (Group g : groups) {
				res.append("<tr id=\"").append(g.fIdx).append("\"><td>")
						.append(g.fIdx).append("</td><td>").append(g.fContent)
						.append("</td><td>").append(g.fStart).append(
								"</td><td>").append(g.fEnd)
						.append("</td></tr>");
			}
			res.append("</body></html>");
			return res.toString();
		} else {
			throw new Exception("no match");
		}
	}

	boolean even = false;

	private String makeLink(Group g) {
		even = !even;
		return "<a class=\"" + (even ? "even" : "odd") + "\" href=\"" + g.fIdx
				+ "\">" + g.fContent + "</a>";
	}

	static class Group {
		private int fIdx;
		private String fContent;
		private int fStart;
		private int fEnd;

		public Group(Matcher m, int idx, String completeString) {
			fIdx = idx;
			fStart = m.start(idx);
			fEnd = m.end(idx);
			fContent = m.group(idx);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.regexer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.flags:
			Intent i = new Intent(this, ConfigureFlagsActivity.class);
			i.putExtra(ConfigureFlagsActivity.FLAGS, fFlags);
			startActivityForResult(i, REQUEST_CODE);
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				fFlags = data.getIntExtra("flags", 0);
			}
		}
	}

	@Override
	protected void onDestroy() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor e = pref.edit();
		e.putInt(ConfigureFlagsActivity.FLAGS, fFlags);
		e.commit();
		super.onDestroy();
	}
}
