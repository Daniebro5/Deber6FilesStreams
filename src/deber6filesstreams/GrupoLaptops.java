/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deber6filesstreams;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author dannibrito
 */
public class GrupoLaptops {
    @XmlElement(name="laptop")
    private final List<Laptop> laptops = new ArrayList<>();

    public List<Laptop> getLaptops() {
        return laptops;
    }
    
}
