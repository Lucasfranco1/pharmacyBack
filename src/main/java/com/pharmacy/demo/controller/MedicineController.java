package com.pharmacy.demo.controller;


import com.pharmacy.demo.dto.MedicineDTO;
import com.pharmacy.demo.entity.MedicineEntity;
import com.pharmacy.demo.exceptions.ParamNotFound;
import com.pharmacy.demo.mapper.MedicineMapper;
import com.pharmacy.demo.repository.MedicineRepository;
import com.pharmacy.demo.service.MedicineService;
import com.pharmacy.demo.service.implementation.MedicineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/medicine")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;


    @GetMapping("/medicines")
    public ResponseEntity<Page<MedicineEntity>> pages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nameMedicine") String order,
            @RequestParam(defaultValue = "true") boolean asc){
        Page<MedicineEntity> medicines = medicineService.pages(
                PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
             medicines = medicineService.pages(
                    PageRequest.of(page, size, Sort.by(order).descending()));
        return new ResponseEntity<Page<MedicineEntity>>(medicines, HttpStatus.OK);

    }

    @PostMapping(params = "name")
    public ResponseEntity<List<MedicineDTO>>findByName(@RequestParam String name){
        List<MedicineDTO> dto = medicineService.findByNameMedicine(name);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MedicineDTO> saveMedicine(@Valid @RequestBody MedicineDTO medicineDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicineService.savedMedicine(medicineDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MedicineDTO> updateMedicine(@PathVariable Long id, @Valid @RequestBody MedicineDTO medicineDTO){
        return ResponseEntity.ok(medicineService.updateMedicine(id, medicineDTO));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicineById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<MedicineEntity> getByIdEducation(@PathVariable() Long id){
        MedicineEntity medicine = medicineService.getOneMedicine(id).get();
        return new ResponseEntity(medicine, HttpStatus.OK);
    }

}
