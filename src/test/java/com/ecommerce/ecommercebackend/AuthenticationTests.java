package com.ecommerce.ecommercebackend;

import com.ecommerce.ecommercebackend.api.auth.AuthenticationController;
import com.ecommerce.ecommercebackend.api.model.LoginBody;
import com.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.ecommerce.ecommercebackend.dataModel.LocalUser;
import com.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.ecommerce.ecommercebackend.exception.UserAlreadyExistException;
import com.ecommerce.ecommercebackend.exception.UserNotVerifiedException;
import com.ecommerce.ecommercebackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private UserDetailsService userDetailsService;

	private RegistrationBody registrationBody;
	private LoginBody loginBody;

	@BeforeEach
	public void setUp() {
		registrationBody = new RegistrationBody();
		registrationBody.setEmail("test@example.com");
		registrationBody.setPassword("password");

		loginBody = new LoginBody();
		loginBody.setUsername("testUserName");
		loginBody.setPassword("password");
	}

	@Test
	public void testRegisterUser_Success() throws Exception {
		mockMvc.perform(post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registrationBody)))
				.andExpect(status().isOk());
	}

	@Test
	public void testRegisterUser_UserAlreadyExist() throws Exception {
		Mockito.doThrow(new UserAlreadyExistException()).when(userService).registerUser(Mockito.any());

		mockMvc.perform(post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registrationBody)))
				.andExpect(status().isConflict());
	}

	@Test
	public void testRegisterUser_EmailFailure() throws Exception {
		Mockito.doThrow(new EmailFailureException()).when(userService).registerUser(Mockito.any());
		mockMvc.perform(post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registrationBody)))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void testLoginUser_Success() throws Exception {
		String jwt = "dummy-jwt";
		Mockito.when(userService.loginUser(Mockito.any())).thenReturn(jwt);

		mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginBody)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.jwt").value(jwt))
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	public void testLoginUser_UserNotVerified() throws Exception {
		UserNotVerifiedException exception = new UserNotVerifiedException(false);
		Mockito.doThrow(exception).when(userService).loginUser(Mockito.any());

		mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(loginBody)))
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.failureReason").value("User_Not_Verified"));
	}

	@Test
	public void testVerifyEmail_Success() throws Exception {
		Mockito.when(userService.verifyUser(Mockito.anyString())).thenReturn(true);

		mockMvc.perform(post("/auth/verify")
						.param("token", "dummy-token"))
				.andExpect(status().isOk());
	}

	@Test
	public void testVerifyEmail_Failure() throws Exception {
		Mockito.when(userService.verifyUser(Mockito.anyString())).thenReturn(false);

		mockMvc.perform(post("/auth/verify")
						.param("token", "dummy-token"))
				.andExpect(status().isConflict());
	}

	@Test
	@WithMockUser(username = "test@example.com")
	public void testGetLoggedInProfile_Success() throws Exception {
		LocalUser localUser = new LocalUser();
		localUser.setEmail("test@example.com");

		Mockito.when(userService.getLoggedInUser(Mockito.any())).thenReturn(localUser);

		mockMvc.perform(get("/auth/me"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("test@example.com"));
	}
}
