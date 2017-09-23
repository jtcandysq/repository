package com.itcast.main.web.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.main.domain.take_delivery.Order;
import com.itcast.main.service.take_delivery.OrderService;
import com.itcast.main.web.action.common.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@Namespace("/")
@Controller
@ParentPackage("json-default")
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {
	
	@Autowired
	private OrderService orderService;
	
	@Action(value = "order_findByOrderNum", results = { @Result(name = "success", type = "json") })
	public String findByOrderNum() {
		// 调用业务层,查询Order信息
	Order order = 	orderService.findByOrderNum(model.getOrderNum());
	Map<String,Object> result = new HashMap<String, Object>();
		if(order==null){
			//订单不存在
			result.put("success", false);
		}else{
			//订单号存在
			result.put("success", true);
			result.put("orderData", order);
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
}
