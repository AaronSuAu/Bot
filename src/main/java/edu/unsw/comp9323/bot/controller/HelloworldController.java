
package edu.unsw.comp9323.bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {
	@GetMapping("/")
	public String hello() {
		return "Hello World";
	}
}
