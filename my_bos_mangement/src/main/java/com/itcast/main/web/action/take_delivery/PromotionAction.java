package com.itcast.main.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itcast.main.domain.take_delivery.Promotion;
import com.itcast.main.service.take_delivery.PromotionService;
import com.itcast.main.web.action.common.BaseAction;

/**
 * 活动宣传、管理
 * @author admin
 *
 */
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class PromotionAction extends BaseAction<Promotion> {

	private File titleImgFile;
	private String titleImgFileFileName;

	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}

	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}

	@Autowired
	private PromotionService promotionService;

	@Action(value = "promotion_save", results = { 
			@Result(name = "success", type = "redirect", 
					location = "./pages/take_delivery/promotion.html") })
	public String save() throws IOException {
		// 宣传图上传，在数据表保存宣传图路径
		String savePath = ServletActionContext.getServletContext().getRealPath(
				"/upload/");
		String saveUrl = ServletActionContext.getRequest().getContextPath()
				+ "/upload/";
		// 生成随机图片名
		UUID uuid = UUID.randomUUID();
		// 获取文件的扩展名
		String ext = titleImgFileFileName
				.substring(titleImgFileFileName.lastIndexOf("."));
		// 得到随机图片名
		String randomFileName = uuid + ext;
		// 保存图片(绝对路径)
		File destFile = new File(savePath + "/" + randomFileName);
		System.out.println(destFile.getAbsolutePath());
		FileUtils.copyFile(titleImgFile, destFile);
		
		//将保存路径 相对工程web访问路径，保存model中
		model.setTitleImg(ServletActionContext.getRequest().getContextPath()+"/upload/"+randomFileName);
		
		//调用业务层，完成活动任务数据保存
		promotionService.save(model);

		return SUCCESS;
	}
	
	@Action(value="promotion_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//构造分页查询参数
		Pageable pageable = new PageRequest(page-1,rows);
		//调用业务层 完成查询
		Page<Promotion> pageData = promotionService.findPageData(pageable);		
		//压入值栈
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}
}
