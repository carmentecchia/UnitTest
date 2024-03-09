package com.example.UnitTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/newUser")
    public Users createNewUser(@RequestBody Users userEntity){
        return userRepository.save(userEntity);
    }
    @GetMapping("/{id}")
    public Users getUsersById(@PathVariable Long id){
        return userRepository.getReferenceById(id);
    }
    @GetMapping("/getUsers")
    public List<Users> getUserList (){
        return userRepository.findAll();

    }

    @PutMapping ("/changeName/{id}")
    public void changeName(@PathVariable Long id, @RequestParam String name){
        Users userEntity = userRepository.findById(id).orElseThrow();
        userEntity.setName(name);
        userRepository.save(userEntity);
    }

    @DeleteMapping ("/deleteUsers")
    public void deleteUsers(){
        userRepository.deleteAll();;
    }
}