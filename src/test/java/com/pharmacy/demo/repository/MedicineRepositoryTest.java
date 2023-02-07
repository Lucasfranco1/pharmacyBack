package com.pharmacy.demo.repository;

import com.pharmacy.demo.entity.MedicineEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SpringJUnitConfig(classes = H2Config.class)
class MedicineRepositoryTest {

    @Autowired
    MedicineRepository medicineRepository;

    private MedicineEntity medicine;

    @BeforeEach
    void setup(){
         medicine = MedicineEntity.builder()
                .nameMedicine("paracetamol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();
    }

    @Test
    @DisplayName("Test para asegurar el guardado de medicina en la base de datos")
    void testSaveMedicine(){
        //given
        MedicineEntity medicine = MedicineEntity.builder()
                .nameMedicine("tafirol")
                .factoryLaboratory("bayer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();
        //when
        MedicineEntity medicineSaved = medicineRepository.save(medicine);
        //then
        assertThat(medicineSaved).isNotNull();
        assertThat(medicineSaved.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test para filtrar por nombre de medicina")
    void findAllByNameMedicine() {
        MedicineEntity medicine = MedicineEntity.builder()
                .nameMedicine("tafirol")
                .factoryLaboratory("bayer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();
        MedicineEntity medicineSaved = medicineRepository.save(medicine);

        //when
        List<MedicineEntity> medicinesEntity = medicineRepository.findAllByNameMedicine(medicine.getNameMedicine());

        //then
        assertThat(medicinesEntity).isNotNull();
    }

    @Test
    void testFindMedicineById(){
        medicineRepository.save(medicine);

        //when
        MedicineEntity medicineEntity = medicineRepository.findById(medicine.getId()).get();

        //then
        assertThat(medicineEntity).isNotNull();
    }

    @Test
    void testUpdateMedicine(){
        medicineRepository.save(medicine);

        //when
        MedicineEntity medicineSaved = medicineRepository.findById(medicine.getId()).get();
        medicineSaved.setNameMedicine("peperinol");
        medicineSaved.setFactoryLaboratory("bayeristo");
        MedicineEntity medicineEdit = medicineRepository.save(medicineSaved);
        //then
        assertThat(medicineEdit.getNameMedicine()).isEqualTo("peperinol");
        assertThat(medicineEdit.getFactoryLaboratory()).isEqualTo("bayeristo");

    }
    @Test
    void deleteMedicineById(){
        medicineRepository.save(medicine);

        //when
        medicineRepository.deleteById(medicine.getId());
        Optional<MedicineEntity> medicineDelete = medicineRepository.findById(medicine.getId());

        //then
        assertThat(medicineDelete).isNotPresent();

    }


}