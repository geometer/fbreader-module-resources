/*
 * Copyright (C) 2004-2023 FBReader.ORG Limited <contact@fbreader.org>
 */

package org.fbreader.language;

import java.util.Locale;

import android.content.Context;

import org.geometerplus.zlibrary.core.resources.ZLResource;

public abstract class LanguageUtil {
	public static Language language(Context context, String code) {
		return language(code, ZLResource.resource(context, "language"));
	}

	public static Language language(String code, ZLResource root) {
		if (code == null) {
			return null;
		}

		final ZLResource resource = root.getResource(code);
		if (resource.hasValue()) {
			return new Language(code, resource.getValue());
		} else {
			try {
				return new Language(code, new Locale(code).getDisplayLanguage());
			} catch (Throwable t) {
				return null;
			}
		}
	}
}
