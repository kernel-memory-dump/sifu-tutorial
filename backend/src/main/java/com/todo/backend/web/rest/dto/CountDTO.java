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

import javax.validation.constraints.NotNull;


public class CountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CountDTO other = (CountDTO) obj;
        if (count == null) {
            if (other.count != null)
                return false;
        } else if (!count.equals(other.count))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((count == null) ? 0 : count.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("CountDTO [count=%s]", count);
    }

}
