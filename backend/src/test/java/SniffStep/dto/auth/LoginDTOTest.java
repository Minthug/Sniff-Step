package SniffStep.dto.auth;

import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoginDTOTest {

    @Test
    void recordLoginDTOTEST_functionTest() {
            LoginDTO dto = new LoginDTO("test@email.com", "password");
            assertNotNull(dto.getEmail());
            assertNotNull(dto.getPassword());
    }

    @Test
    void validation_test() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LoginRequest request = new LoginRequest(null, null);
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        assertEquals(2, violations.size());
    }
}
