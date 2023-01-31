package com.pharmacy.demo.mapper;

import com.pharmacy.demo.dto.MedicineDTO;
import com.pharmacy.demo.entity.MedicineEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicineMapper {


    public MedicineEntity medicineDTO2Entity(MedicineDTO medicineDTO){
        MedicineEntity medicine = new MedicineEntity();

        medicine.setNameMedicine(medicineDTO.getNameMedicine());
        medicine.setFactoryLaboratory(medicineDTO.getFactoryLaboratory());
        medicine.setManufacturingDate(medicineDTO.getManufacturingDate());
        medicine.setDueDate(medicineDTO.getDueDate());
        medicine.setUnitValue(medicineDTO.getUnitValue());
        medicine.setStockQuantity(medicineDTO.getStockQuantity());

        return medicine;
    }

    public MedicineDTO medicineEntityToMedicineDTO(MedicineEntity medicine){
        MedicineDTO dto = new MedicineDTO();
        dto.setNameMedicine(medicine.getNameMedicine());
        dto.setFactoryLaboratory(medicine.getFactoryLaboratory());
        dto.setManufacturingDate(medicine.getManufacturingDate());
        dto.setUnitValue(medicine.getUnitValue());
        dto.setDueDate(medicine.getDueDate());
        dto.setStockQuantity(medicine.getStockQuantity());

        return dto;
    }

    public List<MedicineDTO> listMedicineListDTO(List<MedicineEntity> medicineEntities){
        List<MedicineDTO> medicineDTOList = new ArrayList<>();

        for(MedicineEntity entity : medicineEntities){
            medicineDTOList.add(this.medicineEntityToMedicineDTO(entity));
        }
        return medicineDTOList;
    }

    public List<MedicineEntity> listMedicineListEntity(List<MedicineDTO> medicineDTO){
        List<MedicineEntity> medicineEntities = new ArrayList<>();

        for(MedicineDTO dto : medicineDTO){
            medicineEntities.add(this.medicineDTO2Entity(dto));

        }
        return medicineEntities;
    }
}
