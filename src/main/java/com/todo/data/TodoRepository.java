package com.todo.data;

import com.todo.domain.Todo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository
         extends PagingAndSortingRepository<Todo, Long> {

}
