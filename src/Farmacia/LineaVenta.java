package Farmacia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table
public class LineaVenta implements Serializable{

	@EmbeddedId
	private MedicamentoVentaId id;
	
	@Column(nullable = false)
	private int unidades;
	
	@Column(nullable = false)
	private float importe;

	public LineaVenta() {
		super();
	}
	
	public LineaVenta(MedicamentoVentaId id, int unidades, float importe) {
		super();
		this.id = id;
		this.unidades = unidades;
		this.importe = importe;
	}


	public MedicamentoVentaId getId() {
		return id;
	}

	public void setId(MedicamentoVentaId id) {
		this.id = id;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}
	
	
	
}
