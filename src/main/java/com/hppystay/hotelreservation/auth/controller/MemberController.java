package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.dto.CreateMemberDto;
import com.hppystay.hotelreservation.auth.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class MemberController {
    @PostMapping("/sign-up")
    public MemberDto signUp(
            @RequestBody
            CreateMemberDto createMemberDto
    ) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
