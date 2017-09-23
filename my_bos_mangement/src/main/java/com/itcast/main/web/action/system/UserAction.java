package com.itcast.main.web.action.system;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itcast.main.domain.system.User;
import com.itcast.main.web.action.common.BaseAction;

@Namespace("/")
@Controller
@ParentPackage("json-default")
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	@Action(value = "user_login", results = {
			@Result(name = "login", type = "redirect", location = "login.html"),
			@Result(name = "success", type = "redirect", location = "index.html") })
	public String login() {
		// 用户名和密码都保存在model中
		// 基于shiro实现登录
		Subject subject = SecurityUtils.getSubject();

		// 用户名和密码信息
		AuthenticationToken token = new UsernamePasswordToken(
				model.getUsername(), model.getPassword());
		try {
			subject.login(token);
			// 登录成功
			// 将用户信息保存到Session
			return SUCCESS;
		} catch (AuthenticationException e) {
			// 登录失败
			e.printStackTrace();
			return LOGIN;
		}

	}
}
