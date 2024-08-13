package com.loginauthenication.authentication.Service;


import com.loginauthenication.authentication.Repository.userrepository;
import com.loginauthenication.authentication.models.userDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loginauthenication.authentication.models.userDetails;

import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userrepository userRepository;

    @Autowired
    private emailService emailService;

    public userDetails saveUser(userDetails userDetails)
    {

        return userRepository.save(userDetails);
    }



    public String signUp(userDetails userDetails) {
        if (userRepository.findByUserEmail(userDetails.getUserEmail()).isPresent()) {
            return "User with this email already exists!";
        } else {
//            int otp = emailService.optGenerator();
//            userDetails.setOtp(otp);
            userRepository.save(userDetails);
//            emailService.sendEmail(userDetails.getUserEmail(), otp);
//            return "OTP sent to your email. Please verify.";
            return "User created successfully!";
        }

    }

    public String verifyOtp(String email, int otp) {
        Optional<userDetails>  userDetailsEmail = userRepository.findByUserEmail(email);

        if(userDetailsEmail.isEmpty()){
            return "User not found";
        }

        userDetails userDetails = userDetailsEmail.get();

        if (userDetails.getOtp() == otp) {
            return "User created successfully!";
        } else {
            return "Invalid OTP. Please try again.";
        }}
    public String login(String email, String password)
    {
        Optional<userDetails>  userDetails = userRepository.findByUserEmail(email);
        userDetails userDetails1=userDetails.get();

//        userDetails UserDetails=userRepository.findByUserEmail(email);
        int otp = emailService.optGenerator();
         userDetails1.setOtp(otp);
         userRepository.save(userDetails1);
         emailService.sendEmail(userDetails1.getUserEmail(), otp);

        if(userDetails.isEmpty()){
            return "UserName not found";
        }

        userDetails userDetailsValue = userDetails.get();

        if(userDetailsValue.getUserEmail().equals(email) && userDetailsValue.getPassword().equals(password)&& userDetailsValue.getOtp()==otp)
        {
            return "Login Successfull!";
        }
        else {
            return "Invalid credentials";
        }
    }
}

