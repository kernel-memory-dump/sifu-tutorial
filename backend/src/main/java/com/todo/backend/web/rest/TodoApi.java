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

import org.springframework.format.annotation.DateTimeFormat;


@RestController
@RequestMapping("/api/")
public class TodoApi {

    private final Logger log = LoggerFactory.getLogger(TodoApi.class);

    @Inject
    private TodoRepository todoRepository;

    @Inject
    private UserRepository userRepository;

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<ReadTodoResponse> readTodo(@PathVariable Long id) {
        log.debug("GET /todo/{}", id);
        final Optional<Todo> result = Optional.ofNullable(todoRepository.findOne(id));
        if (result.isPresent()) {
            return ResponseEntity.ok().body(convertToReadTodoResponse(result.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/todo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<CreateTodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) throws URISyntaxException {
        log.debug("POST /todo {}", request);
        final Todo todo = convertToTodo(request);
        final Todo result = todoRepository.save(todo);
        return ResponseEntity.created(new URI("/todo/" + result.getId())).body(convertToCreateTodoResponse(result));
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<UpdateTodoResponse> updateTodo(@PathVariable Long id, @Valid @RequestBody RestUpdateTodoRequest request) {
        log.debug("PUT /todo/{} {}", id, request);
        final Todo todo = convertToTodo(id, request);
        final Todo result = todoRepository.save(todo);
        return ResponseEntity.ok().body(convertToUpdateTodoResponse(result));
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        log.debug("DELETE /todo/{}", id);
        todoRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    private ReadTodoResponse convertToReadTodoResponse(Todo model) {
        final ReadTodoResponse dto = new ReadTodoResponse();
        dto.setId(model.getId());
        dto.setUserId(model.getUser().getId());
        dto.setTask(model.getTask());
        dto.setDate(model.getDate());
        return dto;
    }

    private Todo convertToTodo(CreateTodoRequest dto) {
        final Todo todo = new Todo();
        final User user = userRepository.findOne(dto.getUserId());
        todo.setUser(user);
        todo.setTask(dto.getTask());
        todo.setDate(dto.getDate());
        return todo;
    }

    private CreateTodoResponse convertToCreateTodoResponse(Todo model) {
        final CreateTodoResponse dto = new CreateTodoResponse();
        dto.setId(model.getId());
        dto.setUserId(model.getUser().getId());
        dto.setTask(model.getTask());
        dto.setDate(model.getDate());
        return dto;
    }

    private Todo convertToTodo(Long id, RestUpdateTodoRequest dto) {
        final Todo todo = new Todo();
        todo.setId(id);
        final User user = userRepository.findOne(dto.getUserId());
        todo.setUser(user);
        todo.setTask(dto.getTask());
        todo.setDate(dto.getDate());
        return todo;
    }

    private UpdateTodoResponse convertToUpdateTodoResponse(Todo model) {
        final UpdateTodoResponse dto = new UpdateTodoResponse();
        dto.setId(model.getId());
        dto.setUserId(model.getUser().getId());
        dto.setTask(model.getTask());
        dto.setDate(model.getDate());
        return dto;
    }
}
