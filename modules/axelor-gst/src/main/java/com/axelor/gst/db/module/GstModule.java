package com.axelor.gst.db.module;

import com.axelor.app.AxelorModule;
import com.axelor.gst.db.service.InvoiceService;
import com.axelor.gst.db.service.InvoiceServiceImpl;
import com.axelor.gst.db.web.GstController;

public class GstModule extends AxelorModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
	 bind(InvoiceService.class).to(InvoiceServiceImpl.class);
	 bind(GstController.class);
	}
}
