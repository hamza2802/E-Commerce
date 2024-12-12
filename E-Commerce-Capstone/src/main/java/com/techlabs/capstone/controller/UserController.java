package com.techlabs.capstone.controller;

import com.techlabs.capstone.dto.UserRequestDto;
import com.techlabs.capstone.dto.UserResponseDto;
import com.techlabs.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-commerce/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createNewUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createNewUser(userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/create/delivery-agent")
    public UserResponseDto createDeliveryAgent(@RequestBody UserRequestDto userRequestDto) {
        return userService.addNewDeliveryAgent(userRequestDto);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userId") int userId, 
                                                      @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.updateUser(userId, userRequestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.verifyUserCredentials(userRequestDto.getEmail(), userRequestDto.getPassword());
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }
}
