package com.axelor.gst.db.web;

import java.util.List;

import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Sequence;
import com.axelor.gst.db.repo.SequenceRepository;
import com.axelor.gst.db.service.Sequenceservice;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaModel;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

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
	@Transactional
	public void setrefernce(ActionRequest req, ActionResponse resp) {
		Invoice invoice = req.getContext().asType(Invoice.class);
	//	Sequence sequence = req.getContext().asType(Sequence.class);
		String ref = invoice.getReference();
		if(ref == null) {
	   Sequence sequence = Beans.get(SequenceRepository.class).all().filter("self.model.name = (?1)", "Invoice").fetchOne();
	    resp.setValue("reference", sequence.getNextNumber());
	     sequence = service.increasesequence(sequence);
	    	
	    Beans.get(SequenceRepository.class).save(sequence);
		}
		else {
			
		}
	}
	@Transactional
	public void setpartyref(ActionRequest req ,ActionResponse resp) {
		Party party = req.getContext().asType(Party.class);
		String ref = party.getReferenece();
		if(ref == null) {
		Sequence sequence = Beans.get(SequenceRepository.class).all().filter("self.model.name = (?1)", "Party").fetchOne();
		resp.setValue("referenece", sequence.getNextNumber());
		sequence = service.increasesequence(sequence);
		Beans.get(SequenceRepository.class).save(sequence);
		}
		else {
			
		}
	}
}