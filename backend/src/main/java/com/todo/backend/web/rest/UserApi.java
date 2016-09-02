/**
* Copyright 2016 dryTools doo
* Email: contact@drytools.co
* 
* This file is part of todo.
* 
* todo is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* todo is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with todo. If not, see <http://www.gnu.org/licenses/>.*
**/
package com.todo.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.codahale.metrics.annotation.Timed;
import javax.validation.Valid;
import com.todo.backend.model.*;
import com.todo.backend.web.rest.dto.*;

import com.todo.backend.repository.*;


@RestController
@RequestMapping("/api/")
public class UserApi {

    private final Logger log = LoggerFactory.getLogger(UserApi.class);

    @Inject
    private UserRepository userRepository;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<ReadUserResponse> readUser(@PathVariable Long id) {
        log.debug("GET /user/{}", id);
        final Optional<User> result = Optional.ofNullable(userRepository.findOne(id));
        if (result.isPresent()) {
            return ResponseEntity.ok().body(convertToReadUserResponse(result.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) throws URISyntaxException {
        log.debug("POST /user {}", request);
        final User user = convertToUser(request);
        final User result = userRepository.save(user);
        return ResponseEntity.created(new URI("/user/" + result.getId())).body(convertToCreateUserResponse(result));
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody RestUpdateUserRequest request) {
        log.debug("PUT /user/{} {}", id, request);
        final User user = convertToUser(id, request);
        final User result = userRepository.save(user);
        return ResponseEntity.ok().body(convertToUpdateUserResponse(result));
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("DELETE /user/{}", id);
        userRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    private ReadUserResponse convertToReadUserResponse(User model) {
        final ReadUserResponse dto = new ReadUserResponse();
        dto.setId(model.getId());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        return dto;
    }

    private User convertToUser(CreateUserRequest dto) {
        final User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }

    private CreateUserResponse convertToCreateUserResponse(User model) {
        final CreateUserResponse dto = new CreateUserResponse();
        dto.setId(model.getId());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        return dto;
    }

    private User convertToUser(Long id, RestUpdateUserRequest dto) {
        final User user = new User();
        user.setId(id);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }

    private UpdateUserResponse convertToUpdateUserResponse(User model) {
        final UpdateUserResponse dto = new UpdateUserResponse();
        dto.setId(model.getId());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        return dto;
    }
}
