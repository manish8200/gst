package com.axelor.gst.db.web;

import java.math.BigDecimal;
import java.util.Collection;

import org.quartz.simpl.ZeroSizeThreadPool;

import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoice_line;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.service.InvoiceService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class GstController extends JpaSupport {
	
	@Inject
	InvoiceService inservice;

	public void setitem(ActionRequest rq, ActionResponse rsp) {

		Invoice_line inline = rq.getContext().asType(Invoice_line.class);
		Product product = inline.getProducts();

		
		if(product != null) {
			String name;
			String item;
			String code = new String();
			code = product.getCode();
			name = product.getName();
			item = "[" + code + "]" + name;
			rsp.setValue("item", item);

			rsp.setValue("price", product.getCostPrice());
			rsp.setValue("gstRate", product.getGstRate());
		}
		else {
			rsp.setValue("item", null);
			rsp.setValue("price", null);
			rsp.setValue("gstRate", null);
		}
		
	}

	public void setaddress(ActionRequest rq, ActionResponse rsp) {

		Address address = rq.getContext().asType(Address.class);

		String line1, fulladdress,line2;
		String state;
		String city;
		line1 = address.getLine1();
		line2 = address.getLine2();
		city = address.getCity().getName();
		state = address.getState().getName();
		fulladdress = line1 + line2 + " " + city + " " + state;
		rsp.setValue("fullAddresss", fulladdress);
	}

	public void setInvoice_as_shipping(ActionRequest rq, ActionResponse rsp) {

		Invoice invoice = rq.getContext().asType(Invoice.class);
		Address address = inservice.setinvoiceasship(invoice);
		rsp.setValue("shippingAddress", address);
	}

	public void setInvoiceAmount(ActionRequest rq, ActionResponse rsp) {

		Invoice invoice = rq.getContext().asType(Invoice.class);
		BigDecimal netamount = BigDecimal.ZERO;
		BigDecimal netIgst = BigDecimal.ZERO, netCgst = BigDecimal.ZERO, netSgst= BigDecimal.ZERO, grossAmount = BigDecimal.ZERO;
		
		Collection<Invoice_line> inline = invoice.getInvoiceItems();
		for (Invoice_line invoice_line : inline) {
		 netamount= netamount.add(invoice_line.getNetAmount());
			netIgst = netIgst.add(invoice_line.getIGST());
			netCgst = netCgst.add(invoice_line.getCGST());
			netSgst = netSgst.add(invoice_line.getSGST());
			grossAmount = grossAmount.add(invoice_line.getGrossAmount());
			
			// System.out.println(netIgst);
			rsp.setValue("netAmount", netamount);
			rsp.setValue("netIGST", netIgst);
			rsp.setValue("netCSGT", netCgst);
			rsp.setValue("netSGST", netSgst);
			rsp.setValue("grossAmount", grossAmount);
		}
	}

	public void setValidation(ActionRequest rq, ActionResponse rsp) {
		Invoice_line inline = rq.getContext().asType(Invoice_line.class);
		Invoice invoice = rq.getContext().getParent().asType(Invoice.class);
		Invoice_line invoice_line = inservice.validateaddress(invoice, inline);
		System.out.println(invoice_line);
		rsp.setValue("netAmount", invoice_line.getNetAmount());
		rsp.setValue("IGST", invoice_line.getIGST());
		rsp.setValue("SGST", invoice_line.getSGST());
		rsp.setValue("CGST", invoice_line.getCGST());
		rsp.setValue("grossAmount", invoice_line.getGrossAmount());

	}
			
	public void setparty(ActionRequest rq , ActionResponse rsp) {
		//Party party = rq.getContext().asType(Party.class);
		Invoice invoice = rq.getContext().asType(Invoice.class);
		Invoice partyInvoice = inservice.validateParty(invoice);
		System.out.println(partyInvoice);
		rsp.setValue("partyContact", partyInvoice.getPartyContact());
		rsp.setValue("invoiceAddress", partyInvoice.getInvoiceAddress());
		rsp.setValue("shippingAddress", partyInvoice.getShippingAddress());
	}
	
}