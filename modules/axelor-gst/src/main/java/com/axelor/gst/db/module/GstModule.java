package com.axelor.gst.db.module;

import com.axelor.app.AxelorModule;
import com.axelor.gst.db.service.InvoiceService;
import com.axelor.gst.db.service.InvoiceServiceImpl;
import com.axelor.gst.db.service.Sequenceimpl;
import com.axelor.gst.db.service.Sequenceservice;
import com.axelor.gst.db.web.InvoiceController;
import com.axelor.gst.db.web.ProductController;
import com.axelor.gst.db.web.SequenceController;

public class GstModule extends AxelorModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
	 bind(InvoiceService.class).to(InvoiceServiceImpl.class);
	 bind(Sequenceservice.class).to(Sequenceimpl.class);
	 bind(InvoiceController.class);
	 bind(ProductController.class);
	 bind(SequenceController.class);
	}
}
