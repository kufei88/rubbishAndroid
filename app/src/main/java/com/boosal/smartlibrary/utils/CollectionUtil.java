package com.boosal.smartlibrary.utils;

import java.util.Iterator;
import java.util.Set;

public class CollectionUtil {

	/**
	 *  比较Set集合是否相同
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static boolean isSetEqual(Set set1, Set set2) {

		if(set1 != null && set2 != null &&  set1.size() == 0 && set2.size() == 0) {
			return true;
		}

		if (set1 == null && set2 == null) {
			return true; // Both are null
		}

		if (set1 == null || set2 == null || set1.size() != set2.size() || set1.size() == 0 || set2.size() == 0) {
			return false;
		}

		Iterator ite1 = set1.iterator();
		Iterator ite2 = set2.iterator();

		boolean isFullEqual = true;

		while (ite2.hasNext()) {
			if (!set1.contains(ite2.next())) {
				isFullEqual = false;
			}
		}

		return isFullEqual;
	}
}
