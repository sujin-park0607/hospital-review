package com.hospitalreview.service;

import com.hospitalreview.domain.User;
import com.hospitalreview.domain.dto.UserDto;
import com.hospitalreview.domain.dto.UserJoinRequest;
import com.hospitalreview.domain.dto.UserJoinResponse;
import com.hospitalreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    /**
     * 비즈니스 로직- 회원 가입
     */
    public UserDto join(UserJoinRequest request){
        //회원 userName(id) 중복 Check
        // 중복이면 회원가입 x -> Exception(예외) 발생

        userRepository.findByUserName(request.getUserName())
                .ifPresent(user-> new RuntimeException("해당 UserName이 중복됩니다"));
        User savedUser = userRepository.save(request.toEntity());

        //Entity를 그대로 사용하는것이 좋지 않아서 Dto로 변경해서 return.

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmailAddress())
                .build();
    }
}
