package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.ToDo;

import com.softserve.itacademy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @Query("select t from ToDo t join t.collaborators c where c.id = :id OR t.owner=:id")
    List<ToDo> findTodoByUserId(@Param("id") long id);

}
