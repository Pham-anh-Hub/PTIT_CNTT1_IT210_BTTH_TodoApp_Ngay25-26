package todo_app.ptit_cntt1_it210_personaltodoapp.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import todo_app.ptit_cntt1_it210_personaltodoapp.dto.TodoDTO;
import todo_app.ptit_cntt1_it210_personaltodoapp.service.ITodoService;

@Controller
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final ITodoService todoService;

    @GetMapping("/list")
    public String todoList(
            Model model,
            @PageableDefault(
                    page = 0,
                    size = 3,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ){
        model.addAttribute("todoList", todoService.getAllTodos());
        return "view-todo-list";
    }

    @GetMapping("/view-add")
    public String viewAdd(
            Model model
    ){
        model.addAttribute("onEdit", false);
        model.addAttribute("todoDTO", new TodoDTO());
        return "view-form-add";
    }

    @PostMapping("/handle-add")
    public String onAddNew(
            @Valid @ModelAttribute(name = "todoDTO") TodoDTO todoDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ){
        if (bindingResult.hasErrors()){
            model.addAttribute("onEdit", false);
            model.addAttribute("todoDTO", todoDTO);
            return "view-form-add";
        }

        todoService.addNewTodo(todoDTO);
        redirectAttributes.addFlashAttribute("messages", "Thêm công việc mới thành công");
        return "redirect:/todo/list";
    }

    @GetMapping("/view-edit/{id}")
    public String viewEdit(
            @PathVariable(name = "id") String todoId,
            Model model
    ){
        model.addAttribute("onEdit", true);
        model.addAttribute("todoDTO", todoService.getTodoById(Long.parseLong(todoId)));
        return "view-form-add";
    }

    @PostMapping("/handle-edit")
    public String onEditTodo(
            @Valid @ModelAttribute(name = "todoDTO") TodoDTO todoDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ){
        if (bindingResult.hasErrors()){
            model.addAttribute("onEdit", false);
            model.addAttribute("todoDTO", todoDTO);
            return "view-form-add";
        }

        todoService.editTodo(todoDTO);
        redirectAttributes.addFlashAttribute("messages" ,"Cập nhật thông tin công việc thành công");
        return "redirect:/todo/list";
    }

    @GetMapping("/delete-todo/{id}")
    public String onDeleteTodo(
            @PathVariable(name = "id") String delId,
            RedirectAttributes redirectAttributes
    ){
        todoService.deleteTodo(Long.parseLong(delId));
        redirectAttributes.addFlashAttribute("messages" ,"Cập nhật thông tin công việc thành công");
        return "redirect:/todo/list";
    }
}
