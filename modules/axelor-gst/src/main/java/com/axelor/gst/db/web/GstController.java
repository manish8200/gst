package com.axelor.gst.db.web;

import java.math.BigDecimal;

import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Invoice_line;
import com.axelor.gst.db.Product;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class GstController extends JpaSupport{

	public void copyGst(ActionRequest rq , ActionResponse rsp) {
		/*
		Product product = rq.getContext().asType(Product.class);
		Invoice_line inline = rq.getContext().asType(Invoice_line.class);
		
		String p = product.getName();
		BigDecimal s = product.getGst_rate();
	
		rsp.setValue("item", p);
		rsp.setValue("Gst_rate", s);
		}
	
	public void amount(ActionRequest rq, ActionResponse rsp) {
		
		Invoice_line inlines = rq.getContext().asType(Invoice_line.*/
	}
}
