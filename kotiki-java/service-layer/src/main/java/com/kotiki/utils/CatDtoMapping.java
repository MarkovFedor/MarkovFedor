package com.kotiki.utils;

import entities.Cat;
import com.kotiki.dto.CatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dao.IOwnerDao;

@Service
public class CatDtoMapping{
    @Autowired
    private IOwnerDao ownerDao;

    public CatDTO mapToDTO(Cat cat) {
        CatDTO catDTO = new CatDTO();
        catDTO.setId(cat.getId());
        catDTO.setName(cat.getName());
        catDTO.setOwnerId(cat.getOwner().getId());
        catDTO.setFriendsId(cat.getFriends());
        catDTO.setBreed(cat.getBreed());
        catDTO.setDateOfBirth(cat.getDateOfBirth());
        return catDTO;
    }

    public Cat mapToEntity(CatDTO catDTO) {
        Cat cat = new Cat();
        cat.setBreed(catDTO.getBreed());
        cat.setId(catDTO.getId());
        cat.setName(catDTO.getName());
        cat.setDateOfBirth(catDTO.getDateOfBirth());
        cat.setOwner(ownerDao.findById(catDTO.getOwnerId()).orElse(null));
        return cat;
    }
}
