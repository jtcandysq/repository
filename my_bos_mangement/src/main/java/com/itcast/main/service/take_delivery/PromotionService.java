package com.itcast.main.service.take_delivery;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.main.domain.page.PageBean;
import com.itcast.main.domain.take_delivery.Promotion;

public interface PromotionService {

	//保存宣传任务
	void save(Promotion model);

	//分页查询
	Page<Promotion> findPageData(Pageable pageable);

	// 根据page和rows 返回分页数据
		@Path("/pageQuery")
		@GET
		@Produces({ "application/xml", "application/json" })
		PageBean<Promotion> findPageData(@QueryParam("page") int page,
				@QueryParam("rows") int rows);
		
		//根据id查询
		@Path("/promotion/{id}")
		@GET
		@Produces({ "application/xml", "application/json" })
		Promotion findById(@PathParam("id") Integer id);

		void updateStatus(Date date);
}
