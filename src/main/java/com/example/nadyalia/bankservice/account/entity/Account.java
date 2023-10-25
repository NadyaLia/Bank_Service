package com.example.nadyalia.bankservice.account.entity;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.client.entity.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotEmpty(message = "Name shouldn't be empty")
    @NotNull(message = "Name shouldn't be null")
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "status")
    private int status;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "currency_code")
    private int currencyCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Agreement> agreements;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
}
