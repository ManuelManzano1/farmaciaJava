package Farmacia;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class MedicamentoVentaId implements Serializable{
	@ManyToOne
	@JoinColumn(name="venta", referencedColumnName = "codigo")
	private Venta venta;
	
	@ManyToOne
	@JoinColumn(name="medicamento", referencedColumnName = "codigo")
	private Medicamento medicamento;

	public MedicamentoVentaId() {
		super();
	}

	public MedicamentoVentaId(Venta venta, Medicamento medicamento) {
		super();
		this.venta = venta;
		this.medicamento = medicamento;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
	
	
}
