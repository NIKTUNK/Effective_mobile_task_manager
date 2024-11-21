package com.nikitatunkel.eff_mob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikitatunkel.eff_mob.model.User;
import com.nikitatunkel.eff_mob.security.JwtTokenProvider;
import com.nikitatunkel.eff_mob.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testLoginSuccess() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        String token = "testToken";

        // Mock AuthenticationManager and JwtTokenProvider
        Mockito.doNothing().when(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Mockito.when(jwtTokenProvider.createToken(username, Mockito.anyList())).thenReturn(token);
        Mockito.when(userService.findByUsername(username)).thenReturn(new User(username, password, "ROLE_USER"));

        // Send login request
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("username", username, "password", password))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void testLoginFailure() throws Exception {
        String username = "testUser";
        String password = "wrongPassword";

        // Mock AuthenticationManager to throw exception
        Mockito.doThrow(new BadCredentialsException("Invalid username/password"))
                .when(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // Send login request
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("username", username, "password", password))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid username/password"));
    }

    @Test
    void testRegisterSuccess() throws Exception {
        User user = new User("newUser", "newPassword", "ROLE_USER");

        // Mock UserService
        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(user);

        // Send register request
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }
}