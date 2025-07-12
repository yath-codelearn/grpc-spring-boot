package asia.igsaas.customerservice.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Name is required")
    @Schema(example = "John Doe", description = "Full name of the user")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(example = "jonhdoe@example.com", description = "Valid email address")
    private String email;
}
