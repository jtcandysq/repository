/*package com.itcast.main.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itcast.main.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea model);

	Page<FixedArea> findPageData(Specification<FixedArea> specification,
			Pageable pageable);

	void associationCourierToFixedArea(FixedArea model, Integer courierId,
			Integer takeTimeId);

	
}
*/
package com.itcast.main.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itcast.main.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea model);

	Page<FixedArea> pageQuery(Pageable pageable,
			Specification<FixedArea> specification);

	//关联快递员
	void associationCourierToFixedArea(FixedArea model, Integer courierId,
			Integer takeTimeId);

	
}
