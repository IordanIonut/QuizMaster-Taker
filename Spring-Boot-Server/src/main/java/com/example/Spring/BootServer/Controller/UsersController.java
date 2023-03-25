package com.example.Spring.BootServer.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.Spring.BootServer.Configure.JwtUtil;
import com.example.Spring.BootServer.Configure.LoginRequest;
import com.example.Spring.BootServer.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import com.example.Spring.BootServer.Service.UsersService;
import com.example.Spring.BootServer.Configure.LoginResponse;
import static com.example.Spring.BootServer.Configure.JwtUtil.SECRET;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtUtil JwtProperties;
    @PostMapping("/users/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<Users> user = usersService.findByEmailAndRole(loginRequest.getRole(), loginRequest.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email, please check the email!");
        }
        if(passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword()) && loginRequest.getRole().equals(user.get().getRole())) {
            String token = JWT.create()
                    .withSubject(user.get().getEmail())
                    .withClaim("userId", user.get().getUser_id())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(SECRET.getBytes()));
            return ResponseEntity.ok(new LoginResponse(token));
        } else {
           // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password, please check the password!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword()));
        }
    }

    @GetMapping("/users/token")
    public Long getUserIdFromToken(@RequestParam("token") String token) {
        Long userId = JwtProperties.getUserIdFromToken(token);
        return userId;
    }
    @PostMapping("/users/add")
    public ResponseEntity<?> createUser(@Valid @RequestBody Users user, BindingResult result)  {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersService.createUser(user);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/users/all")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }
    @GetMapping("/users/email")
    public Optional<Users> findByEmail(@RequestParam String email) {
        return usersService.findByEmail(email);
    }
    @GetMapping("/users/phone_number")
    public Optional<Users> findByPhone_number(@RequestParam String phoneNumber) {
        return usersService.findByPhone_number(phoneNumber);
    }
    @PutMapping("/users/update")
    public Users updateUsers(@RequestBody Users user) {
        return usersService.updateUsers(user);
    }
    @DeleteMapping("/users/delete/{id}")
    public void deleteUsers(@PathVariable Long id) {
        usersService.deleteUsers(id);
    }
    @GetMapping("/users/user_id")
    public Users selectUserByUserId(@RequestParam("user_id") Long user_id){
        return usersService.selectUserByUserId(user_id);
    }
}
