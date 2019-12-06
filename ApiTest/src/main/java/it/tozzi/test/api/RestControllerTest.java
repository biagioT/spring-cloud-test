package it.tozzi.test.api;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("api")
public class RestControllerTest {
	
	static final Logger logger = LoggerFactory.getLogger(RestControllerTest.class);
	
	@PostMapping("/without-request-header-token")
	public ResponseEntity<String> testWithoutRequestToken(@RequestBody Message in) {
		
		logger.info("Start API test (without request header token)");
		
		try {
			logger.debug("Request: {}", in.getMessage());

			HttpHeaders responseHeaders = new HttpHeaders();
			
			char[] chars = new char[1000000];
			Arrays.fill(chars, 'a');
			String str = new String(chars);
			
		    responseHeaders.set("Authorization", "Bearer " + str);
			
		    return ResponseEntity.ok()
		  	      .headers(responseHeaders)
		  	      .body(in.getMessage());
			
		} catch (Exception e ) {
			logger.error("Test API error", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Test API error", e);
			
		} finally {
			logger.info("End API test (without request header token)");

		}
	}
	
	@PostMapping("/with-request-header-token")
	public ResponseEntity<String> testWithRequestToken(@RequestHeader("Authorization") String authHeader, @RequestBody Message in) {
		
		logger.info("Start API test (with request header token)");
		
		try {
			logger.debug("Request: {}", in.getMessage());
			logger.debug("Auth. header: {}", authHeader);
			
			HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.set("Authorization", "Bearer " + authHeader);
		    
		    return ResponseEntity.ok()
		  	      .headers(responseHeaders)
		  	      .body(in.getMessage());
			
		} catch (Exception e ) {
			logger.error("Test API error", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Test API error", e);
			
		} finally {
			logger.info("End API test (with request header token)");

		}
	}
	
	public static class Message {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	
	}
}
