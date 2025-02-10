package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", initialValue = 1000)
    private Integer id;

    @NotBlank(message = "Email cannot be blank")
    @Email
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    @Column(name = "email", nullable = false, length = 250, unique = true)
    private String email;

    @NotBlank(message = "Mobile number Cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be of 10 digits")
    @Column(name = "mobile_no", unique = true)
    private String mobileNo;

    @NotBlank(message = "Password cannot be blank")
    @Column(name = "password", nullable = false, length = 250)
    private String password;

    private Integer referenceId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;






    public String getName() {
        return null;
    }


    public void setName(String name) {
    }
}




