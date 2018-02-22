package com.smroom;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.

@Controller
public class MainController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/test")
    public Greeting run(@RequestParam(value="checkpoint", defaultValue="") String checkpoint,@RequestParam(value="variable", defaultValue="") String variable,@RequestParam(value="bom", defaultValue="") String bom,@RequestParam(value="rboRepo", defaultValue="") String rboRepo,@RequestParam(value="bomRepo", defaultValue="") String bomRepo) {
    	try {
    		
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, checkpoint));
    }
}
