package todo_app.ptit_cntt1_it210_personaltodoapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import todo_app.ptit_cntt1_it210_personaltodoapp.model.Todo;

import java.util.List;

@Repository
public interface ITodoRepository extends JpaRepository<Todo, Long> {

//    List<Todo> findAll();
//
//    Todo save(Todo todo);

//    @Query("select t from Todo t")
//    Page<Todo> findAllTodo(Pageable pageable);
}
