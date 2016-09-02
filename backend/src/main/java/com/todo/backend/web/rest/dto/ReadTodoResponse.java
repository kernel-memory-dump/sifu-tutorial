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
package com.todo.backend.web.rest.dto;

import java.io.Serializable;

import java.time.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;


public class ReadTodoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 1, max = 255)
    private String task;

    @NotNull
    private ZonedDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ReadTodoResponse other = (ReadTodoResponse) obj;
        if ((id == null && other.id != null) || !id.equals(other.id))
            return false;
        if ((userId == null && other.userId != null) || !userId.equals(other.userId))
            return false;
        if ((task == null && other.task != null) || !task.equals(other.task))
            return false;
        if ((date == null && other.date != null) || !date.equals(other.date))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((task == null) ? 0 : task.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ReadTodoResponse[" + "id=" + id + ", userId=" + userId + ", task=" + task + ", date=" + date + "]";
    }

}
