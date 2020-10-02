package Farmacia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table
public class Pedido implements Serializable{

	@Column(nullable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(nullable=false)
	private int unidades;
	
	@ManyToOne
	@JoinColumn(name = "medicamento",referencedColumnName = "codigo")
	private Medicamento medicamento;
	
	@Column(nullable=false)
	private boolean entregado;

	public Pedido() {
		super();
	}

	public Pedido(Date fecha, int unidades, Medicamento medicamento, boolean entregado) {
		super();
		this.fecha = fecha;
		this.unidades = unidades;
		this.medicamento = medicamento;
		this.entregado = entregado;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}
	
	public void mostrar() {
		System.out.println("Codigo: "+codigo+
				"\tFecha: "+fecha+
				"\tUnidades: "+unidades+
				"\tMedicamento: "+medicamento.getNombre()+
				"\tEntregado: "+entregado);
	}
	
}
