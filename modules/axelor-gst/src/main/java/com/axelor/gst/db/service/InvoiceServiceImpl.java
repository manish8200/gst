package com.axelor.gst.db.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.apache.poi.xslf.model.geom.AddDivideExpression;

import com.axelor.gst.db.Address;
import com.axelor.gst.db.Company;
import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.InvoiceLine;
import com.axelor.gst.db.Party;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.State;

public class InvoiceServiceImpl implements InvoiceService {

	@Override
	public Address setinvoiceasship(Invoice shipping) {
		// TODO Auto-generated method stub
		Party party = shipping.getParty();

		Boolean myaddress;
		myaddress = shipping.getUseInvoiceAddress();

		List<Address> partyAddress = party.getAddressList();

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
	public InvoiceLine validateaddress(Invoice invoice, InvoiceLine inline) {
		// TODO Auto-generated method stub
		Party party = invoice.getParty();
		Company invoicecompany = invoice.getCompanies();
		Address invoiceaddress= invoice.getInvoiceAddress();
		
		if(party != null && invoiceaddress != null && invoicecompany !=null ) {
		Address addresses = invoice.getInvoiceAddress();
		State states = addresses.getState();
		Company company = invoice.getCompanies();
		Address address = company.getAddress();
		State addressstate = address.getState();
		BigDecimal gst = null, Sgst = null, Igst = null, netamount, price = null, gstRate, grossamount;
		Integer qty = null;
		int divide = 2;
		int percent =100; 
		gst = inline.getGstRate().divide(new BigDecimal(percent));
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
		else {
			
			inline.setNetAmount(BigDecimal.ZERO);
			inline.setSGST(BigDecimal.ZERO);
			inline.setCGST(BigDecimal.ZERO);
			inline.setIGST(BigDecimal.ZERO);
			inline.setGrossAmount(BigDecimal.ZERO);
			
		}
		return inline;
	}

	@Override
	public Invoice validateParty(Invoice invoice) {
		// TODO Auto-generated method stub
		
		
		Contact contacts = null;
		Party party = invoice.getParty();
		
		if(party != null) {
		
		Collection<Contact>partycontacts = party.getContactList();
		for (Contact contact : partycontacts) {
			if(contact.getType().equals("primary")) {
				contacts = contact;
			}
		}
		Address invoiceaddreess = null;
		Address shippingaddress = null;
		
		Collection<Address>partyaddress = party.getAddressList();
		for (Address address : partyaddress) {
			if(address.getType().equals("invoice")) {
				invoiceaddreess = address;
			}if(address.getType().equals("shipping")) {
				shippingaddress = address;
			}
		}
		invoice.setPartyContact(contacts);
		invoice.setInvoiceAddress(invoiceaddreess);
		invoice.setShippingAddress(shippingaddress);
		return invoice;
		}
		else
		{
			invoice.setPartyContact(null);
			invoice.setInvoiceAddress(null);
			invoice.setShippingAddress(null);
				return invoice;
		}
		}


	}

		
