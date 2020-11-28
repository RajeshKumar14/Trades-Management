package com.simpragma.TradesManagement.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "trade_master")
public class Trade {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "user")
    private String user;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "shares")
    private int shares;

    @Column(name = "price")
    private double price;

    @Column (name = "created_at")
    private Timestamp created_at;

    @Column (name = "updated_at")
    private Timestamp updated_at;
}
