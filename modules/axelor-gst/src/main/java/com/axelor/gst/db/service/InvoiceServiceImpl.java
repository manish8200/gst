package com.axelor.gst.db.service;

import java.math.BigDecimal;
import java.util.Collection;
import com.axelor.gst.db.Address;
import com.axelor.gst.db.Company;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Invoice_line;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.State;

public class InvoiceServiceImpl implements InvoiceService {

	@Override
	public Contact setcontact(Invoice invoice) {
		// TODO Auto-generated method stub

		Party party = invoice.getParty();

		Collection<Contact> partyContact = party.getContact();
		Contact contact = null;
		System.out.println(contact);

		for (Contact contacts : partyContact) {
			if (contacts.getType().equals("primary")) {

				contact = contacts;
				// System.out.println(contact);
			}
		}

		return contact;

	}

	@Override
	public Address setaddress(Invoice invoices) {
		// TODO Auto-generated method stub

		Party party = invoices.getParty();

		Collection<Address> partyAddress = party.getAddress();
		Address address = null;

		for (Address addresses : partyAddress) {
			if (addresses.getType().equals("invoice")) {

				address = addresses;
				// System.out.println(address);
			}
		}

		return address;
	}

	@Override
	public Address setshipping(Invoice shipping) {
		// TODO Auto-generated method stub
		Party party = shipping.getParty();

		Collection<Address> partyAddress = party.getAddress();
		Address address = null;

		for (Address addresses : partyAddress) {
			if (addresses.getType().equals("shipping")) {
				address = addresses;
				// System.out.println(address);
			}
		}
		return address;
	}

	@Override
	public Address setinvoiceasship(Invoice shipping) {
		// TODO Auto-generated method stub
		Party party = shipping.getParty();

		Boolean myaddress;
		myaddress = shipping.getUseInvoiceAddress();

		Collection<Address> partyAddress = party.getAddress();

		Address inaddress;
		Address useaddress = null;

		for (Address addresses : partyAddress) {
			if (myaddress) {
				if (addresses.getType().equals("invoice")) {
					inaddress = addresses;
					return inaddress;
				}
			} else if (addresses.getType().equals("shipping")) {
				System.out.println(addresses);
				useaddress = addresses;
				return useaddress;
			}
		}
		return null;
	}

	@Override
	public BigDecimal setvalues(Invoice invoiceLine) {
		// TODO Auto-generated method stub
		BigDecimal netamount = invoiceLine.getNetAmount();
		return netamount;
	}

	@Override
	public Invoice_line validateaddress(Invoice invoice, Invoice_line inline) {
		// TODO Auto-generated method stub
		Address addresses = invoice.getInvoiceAddress();
		State states = addresses.getState();
		Company company = invoice.getCompanies();
		Address address = company.getAddress();
		State addressstate = address.getState();
		BigDecimal gst = null, Sgst = null, Igst = null, netamount, price = null, gstRate, grossamount;
		Integer qty = null;
		int divide = 2;
		gst = inline.getGstRate();
		qty = inline.getQty();
		price = inline.getPrice();
		netamount = price.multiply(new BigDecimal(qty));
		gstRate = gst.divide(new BigDecimal(divide));

		if (addressstate.equals(states)) {
			Sgst = netamount.multiply(gstRate);
			grossamount = netamount.add(Sgst);
		} else {
			Igst = netamount.multiply(gst);
			grossamount = netamount.add(Igst);
		}
		inline.setNetAmount(netamount);
		inline.setSGST(Sgst);
		inline.setCGST(Sgst);
		inline.setIGST(Igst);
		inline.setGrossAmount(grossamount);
		return inline;
	}
}