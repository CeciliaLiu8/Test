package test.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Document
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class User {
  @Id
  private String id;
  
  @NotBlank(message = "Phone number is required.")
  private String phone;

  @NotBlank(message = "First name is required.")
  private String firstName;
  
}
