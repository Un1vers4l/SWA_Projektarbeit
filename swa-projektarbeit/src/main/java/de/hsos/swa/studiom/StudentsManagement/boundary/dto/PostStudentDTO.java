package de.hsos.swa.studiom.StudentsManagement.boundary.dto;

public class PostStudentDTO {
    private String vorname;
    private String nachname;

    public PostStudentDTO(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public PostStudentDTO() {
    }

    public String getVorname() {
        return this.vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }


}
