package com.pharmacy.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineDTO  {

    private String id;
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
