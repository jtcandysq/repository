package com.itcast.main.service.take_delivery.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.main.dao.take_delivery.WayBillRepository;
import com.itcast.main.domain.take_delivery.WayBill;
import com.itcast.main.service.take_delivery.WayBillService;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

	@Autowired
	private WayBillRepository wayBillRepository;

	@Override
	public void save(WayBill wayBill) {
		// 判断运单号是否存在
		WayBill persistWayBill = wayBillRepository.findByWayBillNum(wayBill
				.getWayBillNum());
		if (persistWayBill == null) {//说明运单是不存在的，则进行保存操作
			/*
			 * 如果运单号存在 ，我们需要 更新操作
			 * 如果运单号不存在 ，我们需要 保存操作
			 * 
			 */
			wayBill.setSignStatus(1);// 待发货(如果是新增的运单，则创建运单时，先将运行类型设置为默认：待发货）
//			wayBillRepository.save(wayBill);
		}else{//如果进入到else，说明运单号是存在 的，则进行更新操作
			//注意：操作过程：1）输入运单号，回显运单号查询出来的运单信息   2）更新该运单中的其他信息（同时也可以修改运单前已经存在 的信息）  3）提交运单信息
			//只将根据运单编号查询出来的persistWayBill中的ID设置到wayBill中
			wayBill.setId(persistWayBill.getId());
		}
		wayBillRepository.save(wayBill);//直接调save的方法
		
		//第二种解决方法
		/*if(persistWayBill==null){//运单不存在
			wayBillRepository.save(wayBill);//直接调save的方法
		}else{
			//运单存在
			try {
				Integer id = persistWayBill.getId();
				BeanUtils.copyProperties(persistWayBill, wayBill);
				persistWayBill.setId(id);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} 
		}*/
	}

	@Override
	public Page<WayBill> findPageData(Pageable pageable) {
		return wayBillRepository.findAll(pageable);
	}

	@Override
	public WayBill findByWayBillNum(String wayBillNum) {
		return wayBillRepository.findByWayBillNum(wayBillNum);
	}

}
