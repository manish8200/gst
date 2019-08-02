package com.axelor.gst.db.service;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;

public interface InvoiceService {


	Address setinvoiceasship(Invoice shipping);
	InvoiceLine validateaddress(Invoice invoice, InvoiceLine inline);
	Invoice validateParty(Invoice invoice);
	
}
