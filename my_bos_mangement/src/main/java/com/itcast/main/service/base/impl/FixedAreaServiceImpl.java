/*package com.itcast.main.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.main.dao.base.CourierRepository;
import com.itcast.main.dao.base.FixedAreaRepository;
import com.itcast.main.dao.base.TakeTimeRepository;
import com.itcast.main.domain.base.Courier;
import com.itcast.main.domain.base.FixedArea;
import com.itcast.main.domain.base.TakeTime;
import com.itcast.main.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	// 注入DAO
	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Override
	public void save(FixedArea fixedArea) {
		fixedAreaRepository.save(fixedArea);
	}

	@Override
	public Page<FixedArea> findPageData(Specification<FixedArea> specification,
			Pageable pageable) {
		return fixedAreaRepository.findAll(specification, pageable);
	}

	@Autowired
	private CourierRepository courierRepository;
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public void associationCourierToFixedArea(FixedArea fixedArea,
			Integer courierId, Integer takeTimeId) {
		FixedArea persistFixedArea = fixedAreaRepository.findOne(fixedArea
				.getId());
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		// 快递员 关联到 定区上
		persistFixedArea.getCouriers().add(courier);

		// 将收派标准 关联到 快递员上
		courier.setTakeTime(takeTime);
	}


}
*/

package com.itcast.main.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.main.dao.base.CourierRepository;
import com.itcast.main.dao.base.FixedAreaRepository;
import com.itcast.main.dao.base.TakeTimeRepository;
import com.itcast.main.domain.base.Courier;
import com.itcast.main.domain.base.FixedArea;
import com.itcast.main.domain.base.TakeTime;
import com.itcast.main.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	//注入dao
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	@Override
	public void save(FixedArea model) {
		fixedAreaRepository.save(model);
	}
	
	//分页查询
	@Override
	public Page<FixedArea> pageQuery(Pageable pageable,
			Specification<FixedArea> specification) {
		Page<FixedArea> pageData = fixedAreaRepository.findAll(specification, pageable);
		return pageData;
	}

	
	//注入快递员的dao
	@Autowired
	private CourierRepository courierRepository;
	//注入收派标准的dao
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	//关联快递员
	@Override
	public void associationCourierToFixedArea(FixedArea fixedArea,
			Integer courierId, Integer takeTimeId) {
		
		//查询定区、快递员、收派标准对象
		FixedArea persistFixeArea = fixedAreaRepository.findOne(fixedArea.getId());
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		//快递员关联到定区上----fixedArea是外键维护
		persistFixeArea.getCouriers().add(courier);
		
		//将收派标准关联到快递员上-----courier快递员是外键维护
		courier.setTakeTime(takeTime);
	}

}
