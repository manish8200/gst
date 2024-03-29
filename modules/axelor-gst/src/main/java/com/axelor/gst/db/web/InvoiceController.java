package com.axelor.gst.db.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Company;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.service.InvoiceService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class InvoiceController extends JpaSupport {

	@Inject
	InvoiceService inservice;

	public void setitem(ActionRequest req, ActionResponse resp) {

		InvoiceLine inline = req.getContext().asType(InvoiceLine.class);
		Product product = inline.getProducts();

		if (product != null) {
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
		} else {
			resp.setValue("item", null);
			resp.setValue("price", null);
			resp.setValue("gstRate", null);
			resp.setValue("HSBN", null);
		}

	}

	public void setInvoice_as_shipping(ActionRequest req, ActionResponse resp) {

		Invoice invoice = req.getContext().asType(Invoice.class);
		Address address = inservice.setinvoiceasship(invoice);
		resp.setValue("shippingAddress", address);
	}

	public void setInvoiceAmount(ActionRequest req, ActionResponse resp) {

		Invoice invoice = req.getContext().asType(Invoice.class);
		BigDecimal netamount = BigDecimal.ZERO;
		BigDecimal netIgst = BigDecimal.ZERO, netCgst = BigDecimal.ZERO, netSgst = BigDecimal.ZERO,
				grossAmount = BigDecimal.ZERO;
		
		Company	invoiceComapny = invoice.getCompanies() ;
		
		Collection<InvoiceLine> inline = invoice.getInvoiceItems();
		if(invoiceComapny != null && invoice.getParty() != null && inline != null) {
		
		for (InvoiceLine InvoiceLine : inline) {
			netamount = netamount.add(InvoiceLine.getNetAmount());
			netIgst = netIgst.add(InvoiceLine.getIGST());
			netCgst = netCgst.add(InvoiceLine.getCGST());
			netSgst = netSgst.add(InvoiceLine.getSGST());
			grossAmount = grossAmount.add(InvoiceLine.getGrossAmount());

			// System.out.println(netIgst);

		}
		resp.setValue("netAmount", netamount);
		resp.setValue("netIGST", netIgst);
		resp.setValue("netCSGT", netCgst);
		resp.setValue("netSGST", netSgst);
		resp.setValue("grossAmount", grossAmount);
	}
	else {
		resp.setValue("netAmount", BigDecimal.ZERO);
		resp.setValue("netIGST", BigDecimal.ZERO);
		resp.setValue("netSGST", BigDecimal.ZERO);
		resp.setValue("netCSGT", BigDecimal.ZERO);
		resp.setValue("grossAmount", BigDecimal.ZERO);
	}	
	}

	public void setValidationInvoiceLine(ActionRequest req, ActionResponse resp) {
		InvoiceLine inline = req.getContext().asType(InvoiceLine.class);
		Invoice invoice = req.getContext().getParent().asType(Invoice.class);
		InvoiceLine InvoiceLine = inservice.validateaddress(invoice, inline);
		System.out.println(InvoiceLine);
		resp.setValue("netAmount", InvoiceLine.getNetAmount());
		resp.setValue("IGST", InvoiceLine.getIGST());
		resp.setValue("SGST", InvoiceLine.getSGST());
		resp.setValue("CGST", InvoiceLine.getCGST());
		resp.setValue("grossAmount", InvoiceLine.getGrossAmount());

	}
	

	public void setparty(ActionRequest req, ActionResponse resp) {
		// Party party = req.getContext().asType(Party.class);
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
		
		List<InvoiceLine> inlineList = invoice.getInvoiceItems();
		List<InvoiceLine> invoiceline = new ArrayList<InvoiceLine>();

		if(inlineList != null) {
		for (InvoiceLine InvoiceLine : inlineList) {
		InvoiceLine invoices = inservice.validateaddress(invoice, InvoiceLine);
			invoiceline.add(invoices);
		}
		invoice.setInvoiceItems(invoiceline);
		}else {
			invoice.setInvoiceItems(null);
		}
		resp.setValues(invoice);
	
		}
	
	
	 {
		
	}
}
