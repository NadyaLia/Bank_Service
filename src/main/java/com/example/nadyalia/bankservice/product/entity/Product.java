package com.example.nadyalia.bankservice.product.entity;

import com.example.nadyalia.bankservice.agreement.entity.Agreement;
import com.example.nadyalia.bankservice.manager.entity.Manager;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name shouldn't be empty")
    @NotNull(message = "Name shouldn't be null")
    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;

    @Column(name = "currency_code")
    private int currencyCode;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "`limit`")
    private int limit;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Agreement> agreements;
}
