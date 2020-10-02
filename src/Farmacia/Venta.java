package Farmacia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Venta implements Serializable{

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "id.venta")
	private List<LineaVenta> medicamentos = new ArrayList<LineaVenta>();
	
	public Venta() {
		super();
	}



	public Venta( Date fecha) {
		super();
		this.fecha = fecha;
	}



	public int getCodigo() {
		return codigo;
	}



	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	

	public List<LineaVenta> getMedicamentos() {
		return medicamentos;
	}



	public void setMedicamentos(List<LineaVenta> medicamentos) {
		this.medicamentos = medicamentos;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
}
