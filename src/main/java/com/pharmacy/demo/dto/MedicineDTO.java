package com.pharmacy.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class MedicineDTO {

    @NotNull
    @NotBlank
    private String nameMedicine;

    @NotNull
    @NotBlank
    private String factoryLaboratory;

    @NotNull
    @NotBlank
    private String manufacturingDate;

    @NotNull
    @NotBlank
    private String dueDate;

    @NotNull
    @NotBlank
    private String stockQuantity;

    @NotNull
    @NotBlank
    private String unitValue;

}
