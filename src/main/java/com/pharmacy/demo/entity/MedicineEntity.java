package com.pharmacy.demo.entity;

import lombok.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE medicines SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete = false")
public class MedicineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(length = 250, nullable = false)
    private String nameMedicine;
    @NotNull
    @Column(length = 50,  nullable = false)
    private String factoryLaboratory;

    @NotNull
    @Column(length = 50,  nullable = false)
    private String manufacturingDate;
    @NotNull
    @Column(length = 50,  nullable = false)
    private String dueDate;
    @NotNull
    @Column(length = 50,  nullable = false)
    private String stockQuantity;

    @NotNull
    @Column(length = 50,  nullable = false)
    private String unitValue;

    @Column(name = "softDelete")
    private boolean softDelete = Boolean.FALSE;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicineEntity medicineEntity = (MedicineEntity) o;
        return Objects.equals(id, medicineEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
