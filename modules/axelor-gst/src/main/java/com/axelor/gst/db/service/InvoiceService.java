package com.axelor.gst.db.service;

import java.math.BigDecimal;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoice_line;

public interface InvoiceService {


	Address setinvoiceasship(Invoice shipping);

	Invoice_line validateaddress(Invoice invoice, Invoice_line inline);
	Invoice_line validateinvoice(Invoice invoice , Invoice_line inline);
	
	Invoice validateParty(Invoice invoice);
	
	Invoice setinvoiceitem(Invoice invoice);

}
