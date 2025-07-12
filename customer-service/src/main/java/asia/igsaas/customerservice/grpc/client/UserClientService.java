package asia.igsaas.customerservice.grpc.client;

import asia.igsaas.customerservice.customer.dto.UserDto;
import asia.igsaas.customerservice.customer.dto.UserRequest;
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

    public List<UserDto> getAllUsers() {
        try {
            log.info("Getting all users");
            GetAllUsersRequest request = GetAllUsersRequest.newBuilder().build();
            GetAllUsersResponse response = blockingStub.getAllUsers(request);
            // Convert each user in the response to UserResponse
            return response.getUsersList().stream()
                    .map(this::mapToUserDto)
                    .toList();
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }

    public UserDto getUserById(String id) {
        try {
            log.info("Getting user with id: " + id);
            GetUserRequest request = GetUserRequest.newBuilder().setId(id).build();
            UserResponse response = blockingStub.getUser(request);
            log.info("Found user: " + response.getUser().getName());
            return mapToUserDto(response.getUser());
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }

    public String createUser(UserRequest userRequset) {
        try {
            log.info("Creating user with name: " + userRequset.getName() + ", email: " + userRequset.getEmail());
            CreateUserRequest request = CreateUserRequest.newBuilder()
                    .setName(userRequset.getName())
                    .setEmail(userRequset.getEmail())
                    .build();
            CreateUserResponse response = blockingStub.createUser(request);

            log.info("Created user with id: " + response.getId());
            return response.getId();
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }


    public void updateUser(String id, UserRequest userRequest) {
        try {
            log.info("Updating user with id: " + id);
            UpdateUserRequest request = UpdateUserRequest.newBuilder()
                    .setId(id)
                    .setName(userRequest.getName())
                    .setEmail(userRequest.getEmail())
                    .build();
            UpdateUserResponse response = blockingStub.updateUser(request);

            log.info("Updated user with name: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }


    public void deleteUser(String id) {
        try {
            log.info("Deleting user with id: " + id);
            DeleteUserRequest request = DeleteUserRequest.newBuilder()
                    .setId(id)
                    .build();
            DeleteUserResponse response = blockingStub.deleteUser(request);

            log.info("Deleted user: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            throw e;
        }
    }

    // Helper method to convert User
    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
