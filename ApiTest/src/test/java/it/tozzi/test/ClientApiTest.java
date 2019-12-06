package it.tozzi.test;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.tozzi.test.api.RestControllerTest.Message;

public class ClientApiTest {
	
	static final Logger logger = LoggerFactory.getLogger(ClientApiTest.class);

	public static void main(String[] args) {
		
		RestTemplate restTemplate = new RestTemplate();
		Message in = new Message();
		in.setMessage("Hello!");
		
		try {
			HttpEntity<Message> request = new HttpEntity<Message>(in);
			ResponseEntity<String> r = restTemplate.postForEntity("http://localhost:7090/api/without-request-header-token", request, String.class);
			logger.debug(r.getBody());
			
		} catch (Exception e) {
			logger.error("/api/test/without-request-header-token - {}", e);
		}
		
		try {
			char[] chars = new char[1000000];
			Arrays.fill(chars, 'a');
			String str = new String(chars);
			String authHeader = "Bearer: " + new String(str);
			HttpHeaders h = new HttpHeaders();
			h.add("Authorization", authHeader);
			HttpEntity<Message> request = new HttpEntity<Message>(in, h);
			ResponseEntity<String> r = restTemplate.postForEntity("http://localhost:7090/api/with-request-header-token", request, String.class);
			logger.debug(r.getBody());

		} catch (Exception e) {
			logger.error("/api/test/with-request-header-token - {}", e);

		}
	}
	
}
