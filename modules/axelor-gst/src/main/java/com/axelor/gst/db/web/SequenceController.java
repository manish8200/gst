package com.axelor.gst.db.web;

import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.service.Sequenceservice;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class SequenceController extends JpaSupport {

	@Inject
	Sequenceservice service;
	
	public void setnumber(ActionRequest rq, ActionResponse rsp) {
		Sequence sequence = rq.getContext().asType(Sequence.class);
		String generateseq = sequence.getPrefix();
		String nextno;
		String suffix;
		// String padding = String.format("%03d", sequence.getPadding());
		int userpadding = sequence.getPadding(); 
		int pading = userpadding - 1;
		//System.out.println(pading);
  		suffix = sequence.getSuffix();
		int i ;
		for (i = 0; i<pading; i++) {
			generateseq = generateseq + "0";
		}
		int no = 1;
		System.out.println();
		nextno = generateseq+ no + suffix;		
		rsp.setValue("nextNumber", nextno);
		}
	
	public void setsequence(ActionRequest rq, ActionResponse rsp) {
		
		Sequence sequence = rq.getContext().asType(Sequence.class);
		String prefix = sequence.getPrefix();
		String suffix = sequence.getSuffix();
		String nextnumber = sequence.getNextNumber();
		Integer padding = sequence.getPadding();
			
		int a = prefix.length();
		int b = suffix.length();
		int c = nextnumber.length();
	
		int end = c-b;
	
		for(int i=a;i<end;i++) {
			
		}
		//invoice.getReference();
		
	}
	
	public void seq(ActionRequest rq, ActionResponse rsp) {
		Sequence sequence = rq.getContext().asType(Sequence.class);
		String sequ = service.setseq(sequence);
		rsp.setValue("nextNumber", sequ);
	}
}
 
