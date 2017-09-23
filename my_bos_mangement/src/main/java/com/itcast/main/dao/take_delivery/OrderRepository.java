package com.itcast.main.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.main.domain.take_delivery.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	Order findByOrderNum(String orderNum);

}
