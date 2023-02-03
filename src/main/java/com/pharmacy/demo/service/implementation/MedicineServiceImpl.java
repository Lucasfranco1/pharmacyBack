package com.pharmacy.demo.service.implementation;

import com.pharmacy.demo.dto.MedicineDTO;
import com.pharmacy.demo.entity.MedicineEntity;
import com.pharmacy.demo.exceptions.ParamNotFound;
import com.pharmacy.demo.mapper.MedicineMapper;
import com.pharmacy.demo.repository.MedicineRepository;
import com.pharmacy.demo.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    MedicineMapper medicineMapper;

    public Page<MedicineEntity> pages(Pageable pageable) {
        return medicineRepository.findAll(pageable);
    }

    public MedicineDTO updateMedicine(Long id, MedicineDTO medicineDTO) {
         MedicineEntity medicine = medicineRepository.findById(id).orElseThrow(() -> new ParamNotFound("The medicine is not found in the database"));
         medicine.setNameMedicine(medicineDTO.getNameMedicine());
         medicine.setFactoryLaboratory(medicineDTO.getFactoryLaboratory());
         medicine.setDueDate(medicineDTO.getDueDate());
         medicine.setStockQuantity(medicineDTO.getStockQuantity());
         medicine.setManufacturingDate(medicineDTO.getManufacturingDate());
         medicine.setUnitValue(medicineDTO.getUnitValue());
         medicineRepository.save(medicine);
         return medicineMapper.medicineEntityToMedicineDTO(medicine);
    }
    public void deleteMedicineById(Long id) {
        MedicineEntity medicineEntity = medicineRepository.findById(id).get();
        medicineRepository.delete(medicineEntity);
    }
    public void saveStockMedicine(MedicineEntity medicine) {
        MedicineEntity medicineEntity = getOneMedicine(medicine.getId()).get();
        medicineRepository.save(medicineEntity);
    }
    public List<MedicineDTO> findByNameMedicine(String name) {
        List<MedicineEntity> medicineEntities =  medicineRepository.findAllByNameMedicine(name);
        return medicineMapper.listMedicineListDTO(medicineEntities);
    }
    public Optional<MedicineEntity> getOneMedicine(Long id) {
        return medicineRepository.findById(id);
    }
    public MedicineDTO savedMedicine(MedicineDTO medicineDTO){
        MedicineEntity medicine = medicineMapper.medicineDTO2Entity(medicineDTO);
        MedicineEntity savedMedicine = medicineRepository.save(medicine);
        return medicineMapper.medicineEntityToMedicineDTO(savedMedicine);
    }
}
