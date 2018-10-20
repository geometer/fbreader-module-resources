/*
 * Copyright (C) 2004-2018 FBReader.ORG Limited <contact@fbreader.org>
 */

package org.geometerplus.zlibrary.core.resources;

import java.util.*;

import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.core.filesystem.ZLResourceFile;
import org.geometerplus.zlibrary.core.language.Language;
import org.geometerplus.zlibrary.core.language.LanguageUtil;

abstract public class ZLResource {
	public final String Name;

	private static final List<String> ourLanguageCodes = new LinkedList<String>();
	public static List<String> languageCodes() {
		synchronized (ourLanguageCodes) {
			if (ourLanguageCodes.isEmpty()) {
				final ZLFile dir = ZLResourceFile.createResourceFile("resources/application");
				final List<ZLFile> children = dir.children();
				for (ZLFile file : children) {
					final String name = file.getShortName();
					final String postfix = ".xml";
					if (name.endsWith(postfix) && !"neutral.xml".equals(name)) {
						ourLanguageCodes.add(name.substring(0, name.length() - postfix.length()));
					}
				}
			}
		}
		return Collections.unmodifiableList(ourLanguageCodes);
	}

	public static List<Language> interfaceLanguages() {
		final List<Language> allLanguages = new LinkedList<Language>();
		final ZLResource resource = ZLResource.resource("language-self");
		for (String c : languageCodes()) {
			allLanguages.add(LanguageUtil.language(c, resource));
		}
		Collections.sort(allLanguages);
		allLanguages.add(0, LanguageUtil.language(Language.SYSTEM_CODE));
		return allLanguages;
	}

	public static ZLResource resource(String key) {
		ZLTreeResource.buildTree();
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
