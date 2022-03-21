package com.kotiki.utils;

import entities.Cat;
import com.kotiki.dto.CatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import dao.IOwnerDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class CatDtoMapping{
    @Autowired
    private IOwnerDao ownerDao;

    public CatDTO mapToDTO(Cat cat) {
        CatDTO catDTO = new CatDTO();
        catDTO.setId(cat.getId());
        catDTO.setName(cat.getName());
        catDTO.setOwnerId(cat.getOwner()!=null?cat.getOwner().getId():null);
        catDTO.setFriendsId(cat.getFriends());
        catDTO.setBreed(cat.getBreed());
        catDTO.setDateOfBirth(cat.getDateOfBirth().toString());
        return catDTO;
    }

    public Cat mapToEntity(CatDTO catDTO) throws ParseException {
        Cat cat = new Cat();
        cat.setBreed(catDTO.getBreed());
        cat.setName(catDTO.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = sdf.parse(catDTO.getDateOfBirth());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cat.setDateOfBirth(cal);
        if(catDTO.getOwnerId()!=null) {
            cat.setOwner(ownerDao.findById(catDTO.getOwnerId()).orElse(null));
        }
        return cat;
    }
}
