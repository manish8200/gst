package com.axelor.gst.db.web;

import java.util.ArrayList;
import java.util.List;

import com.axelor.db.JpaSupport;
import com.axelor.gst.db.Invoice_line;
import com.axelor.gst.db.Product;
import com.axelor.gst.db.repo.ProductRepository;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.persist.Transactional;

public class ProductController extends JpaSupport {

	
	@Transactional
	public void getproductInvoiceItem(ActionRequest req, ActionResponse resp) {
		
		List<Invoice_line> inlinelist = new ArrayList<Invoice_line>();
		List<Integer> productIds = (List<Integer>) req.getContext().get("productId");
		
		List<Product> productList = Beans.get(ProductRepository.class).all().filter("self.id in (?1)", productIds).fetch();
		for (Product product2 : productList) {
			String code = "[" + product2.getCode() + "]" + product2.getName();
			Invoice_line inline = new Invoice_line();	
			inline.setItem(code);
			inline.setProducts(product2);
			inline.setGstRate(product2.getGstRate());
			inline.setPrice(product2.getCostPrice());
			inline.setHSBN(product2.getHSBN());
		inlinelist.add(inline);
		}
		resp.setValue("invoiceItems",inlinelist);
	}
}