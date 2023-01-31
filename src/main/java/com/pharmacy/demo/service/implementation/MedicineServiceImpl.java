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



    @Transactional
    public MedicineDTO updateMedicine(Long id, MedicineDTO medicineDTO) {
        Optional<MedicineEntity> medicines = medicineRepository.findById(id);
        if(medicines.isPresent()){
            MedicineEntity medicine = medicines.get();
            medicine.setNameMedicine(medicineDTO.getNameMedicine());
            medicine.setFactoryLaboratory(medicineDTO.getFactoryLaboratory());
            medicine.setManufacturingDate(medicineDTO.getManufacturingDate());
            medicine.setDueDate(medicineDTO.getDueDate());
            medicine.setStockQuantity(medicineDTO.getStockQuantity());
            medicine.setUnitValue(medicineDTO.getUnitValue());
            medicineRepository.save(medicine);
            return medicineMapper.medicineEntityToMedicineDTO(medicine);
        }

        throw new ParamNotFound("EL id no está en la base de datos");

    }

    public void deleteMedicineById(Long id) {
        try {
            MedicineEntity medicineEntity = medicineRepository.findById(id).get();
            medicineRepository.delete(medicineEntity);
        }catch (Exception e){
            throw new ParamNotFound("El id no puede ser nulo " + e.getMessage());
        }
    }

    @Override
    public MedicineEntity findMedicineById(Long id) {
        return medicineRepository.findById(id).orElse(null);
    }

    @Override
    public MedicineEntity saveStockMedicine(MedicineEntity medicine) {
        MedicineEntity medicineEntity = medicineRepository.findById(medicine.getId()).get();
        return medicineRepository.save(medicineEntity);
    }

    @Override
    public List<MedicineDTO> findByNameMedicine(String name) {
        List<MedicineEntity> medicineEntities =  medicineRepository.findAllByNameMedicine(name);
        return medicineMapper.listMedicineListDTO(medicineEntities);

    }

    @Override
    public Optional<MedicineEntity> getOneEducation(Long id) {
        return medicineRepository.findById(id);
    }

    public MedicineDTO savedMedicine(MedicineDTO medicineDTO){
        MedicineEntity medicine = medicineMapper.medicineDTO2Entity(medicineDTO);
        MedicineEntity savedMedicine = medicineRepository.save(medicine);
        return medicineMapper.medicineEntityToMedicineDTO(savedMedicine);

    }
}
