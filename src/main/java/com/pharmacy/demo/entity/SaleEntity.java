package com.pharmacy.demo.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dateAndHour;

    @ManyToOne
    private MedicineEntity medicine;

    private Integer amount;

    private Double unitValue;

    private Double totalValue;
}
