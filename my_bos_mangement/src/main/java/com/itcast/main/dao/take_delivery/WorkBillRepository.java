package com.itcast.main.dao.take_delivery;


import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.main.domain.take_delivery.WorkBill;

public interface WorkBillRepository extends JpaRepository<WorkBill, Integer> {

}
