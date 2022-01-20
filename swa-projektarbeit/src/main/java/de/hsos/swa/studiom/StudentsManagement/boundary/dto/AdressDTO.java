package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

import de.hsos.swa.studiom.StudentsManagement.entity.Adress;

public class AdressDTO {
    public String street;
    public int nr;
    public int zipCode;
    public String town;

    public AdressDTO(String street, int nr, int zipCode, String town) {
        this.street = street;
        this.nr = nr;
        this.zipCode = zipCode;
        this.town = town;
    }

    public AdressDTO(){

    }
    
    public static class Converter {
        public static AdressDTO toDto(Adress adress) {
            return new AdressDTO(adress.getStreet(), adress.getNr(), adress.getZipCode(), adress.getTown());
        }

        public static Adress toAdress(AdressDTO aDto) {
            Adress adress = new Adress();
            adress.setStreet(aDto.street);
            adress.setNr(aDto.nr);
            adress.setTown(aDto.town);
            adress.setZipCode(aDto.zipCode);
            return adress;
        }
    }

}
