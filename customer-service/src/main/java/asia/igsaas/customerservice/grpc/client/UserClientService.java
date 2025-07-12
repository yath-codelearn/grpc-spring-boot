package asia.igsaas.customerservice.grpc.client;

import asia.igsaas.customerservice.customer.dto.CustomerDto;
import asia.igsaas.grpc.user.*;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserClientService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    /**
     * Create a new user.
     */
    public String  createCustomer(CustomerDto customerDto) {
        try {
            log.info("Creating user with name: " + customerDto.getName() + ", email: " + customerDto.getEmail());
            CreateUserRequest request = CreateUserRequest.newBuilder()
                    .setName(customerDto.getName())
                    .setEmail(customerDto.getEmail())
                    .build();
            CreateUserResponse  response = blockingStub.createUser(request);

            log.info("Created user with id: " + response.getId());
            return response.getId();
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }

    /**
     * Get a user by ID.
     */
    public User getUser(String id) {
        log.info("Getting user with id: " + id);
        GetUserRequest request = GetUserRequest.newBuilder().setId(id).build();
        UserResponse response;
        try {
            response = blockingStub.getUser(request);
            log.info("Found user: " + response.getUser().getName());
            return response.getUser();
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }

    /**
     * Get all users.
     */
    public List<CustomerDto> getAllUsers() {
        log.info("Getting all users");
        GetAllUsersRequest request = GetAllUsersRequest.newBuilder().build();
        try {
            GetAllUsersResponse response = blockingStub.getAllUsers(request);

            // Convert each user in the response to CustomerDto
            return response.getUsersList().stream()
                    .map(this::convertToCustomerDto)
                    .peek(user -> log.info("Found user: {}", user.getName()))
                    .toList();
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }

    // Helper method to convert User to CustomerDto
    public CustomerDto convertToCustomerDto(User user) {
        return CustomerDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                // Map other fields as needed
                .build();
    }
}
