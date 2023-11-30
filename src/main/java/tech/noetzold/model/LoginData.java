package tech.noetzold.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RegisterForReflection
public class LoginData {
    private String email;
    private String password;

}
