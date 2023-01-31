package com.pharmacy.demo.service.implementation;

import com.pharmacy.demo.dto.SaleDTO;
import com.pharmacy.demo.entity.MedicineEntity;
import com.pharmacy.demo.entity.SaleEntity;
import com.pharmacy.demo.exceptions.ParamNotFound;
import com.pharmacy.demo.mapper.SaleMapper;
import com.pharmacy.demo.repository.SaleRepository;
import com.pharmacy.demo.service.MedicineService;
import com.pharmacy.demo.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    SaleMapper saleMapper;

    @Autowired
    MedicineService medicineService;

    @Override
    public List<SaleDTO> getAllSale() {
        List<SaleEntity> saleEntities = (List<SaleEntity>) saleRepository.findAll();
        return saleMapper.listSalesListDTO(saleEntities);
    }

    @Override
    public SaleDTO successSale(SaleDTO saleDTO) {
        SaleEntity sale = saleMapper.saleDTO2Entity(saleDTO);
        SaleEntity saleSaved = saleRepository.save(sale);
        restStock(saleDTO);
        return saleMapper.saleEntity2DTO(saleSaved);
    }

    @Override
    public Page<SaleEntity> pages(Pageable pageable) {
       return saleRepository.findAll(pageable);
    }

    public void restStock(SaleDTO saleDTO){
        MedicineEntity medicine = medicineService.findMedicineById(saleDTO.getMedicine().getId());
        int stock = Integer.parseInt(saleDTO.getMedicine().getStockQuantity());
        int amount = saleDTO.getAmount();
        if(stock>=amount){
            int result = stock - amount;
            medicine.setStockQuantity(String.valueOf(result));
            medicineService.saveStockMedicine(medicine);
        } else {
            throw new ParamNotFound("La cantidad supera el stock disponible");
        }

    }
}
