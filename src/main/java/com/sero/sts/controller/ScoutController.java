package com.sero.sts.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.sero.sts.service.ScoutService;
import com.sero.sts.vo.ProDetailsVO;

@RestController
@RequestMapping("/scout")
public class ScoutController {

	@RequestMapping(value="/proList", method = RequestMethod.GET) // 선수 목록 이동
	public ModelAndView proList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		List<ProDetailsVO> proList = ScoutService.listPros();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("proList", proList);
		return mav;
	}
	
	@RequestMapping(value="/proForm", method = RequestMethod.GET) // 선수 추가 폼 이동
	public ModelAndView proForm(@ModelAttribute("pro") ProDetailsVO pro, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@RequestMapping(value="/addPro", method = RequestMethod.POST) // 선수 추가 행동 확인
	public ModelAndView addPro(@ModelAttribute("pro") ProDetailsVO pro, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		result = ScoutService.addPro(pro);
		return new ModelAndView("proList");
	}
	
	@RequestMapping(value="/viewPro/{proNum}", method = RequestMethod.GET) // 선수 추가 행동 확인
	public ModelAndView viewPro(@RequestParam("proNum") int proNum, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ProDetailsVO proInfo = ScoutService.getProInfo(proNum);
		ModelAndView mav = new ModelAndView(viewName);
			
	    mav.addObject("proInfo", proInfo);  // Add book details list object

		return mav;

	}

	/**
	@RequestMapping("/editPro")
	public ModelAndView editPro(@ModelAttribute("pro") ProDetailsVO pro, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ScoutService.editPro(pro);
		ModelAndView mav = new ModelAndView("redirect:/book/viewBook.do?num="+pro.getProNum());
		return mav;
	}
	
	@RequestMapping("/removePro")
	public ModelAndView removePro(@ModelAttribute("pro") ProDetailsVO pro, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	}
	*/

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/"), viewName.length());
		}
		return viewName;
	}

}
