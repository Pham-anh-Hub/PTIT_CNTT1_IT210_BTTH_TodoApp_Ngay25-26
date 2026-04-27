package todo_app.ptit_cntt1_it210_personaltodoapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todo_app.ptit_cntt1_it210_personaltodoapp.dto.TodoDTO;
import todo_app.ptit_cntt1_it210_personaltodoapp.model.Todo;
import todo_app.ptit_cntt1_it210_personaltodoapp.repository.ITodoRepository;
import todo_app.ptit_cntt1_it210_personaltodoapp.service.ITodoService;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements ITodoService {

    private final ITodoRepository todoRepository;

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public void addNewTodo(TodoDTO todoDTO) {
        // Gen Id và chuyển đổi dữ liệu
        Todo target = todoRepository.findAll().stream().max(Comparator.comparingLong(Todo::getId)).orElse(null);
        Long newId = target == null ? 1 : target.getId() + 1;

        Todo newTodo = new Todo(
                null,
                todoDTO.getContent(),
                todoDTO.getDueDate(),
                todoDTO.getStatus(),
                todoDTO.getPriority()
        );

        todoRepository.save(newTodo);
    }

    @Override
    public TodoDTO getTodoById(Long id) {
            Todo todoTarget = todoRepository.findAll().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
            TodoDTO todoDTO = todoTarget == null ? null :  new TodoDTO(
                    id,
                    todoTarget.getContent(),
                    todoTarget.getDueDate(),
                    todoTarget.getStatus(),
                    todoTarget.getPriority()
            );

            return todoDTO;
    }

    @Override
    public void editTodo(TodoDTO todoDTO) {
//        for (Todo t : todoRepository.findAll()){
//            if (t.getId().equals(todoDTO.getId())){
//                t.setContent(todoDTO.getContent());
//                t.setDueDate(todoDTO.getDueDate());
//                t.setStatus(todoDTO.getStatus());
//                t.setPriority(todoDTO.getPriority());
//            }
//        }
        Todo updateTodo = new Todo(
                todoDTO.getId(),
                todoDTO.getContent(),
                todoDTO.getDueDate(),
                todoDTO.getStatus(),
                todoDTO.getPriority()
        );

        todoRepository.save(updateTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
