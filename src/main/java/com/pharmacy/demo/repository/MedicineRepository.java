package com.pharmacy.demo.repository;


import com.pharmacy.demo.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Long> {
    @Query(value = "SELECT m FROM MedicineEntity m WHERE m.nameMedicine=:nameMedicine")
    public List<MedicineEntity> findAllByNameMedicine(String nameMedicine);

}
