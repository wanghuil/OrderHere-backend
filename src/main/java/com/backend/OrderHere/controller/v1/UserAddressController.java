package com.backend.OrderHere.controller.v1;

import com.backend.OrderHere.dto.UserAddress.UserAddressGetDto;
import com.backend.OrderHere.dto.UserAddress.UserAddressPostDto;
import com.backend.OrderHere.dto.UserAddress.UserAddressPutDto;
import com.backend.OrderHere.service.UserAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/public/user-address")
@RequiredArgsConstructor
@Validated
public class UserAddressController {
    private final UserAddressService userAddressService;

    @GetMapping("/{userAddressId}")
    @ResponseStatus(HttpStatus.OK)
    public UserAddressGetDto getByUserAddressId(@PathVariable Integer userAddressId) {
        return userAddressService.getUserAddressByUserAddressId(userAddressId);
    }

    @GetMapping("/all/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserAddressGetDto> getByUserId(@PathVariable Integer userId) {
        return userAddressService.getUserAddressByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddressGetDto createUserAddress(@Valid @RequestBody UserAddressPostDto userAddressPostDto) {
        return userAddressService.createUserAddress(userAddressPostDto);
    }

    @DeleteMapping("/{userAddressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserAddressId(@PathVariable Integer userAddressId) {
        userAddressService.deleteByUserAddressId(userAddressId);
    }

    @PutMapping("/{userAddressId}")
    @ResponseStatus(HttpStatus.OK)
    public UserAddressGetDto updatedUserAddress(@PathVariable Integer userAddressId, @RequestBody UserAddressPutDto userAddressPutDto) {
        return userAddressService.updateUserAddressByUserAddressId(userAddressId, userAddressPutDto);
    }
}
