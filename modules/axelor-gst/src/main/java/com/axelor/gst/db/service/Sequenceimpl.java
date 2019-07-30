package com.axelor.gst.db.service;

import com.axelor.gst.db.Sequence;

public class Sequenceimpl implements Sequenceservice{

	@Override
	public String setseq(Sequence sequence) {
		// TODO Auto-generated method stub
		String prefix = sequence.getPrefix();
		String suffix = sequence.getSuffix();
		int padding = sequence.getPadding();
		
		int userpadding = padding-1;
		for (int i = 0; i<userpadding; i++) {
			prefix = prefix + "0";
		}
		int no = 1;
		String nextno = prefix + no + suffix;
	
		return nextno;
	}

}
