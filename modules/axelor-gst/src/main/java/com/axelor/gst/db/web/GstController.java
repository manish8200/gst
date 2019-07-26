package com.axelor.gst.db.web;

import java.math.BigDecimal;
import java.util.Collection;
import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoice_line;
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
		Product product1 = inline.getProducts();

		{
			String name;
			String item;

			String code = new String();
			code = product.getCode();
			name = product.getName();

			item = "[" + code + "]" + name;
			rsp.setValue("item", item);
		}

		{
			BigDecimal cgst;
			cgst = product1.getGstRate();

			rsp.setValue("gstRate", cgst);
		}
	}

	public void setaddress(ActionRequest rq, ActionResponse rsp) {

		Address address = rq.getContext().asType(Address.class);

		String line1, fulladdress;
		String state;
		String city;
		line1 = address.getLine1();
		city = address.getCity().getName();
		state = address.getState().getName();
		fulladdress = line1 + " " + city + " " + state;
		rsp.setValue("fullAddresss", fulladdress);
	}

	public void setInvoice(ActionRequest rq, ActionResponse rsp) {
		Invoice invoice = rq.getContext().asType(Invoice.class);
		Contact contact = inservice.setcontact(invoice);
		rsp.setValue("partyContact", contact);
	}

	public void setInvoiceAddress(ActionRequest rq, ActionResponse rsp) {
		Invoice invoices = rq.getContext().asType(Invoice.class);
		Address address = inservice.setaddress(invoices);
		// System.out.println(address);
		rsp.setValue("invoiceAddress", address);
	}

	public void setShippingaddress(ActionRequest rq, ActionResponse rsp) {
		Invoice invoice = rq.getContext().asType(Invoice.class);
		Address addresses = inservice.setshipping(invoice);
		rsp.setValue("shippingAddress", addresses);
	}

	public void setInvoice_as_shipping(ActionRequest rq, ActionResponse rsp) {

		Invoice invoice = rq.getContext().asType(Invoice.class);
		Address address = inservice.setinvoiceasship(invoice);
		rsp.setValue("shippingAddress", address);
	}

	public void setInvoiceAmount(ActionRequest rq, ActionResponse rsp) {

		Invoice invoice = rq.getContext().asType(Invoice.class);
		BigDecimal netamount;
		BigDecimal netIgst, netCgst, netSgst, grossAmount;

		Collection<Invoice_line> inline = invoice.getInvoiceItems();
		for (Invoice_line invoice_line : inline) {
			netamount = invoice_line.getNetAmount();
			netIgst = invoice_line.getIGST();
			netCgst = invoice_line.getCGST();
			netSgst = invoice_line.getSGST();
			grossAmount = invoice_line.getSGST();
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

}
