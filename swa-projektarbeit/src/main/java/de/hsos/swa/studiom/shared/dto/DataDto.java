/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:10:18
 * @modify date 2022-01-31 15:10:18
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.dto;

import java.util.List;

public class DataDto<T> {
    private List<T> data;

    public DataDto(List<T> data) {
        this.data = data;
    }

    public DataDto() {
    }

    public List<T> getdata() {
        return this.data;
    }

    public void setdata(List<T> data) {
        this.data = data;
    }

}
