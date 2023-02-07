package com.pharmacy.demo.repository;

import com.pharmacy.demo.entity.MedicineEntity;
import com.pharmacy.demo.entity.SaleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SpringJUnitConfig(classes = H2Config.class)
class SaleRepositoryTest {

    @Autowired
    SaleRepository saleRepository;

    private SaleEntity sale;

    private MedicineEntity medicine;

    @BeforeEach
    void setup() {
        medicine = MedicineEntity.builder()
                .nameMedicine("paracetamol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();


        sale = SaleEntity.builder()
                .dateAndHour(LocalDateTime.now())
                .medicine(medicine)
                .amount(123)
                .unitValue(12.0)
                .totalValue(23.0)
                .build();
    }

    @Test
    @DisplayName("Test para asegurar el guardado de venta en la base de datos")
    void testSaveVenta() {
        //given
        //when
        SaleEntity saleSave = saleRepository.save(sale);
        //then
        assertThat(saleSave).isNotNull();
    }

    @Test
    void testFindMedicineById(){
        saleRepository.save(sale);

        //when
        SaleEntity saleEntityById = saleRepository.findById(sale.getId()).get();

        //then
        assertThat(saleEntityById).isNotNull();
    }
    @Test
    void testUpdateMedicine(){
        saleRepository.save(sale);

        //when
        SaleEntity saleEntityById = saleRepository.findById(sale.getId()).get();
        saleEntityById.setAmount(1);
        saleEntityById.setUnitValue(1.4);
        SaleEntity saleEdit = saleRepository.save(saleEntityById);
        //then
        assertThat(saleEdit.getAmount()).isEqualTo(1);
        assertThat(saleEdit.getUnitValue()).isEqualTo(1.4);

    }
    @Test
    void deleteMedicineById(){
        saleRepository.save(sale);

        //when
        saleRepository.deleteById(sale.getId());
        Optional<SaleEntity> saleDelete = saleRepository.findById(sale.getId());

        //then
        assertThat(saleDelete).isNotPresent();

    }
}