package com.loginauthenication.authentication.Controller;
import com.loginauthenication.authentication.Service.otpService;
import com.loginauthenication.authentication.Service.userService;
import com.loginauthenication.authentication.models.userDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private otpService otpServices;

    @PostMapping("signup")
    public ResponseEntity<String> userDetails(@RequestBody userDetails userDetails)
    {
        String result= userService.signUp(userDetails);
        if (result.equals("User created successfully!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(409).body(result);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String,String>> verifyOtp(@RequestParam("userEmail") String email, @RequestParam("otp") int otp) {
        String result = userService.verifyOtp(email, otp);
        if (result.equals("User created successfully!")) {
            String accessToken = otpServices.generateAccessToken(email);

            String refreshToken = otpServices.generateRefreshToken(email);

            HashMap<String, String> response = new LinkedHashMap<>();
            response.put("status", String.valueOf(HttpStatus.OK.value()));
            response.put("verification","OTP Verified Succesfully");

            HashMap<String,String>data=new LinkedHashMap<>();
            data.put("accessToken", accessToken);
            data.put("refreshToken", refreshToken);
            response.put("data",data.toString());

            System.out.println("OTP Verified Successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            errorResponse.put("error", result);
            return ResponseEntity.status(400).body(errorResponse);  // 400 Bad Request
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam("userEmail")String email,@RequestParam("password")String password)
    {
        String result=userService.login(email, password);
//        if(result.equals("Login Successfull!"))
//        {
//            return ResponseEntity.ok(result);
//        }
//        else
//        {
//
//            return ResponseEntity.status(400).body(result);
//        }
        return "otp sent to mail";
    }
}

