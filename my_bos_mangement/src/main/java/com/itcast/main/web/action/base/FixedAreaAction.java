package com.itcast.main.web.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itcast.main.crm.domain.Customer;
import com.itcast.main.domain.base.FixedArea;
import com.itcast.main.service.base.FixedAreaService;
import com.itcast.main.web.action.common.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {
	// 注入Service
	@Autowired
	private FixedAreaService fixedAreaService;

	// 分页查询定区数据
	@Action(value = "fixedArea_Query", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {

		// 构造分页查询对象
		Pageable pageable = new PageRequest(page - 1, rows);
		// 创建带条件查询对象
		Specification<FixedArea> specification = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 创建一个保存条件的集合对象
				List<Predicate> list = new ArrayList<Predicate>();
				// 添加条件
				if (StringUtils.isNotBlank(model.getId())) {
					// 进行定区编号查询
					Predicate p1 = cb.equal(root.get("id").as(String.class),
							model.getId());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getFixedAreaName())) {
					// 进行定区名称查询
					Predicate p2 = cb.like(
							root.get("fixedAreaName").as(String.class), "%"
									+ model.getFixedAreaName() + "%");
					list.add(p2);
				}
				if (StringUtils.isNotBlank(model.getFixedAreaLeader())) {
					// 进行负责人姓名查询
					Predicate p3 = cb.like(
							root.get("fixedAreaLeader").as(String.class), "%"
									+ model.getFixedAreaLeader() + "%");
					list.add(p3);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		// 调用业务层,返回page
		Page<FixedArea> pageData = fixedAreaService.pageQuery(pageable,
				specification);
		pushPageDataToValueStack(pageData);

		return SUCCESS;
	}

	// 保存定区
	@Action(value = "fixedArea_save", results = { @Result(name = "success", location = "./pages/base/fixed_area.html", type = "redirect") })
	public String save() {
		// 调用业务层
		fixedAreaService.save(model);
		return SUCCESS;
	}

	// 查询未关联客户列表
	@Action(value = "fixedArea_findNoAssociationCusyomers", results = { @Result(name = "success", type = "json") })
	public String findNoAssociationCusyomers() {
		
		// 使用webClient调用webService接口
		Collection<? extends Customer> collection = WebClient
				.create("http://localhost:9002/my_crm_management/services/customerService/noassociationcustomers")
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}

	// 查询已关联当前客户列表
	@Action(value = "fixedArea_findHasAssociation", results = { @Result(name = "success", type = "json") })
	public String findHasAssociationFixedAreaCusyomers() {
		// 使用webClient调用webService接口
		Collection<? extends Customer> collection = WebClient
				.create("http://localhost:9002/my_crm_management/services/customerService/associationfixedareacustomers/"
						+ model.getId()).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}

	// 属性驱动，接收前段传过来的所有id
	private String[] customerIds;

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	// 关联客户到定区
	@Action(value = "fixedArea_customerToArea", results = { @Result(name = "success", location = "./pages/base/fixed_area.html", type = "redirect") })
	public String customerToArea() {
		String customerIdStr = StringUtils.join(customerIds, ",");
		System.out.println(customerIdStr+"====================");
		
		 WebClient
				.create("http://localhost:9002/my_crm_management/services/customerService/associationcustomerstofixedarea?customerIdStr="
						+ customerIdStr + "&fixedAreaId=" + model.getId()).put(
						null);
		 System.out.println(model.getId()+"======================111");
		return SUCCESS;
	}
	
	//属性驱动接收前段传送的数据
	private Integer courierId;
	private Integer takeTimeId;
	
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	//关联快递员到定区
	@Action(value="fixedArea_associationCourierToFixedArea",results={@Result(name="success",location = "./pages/base/fixed_area.html", type = "redirect")})
	public String associationCourierToFixedArea(){
		//调用业务层，完成定区关联快递员
		fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
		return SUCCESS;
	}

}
