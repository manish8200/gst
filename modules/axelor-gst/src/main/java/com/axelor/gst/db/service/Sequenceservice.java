package com.axelor.gst.db.service;

import com.axelor.gst.db.Sequence;

public interface Sequenceservice {

	Sequence setseq(Sequence sequence);
	
	Sequence increasesequence(Sequence sequence);
}
