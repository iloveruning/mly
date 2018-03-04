/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.hfutonline.mly.modules.sys.controller;


import com.google.code.kaptcha.Producer;
import com.hfutonline.mly.common.utils.Result;
import com.hfutonline.mly.modules.sys.entity.SysUser;
import com.hfutonline.mly.modules.sys.service.ISysUserService;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * 登录相关
 * 
 * @author chenliangliang
 * @date 2018/2/18
 */
@Controller
@Slf4j
public class SysLoginController {


	private ISysUserService userService;


	private Producer producer;

	@Autowired
	protected SysLoginController(ISysUserService userService,Producer producer){
		this.userService=userService;
		this.producer=producer;
	}

	
	@GetMapping("/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response){
		try {
			System.out.println();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			//response.setContentType("image/gif");
			response.setContentType("image/jpeg");

			//生成文字验证码
			String text = producer.createText();
			//生成图片验证码
			BufferedImage image = producer.createImage(text);

			ImageIO.write(image,"jpg",response.getOutputStream());
			/**
			 * gif格式动画验证码
			 * 宽，高，位数。
			 */
//			Captcha captcha = new GifCaptcha(146, 33, 4);
//			//输出
//			captcha.out(response.getOutputStream());
			//存入Session
			long expired = System.currentTimeMillis() + 60000;
			//session.removeAttribute("v_code");
			request.getSession(true).setAttribute("v_code", text + "#" + expired);
			//System.out.println(captcha.text() + "#" + expired);
		} catch (Exception e) {
			log.error("获取验证码异常",e);
		}
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Result login(@RequestParam("username") String username, @RequestParam("password") String password
			, @RequestParam("captcha") String captcha, HttpSession session) {

		//对验证码进行验证
		if (captcha == null) {
			return Result.error(HttpStatus.BAD_REQUEST,"验证码不存在！");
		}
		String code_expired = (String) session.getAttribute("v_code");
		if (code_expired == null) {
			return Result.error("验证码不存在！");
		}

		String[] str = StringUtils.split(code_expired, "#");
		String vcode = str[0];
		if (!StringUtils.equalsIgnoreCase(vcode, captcha)) {
			log.error("验证码错误");
			return Result.error("验证码错误");
		}
		long expired = Long.parseLong(str[1]);
		if (expired < System.currentTimeMillis()) {
			log.error("验证码已过期");
			return Result.error("验证码已过期");
		}

		session.removeAttribute("v_code");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		Subject user = SecurityUtils.getSubject();

		try {
			//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			//所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			log.info("对用户[{}]进行登录验证..验证开始", username);
			user.login(token);
			log.info("对用户[{}]进行登录验证..验证通过", username);
		} catch (UnknownAccountException uae) {
			log.info("对用户[{}]进行登录验证..验证未通过,未知账户", username);
			return Result.error("未知账户");
		} catch (IncorrectCredentialsException ice) {
			log.info("对用户[{}]进行登录验证..验证未通过,错误的凭证", username);
			return Result.error("密码不正确");
		} catch (LockedAccountException lae) {
			log.info("对用户[{}]进行登录验证..验证未通过,账户已锁定", username);
			return Result.error("账户已锁定,请联系管理员");
		} catch (ExcessiveAttemptsException eae) {
			log.info("对用户[{}]进行登录验证..验证未通过,错误次数过多", username);
			return Result.error("用户名或密码错误次数过多,请10分钟后再来登录");
		} catch (AuthenticationException ae) {
			//通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			log.info("对用户[{}]进行登录验证..验证未通过,堆栈轨迹如下", username);
			ae.printStackTrace();
			return Result.error("用户名或密码不正确");
		}

		return Result.OK();

	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroKit.logout();
		return "redirect:login";
	}


	@PostMapping("/register")
	public String register(@RequestParam("name") String name, @RequestParam("pw") String pw,
						   Model model) {

		SysUser user = new SysUser();
		user.setUsername(name);
		String salt = ShiroKit.getRandomSalt(16);
		user.setSalt(salt);
		String password = ShiroKit.md5(pw, salt);
		user.setPassword(password);
		if (userService.insert(user)) {
			return "redirect:login";
		} else {
			model.addAttribute("msg", "注册失败！");
			return "error";
		}

	}

	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}
	
}
