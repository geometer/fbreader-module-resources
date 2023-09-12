/*
 * Copyright (C) 2004-2023 FBReader.ORG Limited <contact@fbreader.org>
 */

package org.geometerplus.zlibrary.core.resources;

import java.util.*;

import android.content.Context;

import org.fbreader.language.Language;
import org.fbreader.language.LanguageUtil;

abstract public class ZLResource {
	public final String Name;

	private static String[] assets(Context context, String path) {
		try {
			final String[] names = context.getAssets().list(path);
			if (names != null) {
				return names;
			}
		} catch (Throwable e) {
		}
		return new String[0];
	}

	private static final List<String> ourLanguageCodes = new LinkedList<String>();
	public static List<String> languageCodes(Context context) {
		synchronized (ourLanguageCodes) {
			if (ourLanguageCodes.isEmpty()) {
				for (String name : assets(context, "resources/application")) {
					final String postfix = ".xml";
					if (name.endsWith(postfix) && !"neutral.xml".equals(name)) {
						ourLanguageCodes.add(name.substring(0, name.length() - postfix.length()));
					}
				}
			}
		}
		return Collections.unmodifiableList(ourLanguageCodes);
	}

	public static List<Language> interfaceLanguages(Context context) {
		final List<Language> allLanguages = new LinkedList<Language>();
		final ZLResource resource = ZLResource.resource(context, "language-self");
		for (String c : languageCodes(context)) {
			allLanguages.add(LanguageUtil.language(c, resource));
		}
		Collections.sort(allLanguages);
		allLanguages.add(0, LanguageUtil.language(context, Language.SYSTEM_CODE));
		return allLanguages;
	}

	public static ZLResource resource(Context context, String key) {
		ZLTreeResource.buildTree(context);
		if (ZLTreeResource.ourRoot == null) {
			return ZLMissingResource.Instance;
		}
		try {
			return ZLTreeResource.ourRoot.getResource(key);
		} finally {
		}
	}

	protected ZLResource(String name) {
		Name = name;
	}

	abstract public boolean hasValue();
	abstract public String getValue();
	abstract public String getValue(int condition);
	abstract public ZLResource getResource(String key);
}
