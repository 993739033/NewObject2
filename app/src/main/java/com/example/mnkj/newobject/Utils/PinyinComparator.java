package com.example.mnkj.newobject.Utils;

import com.example.mnkj.newobject.Bean.ContactBean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ContactBean> {

	public int compare(ContactBean o1, ContactBean o2) {
		if (o1.getLetters().equals("@")
				|| o2.getLetters().equals("#")) {
			return -1;
		} else if (o1.getLetters().equals("#")
				|| o2.getLetters().equals("@")) {
			return 1;
		} else {
			return o1.getLetters().compareTo(o2.getLetters());
		}
	}

}
