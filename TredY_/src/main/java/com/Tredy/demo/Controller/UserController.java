package com.Tredy.demo.Controller;

import com.Tredy.demo.Domain.VerificationType;
import com.Tredy.demo.Model.ForgotPassToken;
import com.Tredy.demo.Request.ForgotPassTokenRequest;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.VerficationCode;
import com.Tredy.demo.Request.ResetPassRequest;
import com.Tredy.demo.Response.ApiResponse;
import com.Tredy.demo.Response.AuthResponse;
import com.Tredy.demo.Service.EmailService;
import com.Tredy.demo.Service.ForgotPasswordTokenServ;
import com.Tredy.demo.Service.UserService;
import com.Tredy.demo.Service.VerificationCodeserv;
import com.Tredy.demo.Utils.OTP_Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeserv verificationCodeserv;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordTokenServ forgotPasswordTokenServ;

    @GetMapping("/jwt")
    public ResponseEntity<User> getUserProfile( @RequestHeader("Authorization") String jwt) throws  Exception{
        User user=userService.findUserByJWT(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/verification/{verificationType}/sendOTP")
    public ResponseEntity<String> sendVerificationOTP(@RequestHeader("Authorization") String jwt, @PathVariable VerificationType verificationType) throws Exception {
        User user=userService.findUserByJWT(jwt);
        VerficationCode verficationCode=verificationCodeserv.getVerificationCodeByUser(user.getUserId());
        if(verficationCode==null){
            verficationCode=verificationCodeserv.sendVerificationCode(user,verificationType);
        }
        if(verificationType.equals(verificationType.EMAIL)){
            emailService.sendVerificationOTPEmail(user.getEmail(),verficationCode.getOtp());
        }
        return  new ResponseEntity<>("Verification OTP sent successfully",HttpStatus.OK);
    }

    @PostMapping("/enableTwoFactorVerification/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization") String jwt,@PathVariable String otp) throws Exception {
        User user=userService.findUserByJWT(jwt);
        VerficationCode verficationCode=verificationCodeserv.getVerificationCodeByUser(user.getUserId());
        String sendTo=verficationCode.getVerificationType().equals(VerificationType.EMAIL)?verficationCode.getEmail():verficationCode.getMobile();
        boolean isVerified=verficationCode.getOtp().equals(otp);
        if (isVerified){
            User updatedUser=userService.enableTwoFactorAuthentication(verficationCode.getVerificationType(),sendTo,user);
            verificationCodeserv.deleteVerificationCode(verficationCode);
            return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        }
        throw  new Exception("Wrong otp");
    }

    @PostMapping("/resetPass/sendOTP")
    public ResponseEntity<AuthResponse> sendForgotPassOTP(@RequestBody ForgotPassTokenRequest forgotPassTokenRequest) throws Exception {
        User user=userService.findUserByEmail(forgotPassTokenRequest.getSendTo());
        String otp= OTP_Utils.generateOTP();
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();
        ForgotPassToken token=forgotPasswordTokenServ.findByUser(user.getUserId());

        if (token==null){
            token=forgotPasswordTokenServ.createToken(user,id,otp,forgotPassTokenRequest.getVerificationType(),forgotPassTokenRequest.getSendTo());
        }
        if (forgotPassTokenRequest.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOTPEmail(user.getEmail(),token.getOtp());
        }

        AuthResponse authResponse=new AuthResponse();
        authResponse.setSession(token.getId());
        authResponse.setMessage("Password reset otp sent successfully");

        return  new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    @PostMapping("/resetPass/verifyOTP")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id, @RequestBody ResetPassRequest resetPassRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        ForgotPassToken forgotPassToken=forgotPasswordTokenServ.findById(id);
        boolean isVerified=forgotPassToken.getOtp().equals(resetPassRequest.getOtp());
        if (isVerified){
            userService.updatePassword(forgotPassToken.getUser(),resetPassRequest.getPassword());
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage("password update successfully");
            return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
        }

        throw new Exception("wrong OTP");
    }

}



