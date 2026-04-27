package todo_app.ptit_cntt1_it210_personaltodoapp.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import todo_app.ptit_cntt1_it210_personaltodoapp.model.Priority;
import todo_app.ptit_cntt1_it210_personaltodoapp.model.Status;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoDTO {
    private Long id;

    @NotBlank(message = "Vui lòng không để trốn nội dung công việc")
    private String content;

    @NotNull(message = "Vui lòng không để trống hạn công việc")
    @FutureOrPresent(message = "Hạn công việc chưa hợp lệ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @NotNull(message = "Vui lòng không để trống trạng thái công việc")
    private Status status;

    @NotNull(message = "Vui lòng không để trống độ ưu tiên cho công việc")
    private Priority priority;
}
