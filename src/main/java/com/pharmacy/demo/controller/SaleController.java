package com.pharmacy.demo.controller;

import com.pharmacy.demo.dto.SaleDTO;
import com.pharmacy.demo.entity.SaleEntity;
import com.pharmacy.demo.service.SaleService;
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
@RequestMapping("/sales")
@CrossOrigin(origins = "http://localhost:4200")
public class SaleController {

    @Autowired
    SaleService saleService;

    @PostMapping("/sale")
    public ResponseEntity<SaleDTO> saveMedicine(@Valid @RequestBody SaleDTO saleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.successSale(saleDTO));
    }

    @GetMapping("/allLastDate")
    public ResponseEntity<List<SaleDTO>>listByDate(){
        return ResponseEntity.ok(saleService.getAllSale());
    }
    @GetMapping("/soldOut")
    public ResponseEntity<Page<SaleEntity>> pages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateAndHour") String order,
            @RequestParam(defaultValue = "true") boolean asc){
        Page<SaleEntity> sales = saleService.pages(
                PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
            sales = saleService.pages(
                    PageRequest.of(page, size, Sort.by(order).descending()));
        return new ResponseEntity<Page<SaleEntity>>(sales, HttpStatus.OK);

    }
}