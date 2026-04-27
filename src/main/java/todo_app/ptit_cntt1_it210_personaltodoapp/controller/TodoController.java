package todo_app.ptit_cntt1_it210_personaltodoapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import todo_app.ptit_cntt1_it210_personaltodoapp.dto.TodoDTO;
import todo_app.ptit_cntt1_it210_personaltodoapp.service.ITodoService;

@Controller
@RequestMapping("todo")
@RequiredArgsConstructor
@SessionAttributes("username") // Khai báo quản lý 'username' bằng Session
public class TodoController {

    private final ITodoService todoService;

    @ModelAttribute("username")
    public String defaultUsername() {
        return "";
    }

    @GetMapping("/login")
    public String login() {
        return "form-input-name";
    }

    @PostMapping("/on-login")
    public String onLogin(
            @RequestParam("username") String username,
            Model model
    ){
        if (username == null || username.trim().isEmpty()){
            return "form-input-name";
        }
        // Khi thêm vào model, Spring sẽ tự động đồng bộ lên SessionAttributes
        model.addAttribute("username", username);
        return "redirect:/todo/list";
    }

    @GetMapping("/list")
    public String todoList(
            @ModelAttribute("username") String username,
            Model model
    ) {
        if (username.isEmpty()) return "redirect:/todo/login";

        model.addAttribute("todoList", todoService.getAllTodos());
        return "view-todo-list";
    }

    @GetMapping("/view-add")
    public String viewAdd(@ModelAttribute("username") String username, Model model) {
        if (username.isEmpty()) return "redirect:/todo/login";

        model.addAttribute("onEdit", false);
        model.addAttribute("todoDTO", new TodoDTO());
        return "view-form-add";
    }

    @PostMapping("/handle-add")
    public String onAddNew(
            @Valid @ModelAttribute("todoDTO") TodoDTO todoDTO,
            BindingResult bindingResult,
            @ModelAttribute("username") String username,
            RedirectAttributes redirectAttributes,
            Model model
    ){
        if (username.isEmpty()) return "redirect:/todo/login";

        if (bindingResult.hasErrors()){
            model.addAttribute("onEdit", false);
            return "view-form-add";
        }

        todoService.addNewTodo(todoDTO);
        redirectAttributes.addFlashAttribute("messages", "Thêm công việc mới thành công");
        return "redirect:/todo/list";
    }

    @GetMapping("/view-edit/{id}")
    public String viewEdit(
            @PathVariable("id") Long id,
            @ModelAttribute("username") String username,
            Model model
    ){
        if (username.isEmpty()) return "redirect:/todo/login";

        model.addAttribute("onEdit", true);
        model.addAttribute("todoDTO", todoService.getTodoById(id));
        return "view-form-add";
    }

    @PostMapping("/handle-edit")
    public String onEditTodo(
            @Valid @ModelAttribute("todoDTO") TodoDTO todoDTO,
            BindingResult bindingResult,
            @ModelAttribute("username") String username,
            RedirectAttributes redirectAttributes,
            Model model
    ){
        if (username.isEmpty()) return "redirect:/todo/login";

        if (bindingResult.hasErrors()){
            model.addAttribute("onEdit", true);
            return "view-form-add";
        }

        todoService.editTodo(todoDTO);
        redirectAttributes.addFlashAttribute("messages" ,"Cập nhật thành công");
        return "redirect:/todo/list";
    }

    @GetMapping("/delete-todo/{id}")
    public String onDeleteTodo(
            @PathVariable Long id,
            @ModelAttribute("username") String username
    ) {
        if (username.isEmpty()) return "redirect:/todo/login";

        todoService.deleteTodo(id);
        return "redirect:/todo/list";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        // Đánh dấu hoàn tất session để Spring xóa các attribute trong @SessionAttributes
        sessionStatus.setComplete();
        return "redirect:/todo/login";
    }
}