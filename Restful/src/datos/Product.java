package datos;

import java.io.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "producto")
public class Product implements Serializable{
	private String name;
	private double id;
	
	@XmlElement(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement(name = "id")
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	
	public Product(String name, double id) {
		super();
		this.name = name;
		this.id = id;
	}
	public Product() {
		super();
	}
}
