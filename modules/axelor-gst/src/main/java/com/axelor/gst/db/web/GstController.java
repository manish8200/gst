package com.axelor.gst.db.web;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Address;
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

	public void setitem(ActionRequest req, ActionResponse resp) {

		Invoice_line inline = req.getContext().asType(Invoice_line.class);
		Product product = inline.getProducts();

		
		if(product != null) {
			String name;
			String item;
			String code = new String();
			code = product.getCode();
			name = product.getName();
			item = "[" + code + "]" + name;
			resp.setValue("item", item);

			resp.setValue("price", product.getCostPrice());
			resp.setValue("gstRate", product.getGstRate());
			resp.setValue("HSBN", product.getHSBN());
		}
		else {
			resp.setValue("item", null);
			resp.setValue("price", null);
			resp.setValue("gstRate", null);
			resp.setValue("HSBN", null);
		}
		
	}

	public void setaddress(ActionRequest req, ActionResponse resp) {

		Address address = req.getContext().asType(Address.class);

		String line1, fulladdress,line2;
		String state;
		String city;
		line1 = address.getLine1();
		line2 = address.getLine2();
		city = address.getCity().getName();
		state = address.getState().getName();
		fulladdress = line1 + line2 + " " + city + " " + state;
		resp.setValue("fullAddresss", fulladdress);
	}

	public void setInvoice_as_shipping(ActionRequest req, ActionResponse resp) {

		Invoice invoice = req.getContext().asType(Invoice.class);
		Address address = inservice.setinvoiceasship(invoice);
		resp.setValue("shippingAddress", address);
	}

	public void setInvoiceAmount(ActionRequest req, ActionResponse resp) {

		Invoice invoice = req.getContext().asType(Invoice.class);
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
			resp.setValue("netAmount", netamount);
			resp.setValue("netIGST", netIgst);
			resp.setValue("netCSGT", netCgst);
			resp.setValue("netSGST", netSgst);
			resp.setValue("grossAmount", grossAmount);
		}
	}

	public void setValidation(ActionRequest req, ActionResponse resp) {
		Invoice_line inline = req.getContext().asType(Invoice_line.class);
		Invoice invoice = req.getContext().getParent().asType(Invoice.class);
		Invoice_line invoice_line = inservice.validateaddress(invoice, inline);
		System.out.println(invoice_line);
		resp.setValue("netAmount", invoice_line.getNetAmount());
		resp.setValue("IGST", invoice_line.getIGST());
		resp.setValue("SGST", invoice_line.getSGST());
		resp.setValue("CGST", invoice_line.getCGST());
		resp.setValue("grossAmount", invoice_line.getGrossAmount());

	}
			
	public void setparty(ActionRequest req , ActionResponse resp) {
		//Party party = req.getContext().asType(Party.class);
		Invoice invoice = req.getContext().asType(Invoice.class);
		System.err.println(invoice.getParty());
		Invoice partyInvoice = inservice.validateParty(invoice);
		System.out.println(partyInvoice);
		resp.setValue("partyContact", partyInvoice.getPartyContact());
		resp.setValue("invoiceAddress", partyInvoice.getInvoiceAddress());
		resp.setValue("shippingAddress", partyInvoice.getShippingAddress());
	}
	public void setvalidationParty(ActionRequest req, ActionResponse resp) {
		Invoice invoice = req.getContext().asType(Invoice.class);
		List<Invoice_line> inline = invoice.getInvoiceItems();
		for (Invoice_line invoice_line : inline) {
			Invoice_line invoices = inservice.validateaddress(invoice,invoice_line);
		}
		resp.setValues(invoice);
	}
	}
	
