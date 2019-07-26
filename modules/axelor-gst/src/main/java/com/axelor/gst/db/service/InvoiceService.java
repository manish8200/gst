package com.axelor.gst.db.service;

import java.math.BigDecimal;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoice_line;

public interface InvoiceService {

	Contact setcontact(Invoice invoice);

	Address setaddress(Invoice invoices);

	Address setshipping(Invoice shipping);

	Address setinvoiceasship(Invoice shipping);

	BigDecimal setvalues(Invoice invoiceLine);

	Invoice_line validateaddress(Invoice invoice, Invoice_line inline);

}
