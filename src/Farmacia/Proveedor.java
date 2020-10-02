package Farmacia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Proveedor implements Serializable{

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	@Column(nullable = false)
	private String nombre;
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "proveedor")
	private List<Medicamento> medicamentos = new ArrayList<Medicamento>();

	public Proveedor() {
		super();
	}

	public Proveedor(String nombre) {
		super();
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void mostrar() {
		System.out.println("Codigo: "+codigo+
				"\tNombre:"+nombre);
	}
}
