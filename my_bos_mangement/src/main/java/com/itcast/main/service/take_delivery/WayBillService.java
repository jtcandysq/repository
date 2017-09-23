package com.itcast.main.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itcast.main.domain.take_delivery.WayBill;

public interface WayBillService {

	//保存运单
	void save(WayBill wayBill);

	//无条件分页查询
	Page<WayBill> findPageData(Pageable pageable);

	WayBill findByWayBillNum(String wayBillNum);

}
