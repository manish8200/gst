package com.axelor.gst.db.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.axelor.gst.db.Contact;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Party;

public class InvoiceServiceImpl implements InvoiceService {

	@Override
	public Contact setcontact(Invoice invoice) {
		// TODO Auto-generated method stub

		Party party = invoice.getPartys();

		Collection<Contact> partyContact = party.getContact();
		Contact contact = null;
		String i = null;

		for (Contact contacts : partyContact) {
			if (contacts.getType().equals("Primary")) {
				contacts.getName();
				contact = contacts;
			}
		}

		return contact;

	}

}
