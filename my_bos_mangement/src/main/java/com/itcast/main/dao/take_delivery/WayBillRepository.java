package com.itcast.main.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.main.domain.take_delivery.WayBill;

public interface WayBillRepository extends JpaRepository<WayBill,Integer> {

	WayBill findByWayBillNum(String wayBillNum);

}
