package com.example.Spring.BootServer.Service;

import com.example.Spring.BootServer.Model.Users;
import com.example.Spring.BootServer.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private Validator validator;
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
    public String authenticate(String email, String password) {
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.get().getPassword())) {
            String token = Jwts.builder()
                    .setSubject(user.get().getUser_id().toString())
                    .signWith(SignatureAlgorithm.HS256, "secret")
                    .compact();
            return token;
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }
    public Users createUser(Users user) throws DuplicateKeyException {
        Set<ConstraintViolation<Users>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateKeyException("Email already exists!");
        }
        if (usersRepository.findByPhone_number(user.getPhone_number()).isPresent()) {
            throw new DuplicateKeyException("Phone number already exists!");
        }
        return usersRepository.save(user);
    }
    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
    public Optional<Users> findByPhone_number(String phone_number) {
        return usersRepository.findByPhone_number(phone_number);
    }
    public Optional<Users> findByEmailAndRole(String role, String email) {
        return usersRepository.findByEmailAndRole(role, email);
    }
    public Users updateUsers(Users user) {
        Users existingUser = usersRepository.findById(user.getUser_id()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }
    public void deleteUsers(Long id) {
        usersRepository.deleteById(id);
    }
    public Users selectUserByUserId(Long user_id){
        return usersRepository.selectUserByUserId(user_id);
    }
}
