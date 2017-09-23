package com.itcast.main.web.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import com.itcast.main.domain.take_delivery.WayBill;
import com.itcast.main.service.take_delivery.WayBillService;
import com.itcast.main.web.action.common.BaseAction;
import com.opensymphony.xwork2.ActionContext;

//运单管理
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class WayBillAction extends BaseAction<WayBill> {

	// 日志对象
	private static final Logger LOGGER = Logger.getLogger(WayBillAction.class);

	@Autowired
	private WayBillService wayBillService;

	@Action(value = "waybill_save", results = { @Result(name = "success", type = "json") })
	public String save() {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 去除没有id的order对象
			if (model.getOrder() != null
					&& (model.getOrder().getId() == null || model.getOrder()
							.getId() == 0)) {
				model.setOrder(null);
			}

			// 保存成功
			wayBillService.save(model);
			result.put("success", true);
			result.put("msg", "保存运单成功");
			LOGGER.info("保存运单成功，运单号：" + model.getWayBillNum());
		} catch (Exception e) {
			// 保存失败
			result.put("success", false);
			result.put("msg", "保存运单失败!");
			LOGGER.error("保存运单失败，运单号：" + model.getWayBillNum(), e);
			e.printStackTrace();
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}

	
	@Action(value = "waybill_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		// 无条件查询
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(
				new Sort.Order(Sort.Direction.DESC, "id")));

		//调用业务层 进行查询
		Page<WayBill> pageData = wayBillService.findPageData(pageable);
		//压入值栈返回
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}
	
	@Action(value="waybill_fandByWayBillNum",results={@Result(name="success",type="json")})
	public String fandByWaybillNum(){
		
		WayBill wayBill = wayBillService.findByWayBillNum(model.getWayBillNum());
		Map<String,Object> result = new HashMap<String, Object>();
		if(wayBill==null){
			//运单不存在
			result.put("success", false);
		}else{
			//运单号存在
			result.put("success", true);
			result.put("wayBillData", wayBill);
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
}
