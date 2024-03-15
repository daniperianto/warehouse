package com.ruangmain.warehouse.mapper.impl;

import com.ruangmain.warehouse.dto.auth.ResponseUser;
import com.ruangmain.warehouse.mapper.Mapper;
import com.ruangmain.warehouse.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper implements Mapper<User, ResponseUser> {

    private final ModelMapper modelMapper;

    @Override
    public ResponseUser mapTo(User user) {
        return modelMapper.map(user, ResponseUser.class);
    }

    @Override
    public List<ResponseUser> mapTo(List<User> users) {
        return users.stream()
                .map(user -> modelMapper.map(user, ResponseUser.class))
                .toList();
    }


}
