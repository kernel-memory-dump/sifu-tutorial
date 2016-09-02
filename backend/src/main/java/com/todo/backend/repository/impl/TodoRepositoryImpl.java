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
package com.todo.backend.repository.impl;

import java.time.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import com.todo.backend.model.*;

import com.todo.backend.repository.TodoRepositoryCustom;

import com.querydsl.jpa.JPQLQueryFactory;


public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final Logger log = LoggerFactory.getLogger(TodoRepositoryImpl.class);

    @Inject
    private JPQLQueryFactory factory;

    @Override
    public List<Todo> findByUser(Long userId) {
        log.trace(".findByUser(userId: {})", userId);
        final QTodo todo = QTodo.todo;
        return factory.select(todo).from(todo).where(todo.user.id.eq(userId)).fetch();
    }

    @Override
    public List<Todo> findByTask(String task) {
        log.trace(".findByTask(task: {})", task);
        final QTodo todo = QTodo.todo;
        return factory.select(todo).from(todo).where(todo.task.eq(task)).fetch();
    }

    @Override
    public List<Todo> findByDate(ZonedDateTime date) {
        log.trace(".findByDate(date: {})", date);
        final QTodo todo = QTodo.todo;
        return factory.select(todo).from(todo).where(todo.date.eq(date)).fetch();
    }

}
