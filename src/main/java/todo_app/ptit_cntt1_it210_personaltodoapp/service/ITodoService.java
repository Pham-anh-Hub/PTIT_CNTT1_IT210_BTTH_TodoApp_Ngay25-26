package todo_app.ptit_cntt1_it210_personaltodoapp.service;

import todo_app.ptit_cntt1_it210_personaltodoapp.dto.TodoDTO;
import todo_app.ptit_cntt1_it210_personaltodoapp.model.Todo;

import java.util.List;

public interface ITodoService {
    List<Todo> getAllTodos();

    void addNewTodo(TodoDTO todoDTO);

    TodoDTO getTodoById(Long id);

    void editTodo(TodoDTO todoDTO);

    void deleteTodo(Long id);
}
