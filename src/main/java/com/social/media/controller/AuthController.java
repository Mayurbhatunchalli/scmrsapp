package com.social.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.media.config.JwtProvider;
import com.social.media.entity.User;
import com.social.media.repo.UserRepository;
import com.social.media.request.LoginRequest;
import com.social.media.response.AuthResponse;
import com.social.media.service.CustomerUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	public UserRepository userRepo;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Autowired
	public CustomerUserDetailService customerService;

	@PostMapping("/signUp")
	public AuthResponse createUser(@RequestBody User user) throws Exception {

		User isExists = userRepo.findByEmail(user.getEmail());

		if (isExists != null) {
			throw new Exception("This user already exists with the above email Id");
		}

		User newUser = new User();

		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser = userRepo.save(newUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());

		String token = JwtProvider.generateToken(authentication);

		AuthResponse authResp = new AuthResponse(token, "Registerd successfully");

		return authResp;
	}

	@PostMapping("/signIn")
	public AuthResponse userSignIn(@RequestBody LoginRequest loginReq) {

		Authentication authentication = authenticate(loginReq.getEmail(), loginReq.getPassword());

		String token = JwtProvider.generateToken(authentication);

		AuthResponse authResp = new AuthResponse(token, "Logged in successfully");

		return authResp;
	}

	private Authentication authenticate(String email, String password) {

		UserDetails userDetails = customerService.loadUserByUsername(email);

		if (userDetails == null) {

			throw new BadCredentialsException("Invalid Username");
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {

			throw new BadCredentialsException("Invalid Password");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
