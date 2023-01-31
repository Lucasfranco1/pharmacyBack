package com.pharmacy.demo.mapper;

import com.pharmacy.demo.dto.SaleDTO;
import com.pharmacy.demo.entity.SaleEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaleMapper {

    public SaleEntity saleDTO2Entity(SaleDTO saleDTO){
        SaleEntity saleEntity = new SaleEntity();

        saleEntity.setDateAndHour(LocalDateTime.now());
        saleEntity.setMedicine(saleDTO.getMedicine());
        saleEntity.setAmount(saleDTO.getAmount());
        saleEntity.setUnitValue(saleDTO.getUnitValue());
        saleEntity.setTotalValue(saleDTO.getTotalValue());

        return saleEntity;
    }
    public SaleDTO saleEntity2DTO(SaleEntity saleEntity){
        SaleDTO dto = new SaleDTO();

        dto.setDateAndHour(LocalDateTime.now());
        dto.setMedicine(saleEntity.getMedicine());
        dto.setAmount(saleEntity.getAmount());
        dto.setUnitValue(saleEntity.getUnitValue());
        dto.setTotalValue(saleEntity.getTotalValue());

        return dto;
    }

    public List<SaleDTO> listSalesListDTO(List<SaleEntity> saleEntities){
        List<SaleDTO> saleDTOList = new ArrayList<>();

        for(SaleEntity entity : saleEntities){
            saleDTOList.add(this.saleEntity2DTO(entity));
        }
        return saleDTOList;
    }
}
