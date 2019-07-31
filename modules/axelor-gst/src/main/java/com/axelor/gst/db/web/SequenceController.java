package com.axelor.gst.db.web;

import com.axelor.db.JpaSupport;
import com.axelor.db.Query;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.gst.db.service.Sequenceservice;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class SequenceController extends JpaSupport {

	@Inject
	Sequenceservice service;
	
		
	public void seq(ActionRequest req, ActionResponse resp) {
		Sequence sequence = req.getContext().asType(Sequence.class);
		Sequence sequ = service.setseq(sequence);
		resp.setValue("nextNumber", sequ.getNextNumber());
	}
	
	public void incrementseq(ActionRequest req , ActionResponse resp) {
		Sequence sequence = req.getContext().asType(Sequence.class);
		Sequence seq = service.increasesequence(sequence);
		System.err.println(seq.getNextNumber());
		resp.setValue("nextNumber", seq.getNextNumber());
	}
	public void setrefernce(ActionRequest req, ActionResponse resp) {
		Invoice invoice = req.getContext().asType(Invoice.class);
	    Query<Sequence> sequence = Beans.get(SequenceRepository.class).all().filter("self.model.name = (?1)", "Invoice");
	    
	    
	
	}
}