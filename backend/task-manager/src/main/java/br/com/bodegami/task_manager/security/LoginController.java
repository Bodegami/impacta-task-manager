package br.com.bodegami.task_manager.security;

import br.com.bodegami.task_manager.application.usecase.AuthenticateUserUseCase;
import br.com.bodegami.task_manager.application.usecase.TestAuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final TestAuthenticationUseCase testAuthenticationUseCase;

    @PostMapping
    public ResponseEntity<RecoveryJwtTokenDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDto) {
        return ResponseEntity.ok(authenticateUserUseCase.execute(loginUserDto));
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return testAuthenticationUseCase.execute(null);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return testAuthenticationUseCase.execute("customer");
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return testAuthenticationUseCase.execute("administrator");
    }

}
