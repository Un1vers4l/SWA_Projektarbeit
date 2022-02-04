/**de.hsos.swa.studiom.ModulManagment.boundary.dto
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-02-03 20:18:13
 * @modify date 2022-02-03 20:18:13
 * @desc [description]
 */
package de.hsos.swa.studiom.ModulManagment.boundary.dto.modul;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import de.hsos.swa.studiom.ModulManagment.entity.Modul;

public class PostModulDto {
    @NotBlank(message="Bitte geben Sie den Modul einen Namen")
    private String name;

    @NotBlank(message="Bitte geben Sie zu diesem Projekt ein Beschreibung")
    private String description;

    @NotNull(message="Bitte geben Sie an, ob es sich um eine Projekt handelt")
    private Boolean isProject;


    public PostModulDto() {
    }

    public PostModulDto(String name, String description, Boolean isProject) {
        this.name = name;
        this.description = description;
        this.isProject = isProject;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsProject() {
        return this.isProject;
    }

    public Boolean getIsProject() {
        return this.isProject;
    }

    public void setIsProject(Boolean isProject) {
        this.isProject = isProject;
    }

    public static class Converter {

        public static Modul DtoToModul(PostModulDto postmodul){
            return new Modul(postmodul.getName(), postmodul.getDescription(), postmodul.getIsProject());
        }

    }

}
