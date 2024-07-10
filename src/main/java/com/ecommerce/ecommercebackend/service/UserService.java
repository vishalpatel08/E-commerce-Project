package com.ecommerce.ecommercebackend.service;


import com.ecommerce.ecommercebackend.api.model.LoginBody;
import com.ecommerce.ecommercebackend.api.model.RegistrationBody;
import com.ecommerce.ecommercebackend.dataModel.LocalUser;
import com.ecommerce.ecommercebackend.dataModel.VerificationToken;
import com.ecommerce.ecommercebackend.dataModel.dao.LocalUserDao;
import com.ecommerce.ecommercebackend.dataModel.dao.VerificationTokenDAO;
import com.ecommerce.ecommercebackend.exception.EmailFailureException;
import com.ecommerce.ecommercebackend.exception.UserAlreadyExistException;
import com.ecommerce.ecommercebackend.exception.UserNotVerifiedException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private LocalUserDao localUserDao;
    private JWTService jwtService;

    private VerificationTokenDAO verificationTokenDAO;

    private EmailService emailService;

    public UserService(LocalUserDao localUserDao, JWTService jwtService, VerificationTokenDAO verificationTokenDAO, EmailService emailService){
        this.localUserDao = localUserDao;
        this.jwtService = jwtService;
        this.verificationTokenDAO = verificationTokenDAO;
        this.emailService = emailService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailureException {

        if( localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || localUserDao.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        System.out.println(" a2 ");
        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setEmail(registrationBody.getEmail());
        // Todo: Password Encryption
        user.setPassword(registrationBody.getPassword());
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEMail(verificationToken);
        //verificationTokenDAO.save(verificationToken);
        System.out.println(" a9 ");
        return localUserDao.save(user);
    }


    private VerificationToken createVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
        Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUsername());
        //System.out.println(" Function Called");
        if(opUser.isPresent()){
            //System.out.println(" User Found ");
            LocalUser user = opUser.get();
            if(user.getPassword().equals(loginBody.getPassword())){
                if(user.isEmailVerified()){
                    return jwtService.generateJWT(user);
                }else{
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size()==0 || verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis()-(60*60*1000)));
                    System.out.println(" resend = "+resend);
                    if(resend){
                        System.out.println("Resend");
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEMail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }

            }
            //System.out.println(" Password didn't match");
        }
        return null;
    }

    @Transactional
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);

        if(opToken.isPresent()){
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if(!user.isEmailVerified()){
                user.setEmailVerified(true);
                localUserDao.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    public Object getLoggedInUser(Object any) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return localUserDao.findByEmailIgnoreCase(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
