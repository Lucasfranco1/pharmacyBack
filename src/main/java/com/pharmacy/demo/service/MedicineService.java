package com.pharmacy.demo.service;

import com.pharmacy.demo.dto.MedicineDTO;
import com.pharmacy.demo.entity.MedicineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicineService {

    public Page<MedicineEntity> pages (Pageable pageable);
    //public List<MedicineDTO> getAllMedicine();
    public MedicineDTO savedMedicine(MedicineDTO medicineDTO);
    public MedicineDTO updateMedicine(Long id, MedicineDTO medicineDTO);
    public void deleteMedicineById(Long id);
    public MedicineEntity findMedicineById(Long id);
    public MedicineEntity saveStockMedicine(MedicineEntity medicine);

    public List<MedicineDTO>findByNameMedicine(String name);

    public Optional<MedicineEntity> getOneEducation(Long id);
}
