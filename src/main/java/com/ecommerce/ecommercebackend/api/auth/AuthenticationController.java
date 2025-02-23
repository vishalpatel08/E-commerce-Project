package com.ecommerce.ecommercebackend.api.auth;

import com.ecommerce.ecommercebackend.api.model.LoginBody;
import com.ecommerce.ecommercebackend.api.model.LoginResponse;
import com.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.ecommerce.ecommercebackend.dataModel.LocalUser;
import com.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.ecommerce.ecommercebackend.exception.UserAlreadyExistException;
import com.ecommerce.ecommercebackend.exception.UserNotVerifiedException;
import com.ecommerce.ecommercebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody){
        try{
            userService.registerUser((registrationBody));
            return ResponseEntity.ok().build();
        }catch (UserAlreadyExistException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse > loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = null;
        try{
            jwt = userService.loginUser(loginBody);
        }catch (UserNotVerifiedException ex){
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "User_Not_Verified";
            if(ex.isNewEmailSent()){
                reason += "_Email_Resent";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token){
        if(userService.verifyUser(token)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/me")
    public LocalUser getLoggedInProfile(@AuthenticationPrincipal LocalUser user){
        return user;
    }
}
