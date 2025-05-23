/*
 * Copyright (C) 2004-2024 FBReader.ORG Limited <contact@fbreader.org>
 */

package org.geometerplus.zlibrary.core.resources;

import java.util.*;

import android.content.Context;

import org.fbreader.resources.R;

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
