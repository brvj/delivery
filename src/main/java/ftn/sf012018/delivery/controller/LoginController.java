package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.user.LoginDTO;
import ftn.sf012018.delivery.security.token.TokenUtils;
import ftn.sf012018.delivery.service.user.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/login")
public class LoginController {
    @Autowired
    private DefaultUserService defaultUserService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenUtils tokenUtils;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            authManager.authenticate(token);
            UserDetails details = defaultUserService.loadUserByUsername(loginDTO.getUsername());
            String jwt = tokenUtils.generateToken(details);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
