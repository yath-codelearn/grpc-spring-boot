package asia.igsaas.customerservice.customer.controller;

import asia.igsaas.customerservice.customer.dto.UserDto;
import asia.igsaas.customerservice.customer.dto.UserRequest;
import asia.igsaas.customerservice.grpc.client.UserClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/users")
@RequiredArgsConstructor
public class CustomerController {

    private final UserClientService userClientService;

    @GetMapping()
    @Operation(summary = "Get all users", description = "Returns list of all users")
    public List<UserDto> getAllUsers() {
        return userClientService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user with given ID")
    public UserDto getUserById(@PathVariable String id) {
        return userClientService.getUserById(id);
    }

    @PostMapping("/create")
    @Operation(summary = "Create user", description = "Return a user ID")
    public String createUser(@RequestBody @Valid UserRequest request) {
        return userClientService.createUser(request);
    }


    @PatchMapping("/update/{id}")
    @Operation(summary = "Update user", description = "No response")
    public void updateUser(@PathVariable String id, @RequestBody @Valid UserRequest request) {
        userClientService.updateUser(id, request);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete user", description = "No response")
    public void deleteUser(@PathVariable String id) {
        userClientService.deleteUser(id);
    }
}
