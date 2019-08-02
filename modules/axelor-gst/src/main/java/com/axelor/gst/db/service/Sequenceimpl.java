package com.axelor.gst.db.service;

import com.axelor.gst.db.Sequence;

public class Sequenceimpl implements Sequenceservice {

	@Override
	public Sequence setseq(Sequence sequence) {
		// TODO Auto-generated method stub
		String prefix = sequence.getPrefix();
		String suffix = sequence.getSuffix();
		int padding = sequence.getPadding();

		int userpadding = padding - 1;
		for (int i = 0; i < userpadding; i++) {
			prefix = prefix + "0";
		}
		int no = 1;
		String nextno = prefix + no + suffix;
		sequence.setNextNumber(nextno);

		return sequence;
	}

	@Override
	public Sequence increasesequence(Sequence sequence) {
		// TODO Auto-generated method stub
		String prefix = sequence.getPrefix();
		String suffix = sequence.getSuffix();
		String nextnumber = sequence.getNextNumber();
		int padding = sequence.getPadding();
		int nextnolength = nextnumber.length();
		int prefixlength = prefix.length();
		int suffixlength = suffix.length();
		int end = nextnolength - suffixlength;
		String temp = "";
		for (int i = prefixlength; i < end; i++) {
			temp = temp + String.valueOf(nextnumber.charAt(i));
		}
		System.out.println(temp);

		int newno = Integer.parseInt(temp) + 1;
		System.err.println(newno);
		int length = padding - (newno + "").length();
		String newstring = "";
		for (int i = 0; i < length; i++) {
			newstring = "0" + newstring;
		}
		System.err.println(newstring);
		String increasedvalue = prefix + newstring;
		int value = (Integer.parseInt(temp) + 1);
		System.out.println(value);
		sequence.setNextNumber(increasedvalue + "" + value + "" + suffix);

		return sequence;
	}

}
