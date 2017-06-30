package com.flopcode.android.tools;

public class WebviewHelper {

	public static String javascriptTagFromAsset(String string) {
		return "<script type=\"text/javascript\" src=\"file:///android_asset/"
				+ string + "\"></script>";
	}

}
