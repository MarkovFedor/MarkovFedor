package com.kotiki.utils;

import com.kotiki.dto.OwnerDTO;
import entities.Owner;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class OwnerDtoMapping {
    public OwnerDTO mapToDTO(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setId(owner.getId());
        ownerDTO.setName(owner.getName());
        ownerDTO.setCatsId(owner.getCats());
        String dateString = owner.getDateOfBirth().get(Calendar.DAY_OF_MONTH)
                +"-"+owner.getDateOfBirth().get(Calendar.MONTH)
                +"-"+owner.getDateOfBirth().get(Calendar.YEAR);
        ownerDTO.setDateOfBirth(dateString);
        return ownerDTO;
    }

    public Owner mapToEntity(OwnerDTO ownerDTO) throws ParseException {
        Owner owner = new Owner();
        owner.setCats(null);
        owner.setName(ownerDTO.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = sdf.parse(ownerDTO.getDateOfBirth());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        owner.setDateOfBirth(cal);
        return owner;
    }
}
