package com.Tredy.demo.Controller;

import com.Tredy.demo.Config.JwtProvider;
import com.Tredy.demo.Model.TwoFactorOTP;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Repository.UserRepo;
import com.Tredy.demo.Response.AuthResponse;
import com.Tredy.demo.Service.CustomeUserDetailService;
import com.Tredy.demo.Service.EmailService;
import com.Tredy.demo.Service.TwoFactorOTPsrv;
import com.Tredy.demo.Service.WatchListServ;
import com.Tredy.demo.Utils.OTP_Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private CustomeUserDetailService customeUserDetailService;

    @Autowired
    private TwoFactorOTPsrv twoFactorOTPsrv;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WatchListServ watchListServ;


    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> register(@RequestBody  User user) throws Exception{
        User isMailExists=userRepository.findByEmail(user.getEmail());
        if(isMailExists!=null){
            throw new Exception("Email is already used with another account....");
        }
        User newUser=new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        User savedUser=userRepository.save(newUser);
        watchListServ.createWatchList(savedUser);

        Authentication auth=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt= JwtProvider.generateToken(auth);

        AuthResponse respo=new AuthResponse();
        respo.setJwt(jwt);
        respo.setStatus(true);
        respo.setMessage("Register successfully");



        return new ResponseEntity<>(respo, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> logIn(@RequestBody  User user) throws Exception{

        String userName=user.getEmail();
        String password=user.getPassword();

        Authentication auth=authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt= JwtProvider.generateToken(auth);

        User authUser=userRepository.findByEmail(userName);

        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse respo=new AuthResponse();
            respo.setMessage("TwoFactorAuth is enabled...");
            respo.setTwoFactorAuthEnabled(true);
            String otp= OTP_Utils.generateOTP();

            TwoFactorOTP oldTwoFactorOTP=twoFactorOTPsrv.findByUser(authUser.getUserId());
            if(oldTwoFactorOTP!=null){
                twoFactorOTPsrv.deleteTwoFactorOTP(oldTwoFactorOTP);
            }
            TwoFactorOTP newTwoFactorOTP=twoFactorOTPsrv.createTwoFactorOTP(authUser,otp,jwt);
            emailService.sendVerificationOTPEmail(userName,otp);
            respo.setSession(newTwoFactorOTP.getO_ID());
            return new ResponseEntity<>(respo,HttpStatus.ACCEPTED);
        }


        AuthResponse respo=new AuthResponse();
        respo.setJwt(jwt);
        respo.setStatus(true);
        respo.setMessage("Login successfully");



        return new ResponseEntity<>(respo, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails=customeUserDetailService.loadUserByUsername(userName);
        if(userDetails==null){
            throw  new BadCredentialsException("Invalid userName");
        }
        if(!password.equals(userDetails.getPassword())){
            throw  new BadCredentialsException("Invalid password");
        }
        return  new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

    @PostMapping("/twoFactorOTP/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOTP(@PathVariable String otp,@RequestParam String id) throws Exception {
        TwoFactorOTP twoFactorOTP=twoFactorOTPsrv.findById(id);
        if(twoFactorOTPsrv.verifyTwoFactorOTP(twoFactorOTP,otp)){
            AuthResponse repso=new AuthResponse();
            repso.setMessage("Two Factor Authentication verified");
            repso.setTwoFactorAuthEnabled(true);
            repso.setJwt(twoFactorOTP.getJwt());
            return  new ResponseEntity<>(repso,HttpStatus.OK);
        }
        throw  new Exception("Invalid OTP");
    }
}
