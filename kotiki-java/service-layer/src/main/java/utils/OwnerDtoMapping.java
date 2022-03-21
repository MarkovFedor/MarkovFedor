package utils;

import dto.OwnerDTO;
import owner.Owner;

public class OwnerDtoMapping {
    public OwnerDTO mapToDTO(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setId(owner.getId());
        ownerDTO.setName(owner.getName());
        ownerDTO.setCatsId(owner.getCats());
        ownerDTO.setDateOfBirth(owner.getDateOfBirth());
        return ownerDTO;
    }

    public Owner mapToEntity(OwnerDTO ownerDTO) {
        Owner owner = new Owner();
        owner.setId(ownerDTO.getId());
        owner.setCats(null);
        owner.setName(ownerDTO.getName());
        owner.setDateOfBirth(ownerDTO.getDateOfBirth());
        return owner;
    }
}