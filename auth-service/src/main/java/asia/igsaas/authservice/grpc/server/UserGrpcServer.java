package asia.igsaas.authservice.grpc.server;

import asia.igsaas.grpc.user.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.*;

@Slf4j
@GrpcService
public class UserGrpcServer extends UserServiceGrpc.UserServiceImplBase {

    private final Map<String, User> userStore = new HashMap<>();

    public UserGrpcServer() {
        String id1 = UUID.randomUUID().toString();
        userStore.put(id1, User.newBuilder().setId(id1).setName("John").setEmail("john@doe.com").build());

        String id2 = UUID.randomUUID().toString();
        userStore.put(id2, User.newBuilder().setId(id2).setName("Jane").setEmail("jane@doe.com").build());

        String id3 = UUID.randomUUID().toString();
        userStore.put(id3, User.newBuilder().setId(id3).setName("Bob").setEmail("bob@doe.com").build());
    }

    @Override
    public void createUser(CreateUserRequest request,
                           StreamObserver<CreateUserResponse> responseObserver) {
        try {
            String id = UUID.randomUUID().toString();
            User user = User.newBuilder()
                    .setId(id)
                    .setName(request.getName())
                    .setEmail(request.getEmail())
                    .build();
            System.out.println(user);
            userStore.put(id, user);

            responseObserver.onNext(CreateUserResponse.newBuilder()
                    .setId(id)
                    .setMessage("User created successfully")
                    .build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error creating user: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getUser(GetUserRequest request,
                        StreamObserver<UserResponse> responseObserver) {
        try {
            System.out.println(request.getId());
            User user = userStore.get(request.getId());
            if (user == null) {
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("User not found")
                        .asRuntimeException());
                return;
            }

            responseObserver.onNext(UserResponse.newBuilder()
                    .setUser(user)
                    .build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error fetching user: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getAllUsers(GetAllUsersRequest request,
                            StreamObserver<GetAllUsersResponse> responseObserver) {
        try {
            List<User> users = new ArrayList<>(userStore.values());

            responseObserver.onNext(GetAllUsersResponse.newBuilder()
                    .addAllUsers(users)
                    .build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error fetching users: " + e.getMessage())
                    .asRuntimeException());
        }
    }

}
