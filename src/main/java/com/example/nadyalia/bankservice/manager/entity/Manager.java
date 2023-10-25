package com.example.nadyalia.bankservice.manager.entity;

import com.example.nadyalia.bankservice.client.entity.Client;
import com.example.nadyalia.bankservice.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 1, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 1, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "status")
    private int status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Client> clients;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> products;
}
