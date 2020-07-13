package com.android.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {
	private static final Logger Logger=LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("doA")
	public void doA() {
		Logger.info("doA called.............");
		
	}
	@RequestMapping("doB")
	public void doB() {
		Logger.info("doB called..............");
	}
	@RequestMapping("doC")
	public String doC(@ModelAttribute("msg") String msg) {
		Logger.info("doC callied.............");
		return "result";
		
	}
}
