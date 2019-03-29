package com.bits.apiinteraction;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.bits.dto.ResponseInteractionTypeGroup;

@RestController
public class ApiInteractionController {
		
	@Value("${api.url}")
    private String uri;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping(value = "/api/interaction")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewEmployee(HttpServletRequest request) {
		try {            
			ResponseEntity < ResponseInteractionTypeGroup<?>> response = restTemplate.exchange(
						uri,
	            	    HttpMethod.GET, 
	            	    null, 
	            	    new ParameterizedTypeReference < ResponseInteractionTypeGroup<?> > () {}
					);
			ResponseInteractionTypeGroup<?> entity = response.getBody();
            return ResponseEntity.ok(new ResponseInteractionTypeGroup<>(entity,"Successfully",200));
        } catch (HttpClientErrorException e) {
        	return ResponseEntity.ok(new ResponseInteractionTypeGroup<>(null,e.getMessage(),e.getStatusCode().value()));
        } catch (Exception e) {
        	return ResponseEntity.ok(new ResponseInteractionTypeGroup<>(null,e.getMessage(),200));
        }
	}
	
}
