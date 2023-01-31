package com.pharmacy.demo.dto;

import com.pharmacy.demo.entity.MedicineEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
public class SaleDTO {

    private LocalDateTime dateAndHour;

    @NotNull
    private MedicineEntity medicine;
    @NotNull
    private Integer amount;
    @NotNull
    private Double unitValue;
    @NotNull
    private Double totalValue;
}
