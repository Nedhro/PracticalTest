package com.bits.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bits.dto.Response;
import com.bits.security.TokenUtil;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationToken(@RequestBody Map<String, String> authenticationRequest, Device device) throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.get("username"),
                        authenticationRequest.get("password")
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.get("username"));       
        final String accessToken = jwtTokenUtil.generateToken(userDetails, device);       
        final String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails, device);
        
        Map<String,String> token = new HashMap<String,String>();
        token.put("accessToken", accessToken);
        token.put("refreshToken", refreshToken);
        
        Response<Map<String,String>> response = new Response<Map<String,String>>(token,"200","success");
        return ResponseEntity.ok(response);
    }

}
