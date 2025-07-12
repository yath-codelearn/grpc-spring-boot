package asia.igsaas.customerservice.customer;

import asia.igsaas.customerservice.customer.dto.CustomerDto;
import asia.igsaas.customerservice.grpc.client.UserClientService;
import asia.igsaas.grpc.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final UserClientService userClientService;


    @GetMapping()
    public List<CustomerDto> getAllCustomers() {
        return userClientService.getAllUsers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable String id) {
        final User user = userClientService.getUser(id);
        return userClientService.convertToCustomerDto(user);
    }

    @PostMapping("/create")
    public String createCustomer(@RequestBody CustomerDto request) {
        return userClientService.createCustomer(request);
    }
}
