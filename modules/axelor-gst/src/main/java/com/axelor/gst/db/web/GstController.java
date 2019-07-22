package com.axelor.gst.db.web;

import java.math.BigDecimal;

import com.axelor.gst.db.Product;
import com.axelor.gst.db.Product_Category;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class GstController {

	public void put(ActionRequest rq, ActionResponse rsp) {
		
		Product p = rq.getContext().asType(Product.class);
		Product_Category pc = rq.getContext().getParent().asType(Product_Category.class);
		
		if(p.getGst_rate() == null) {
				
System.out.println("null");
	}
	
}

}