package com.example.nadyalia.bankservice.agreement.entity;

import com.example.nadyalia.bankservice.account.entity.Account;
import com.example.nadyalia.bankservice.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agreement")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "status")
    private int status;

    @Column(name = "sum")
    private BigDecimal sum;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
}
