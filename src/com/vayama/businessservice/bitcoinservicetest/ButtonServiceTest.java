package com.vayama.businessservice.bitcoinservicetest;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vayama.businessservice.bitcoinservice.ButtonService;

public class ButtonServiceTest {
	public static void main(String args[]) {
		ButtonService btService = new ButtonService();
		try {
			String test = btService.createButton();
			
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(test, Object.class);	
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
