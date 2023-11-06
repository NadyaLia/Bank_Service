package com.example.nadyalia.bankservice.client.entity;

import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.manager.entity.Manager;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    /**
     * Client's status (active, inactive, etc.)
     */
    @Column(name = "status")
    private int status;

    /**
     * Client's tax code (external ID)
     */
    @Column(name = "tax_code")
    private String taxCode;

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

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Address should not be empty")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Phone number should not be empty")
    @Column(name = "phone")
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Account> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return status == client.status && Objects.equals(id, client.id) && Objects.equals(taxCode, client.taxCode) &&
                Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) &&
                Objects.equals(userId, client.userId) && Objects.equals(email, client.email) && Objects.equals(address,
                client.address) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(createDate,
                client.createDate) && Objects.equals(updateDate, client.updateDate) && Objects.equals(manager,
                client.manager) && Objects.equals(accounts, client.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, taxCode, firstName, lastName, userId, email, address, phoneNumber, createDate,
                updateDate, manager, accounts);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", status=" + status +
                ", taxCode='" + taxCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", manager=" + manager +
                ", accounts=" + accounts +
                '}';
    }
}
