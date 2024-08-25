package com.ashokit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ashokit.dto.DashboardResponse;
import com.ashokit.entities.Counsellor;
import com.ashokit.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	private CounsellorService counsellorService;

	public CounsellorController(CounsellorService counsellorService) {

		this.counsellorService = counsellorService;
	}

	@GetMapping("/")
	public String index(Model model) {

		Counsellor cobj = new Counsellor();

		// Sending data from controller to UI
		model.addAttribute("counsellor", cobj);

		// returning view name
		return "index";
	}

	@PostMapping("/login")
	public String handleLoginBtn(Counsellor counsellor, HttpServletRequest request, Model model) throws Exception {

		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

		if (c == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";

		} else {

			// valid login, store counsellorId in session for future

			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());
			return "redirect:/dashboard";

		}

	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) throws Exception {

		// get Existing session obj
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		DashboardResponse dbresObj = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo", dbresObj);	
		
		return "dashboard";
		
	}
	
	@GetMapping("/register")
	public String registerpage(Model model) {
		Counsellor cobj = new Counsellor();

		// Sending data from controller to UI
		model.addAttribute("counsellor", cobj);
		return "register";
	}
	
	
	
	@PostMapping("/register")
	public String handlerRegistration(Counsellor counsellor,Model model) {
		
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
		
		if(byEmail!=null) {
			model.addAttribute("emsg", "Duplicate Email");
			return "register";
		}
		
		boolean isRegistered = counsellorService.register(counsellor);
		if(isRegistered) {
			// success
			model.addAttribute("smsg", "Registration success..!!");
		} else {
			// failure
			model.addAttribute("emsg", "Registration failed..!!");
		}
		return "register";
	}
	

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {

		// get existing session and invalidate it
		HttpSession session = req.getSession(false);
		session.invalidate();

		// redirect to login page
		return "redirect:/";

	}

}
