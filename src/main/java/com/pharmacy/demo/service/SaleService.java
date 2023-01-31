package com.pharmacy.demo.service;

import com.pharmacy.demo.dto.SaleDTO;

import com.pharmacy.demo.entity.SaleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleService {
    public List<SaleDTO> getAllSale();

    public SaleDTO successSale(SaleDTO saleDTO);
    public Page<SaleEntity> pages (Pageable pageable);
}
