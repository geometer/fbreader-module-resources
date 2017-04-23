/*
 * Copyright (C) 2007-2016 FBReader.ORG Limited <contact@fbreader.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.zlibrary.core.resources;

import java.util.*;

import android.content.Context;

import org.fbreader.util.Language;

abstract public class ZLResource {
	public final String Name;

	private static List<String> languageCodes(Context context) {
		try {
			final String[] names = context.getAssets().list("resources/application");
			if (names != null && names.length != 0) {
				final ArrayList<String> codes = new ArrayList<String>(names.length);
				final String postfix = ".xml";
				for (String n : names) {
					if (n.endsWith(postfix) && !"neutral.xml".equals(n)) {
						codes.add(n.substring(0, n.length() - postfix.length()));
					}
				}
				return codes;
			}
		} catch (Exception e) {
		}
		return Collections.emptyList();
	}

	public static List<Language> interfaceLanguages(Context context) {
		final List<Language> allLanguages = new LinkedList<Language>();
		final ZLResource resource = ZLResource.resource("language-self");
		for (String c : languageCodes(context)) {
			allLanguages.add(ResourceLanguageUtil.language(c, resource));
		}
		Collections.sort(allLanguages);
		allLanguages.add(0, ResourceLanguageUtil.language(Language.SYSTEM_CODE));
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
