package fr.eni.ludotheque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/api/v1/login")
	public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
	    if(authentication.isAuthenticated()){
	       return JwtResponseDTO.builder()
	    		   .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
	    		   .build();
	    } else {
	        throw new UsernameNotFoundException("invalid user request..!!");
	    }
	}

	@GetMapping("/encode")
	public String encode() {
		String enc = passwordEncoder.encode("Pa$$w0rd");
		System.out.println(enc);
		return enc;
	}

}
