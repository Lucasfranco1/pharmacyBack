package com.pharmacy.demo.service.implementation;

import com.pharmacy.demo.dto.MedicineDTO;
import com.pharmacy.demo.entity.MedicineEntity;
import com.pharmacy.demo.mapper.MedicineMapper;
import com.pharmacy.demo.repository.MedicineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private MedicineMapper medicineMapper;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    private MedicineEntity medicine;

    private MedicineDTO medicineDTO;

    @BeforeEach
    void setup(){
        medicine = MedicineEntity.builder()
                .id(1L)
                .nameMedicine("paracetamol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();


        medicineDTO = MedicineDTO.builder()
                .id("1")
                .nameMedicine("paracetamol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();

    }

    @Test
    void testMedicineSave(){
        //given
        MedicineEntity medicineSave = medicineMapper.medicineDTO2Entity(medicineDTO);
        given(medicineRepository.save(medicineSave)).willReturn(medicineSave);
        //when

        MedicineDTO medicineDTO1 = MedicineDTO.builder()
                .nameMedicine("tafirol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();

        medicineService.savedMedicine(medicineDTO1);

        //then
        assertThat(medicineDTO1).isNotNull();
    }

    @Test
    void testGetOneMedicine(){
        given(medicineRepository.findById(1L)).willReturn(Optional.of(medicine));

        MedicineEntity medicineEntity = medicineService.getOneMedicine(medicine.getId()).get();

        assertThat(medicineEntity).isNotNull();

    }
    @Test
    void testFindByNameMedicine(){
        given(medicineRepository.findAllByNameMedicine("paracetamol")).willReturn(List.of(medicine));

        MedicineDTO newDTO = MedicineDTO.builder()
                .nameMedicine("paracetamol")
                .build();

        List<MedicineDTO> medicineDTOList = medicineService.findByNameMedicine(newDTO.getNameMedicine());

        assertThat(medicineDTOList).isNotNull();

    }
    @Test
    void testDeleteById(){
        given(medicineRepository.findById(1L)).willReturn(Optional.of(medicine));
        medicine.setSoftDelete(true);

        medicineService.deleteMedicineById(medicine.getId());

        assertThat(medicine.isSoftDelete()).isEqualTo(true);
    }


    @Test
    void  UpdateMedicineTest(){
        //give
        Long id = 1L;
        MedicineDTO medicineDTO1 = MedicineDTO.builder()
                .nameMedicine("tafirol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();
        //when
        when(medicineRepository.findById(id)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(medicine)).thenReturn(medicine);
        medicineService.updateMedicine(id, medicineDTO1);

        //then
        assertThat(medicine.getNameMedicine()).isEqualTo("tafirol");
    }

    @Test
    void saveStockMedicineTest(){
        given(medicineRepository.findById(1L)).willReturn(Optional.of(medicine));
        medicine.setStockQuantity("3");

        medicineService.saveStockMedicine(medicine);
        MedicineEntity medicineBus = medicineService.getOneMedicine(medicine.getId()).get();


        assertThat(medicineBus.getStockQuantity()).isEqualTo("3");
    }

    @Test
    void testPage(){
        Page<MedicineEntity>medicines  = Mockito.mock(Page.class);

        when(medicineRepository.findAll(Mockito.any(Pageable.class))).thenReturn(medicines);

        medicines = medicineService.pages(Mockito.mock(Pageable.class));

        assertThat(medicines).isNotNull();
    }



}