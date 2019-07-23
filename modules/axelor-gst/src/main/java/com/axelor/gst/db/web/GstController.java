package com.axelor.gst.db.web;

import java.math.BigDecimal;

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
			cgst = product1.getGst_rate();

			rsp.setValue("gst_ratte", cgst);
		}
	}

	public void setNetAmount(ActionRequest rq, ActionResponse rsp) {

		Invoice_line inline = rq.getContext().asType(Invoice_line.class);

		Integer qty;
		BigDecimal price, gst, Igst, gst_rate;

		gst_rate = inline.getGst_ratte();
		qty = inline.getQty();
		price = inline.getPrice();
		gst = price.multiply(new BigDecimal(qty));
		Igst = gst.multiply(gst_rate);
		rsp.setValue("net_amount", gst);
		rsp.setValue("IGST", Igst);

	}
	/*
	 * public void setIgst(ActionRequest rq , ActionResponse rsp) {
	 * 
	 * Invoice_line inline = rq.getContext().asType(Invoice_line.class); BigDecimal
	 * a,b,c; a= inline.getNet_amount(); b= inline.getGst_ratte();
	 * 
	 * c = a.multiply(b);
	 * 
	 * rsp.setValue("IGST", c); }
	 */

	public void setSgst(ActionRequest rq, ActionResponse rsp) {
		Invoice_line inline = rq.getContext().asType(Invoice_line.class);

		BigDecimal net_amount, gst_rate, gst, Cgst, gross_amount;

		int divide = 2;

		net_amount = inline.getNet_amount();
		gst_rate = inline.getGst_ratte();
		gst = gst_rate.divide(new BigDecimal(divide));
		Cgst = net_amount.multiply(gst);
		gross_amount = net_amount.add(Cgst);

		rsp.setValue("SGST", Cgst);
		rsp.setValue("CGST", Cgst);
		rsp.setValue("gross_amount", gross_amount);
	}

	public void setaddress(ActionRequest rq, ActionResponse rsp) {

		Address address = rq.getContext().asType(Address.class);

		String a, d;
		String c;
		String b;

		a = address.getLine1();
		b = address.getCity().getName();
		c = address.getState().getName();

		d = a + " " + b + " " + c;

		rsp.setValue("full_addresss", d);
	}

	public void setInvoice(ActionRequest rq, ActionResponse rsp) {
		Invoice invoice = rq.getContext().asType(Invoice.class);

		Contact contact = inservice.setcontact(invoice);

		rsp.setValue("party_contact", contact.getName());

	}
}
