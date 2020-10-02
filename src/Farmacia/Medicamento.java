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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Medicamento implements Serializable{

	@Column(nullable=false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	@Column(nullable=false)
	private String nombre;
	
	@Column(nullable=false)
	private int stockMin;
	
	@Column(nullable=false)
	private int stockReal;
	
	@Column(nullable=false)
	private int stockMax;
	
	@ManyToOne
	@JoinColumn(name = "proveedor",referencedColumnName = "codigo")
	private Proveedor proveedor;
	
	@Column(nullable=false)
	private float precio;

	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "id.medicamento")
	private List<LineaVenta> ventas = new ArrayList<LineaVenta>();
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "medicamento")
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	
	
	public Medicamento() {
		super();
	}

	public Medicamento(String nombre, int stockMin, int stockReal, int stockMax, Proveedor proveedor,
			float precio) {
		super();
		this.nombre = nombre;
		this.stockMin = stockMin;
		this.stockReal = stockReal;
		this.stockMax = stockMax;
		this.proveedor = proveedor;
		this.precio = precio;
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

	public int getStockMin() {
		return stockMin;
	}

	public void setStockMin(int stockMin) {
		this.stockMin = stockMin;
	}

	public int getStockReal() {
		return stockReal;
	}

	public void setStockReal(int stockReal) {
		this.stockReal = stockReal;
	}

	public int getStockMax() {
		return stockMax;
	}

	public void setStockMax(int stockMax) {
		this.stockMax = stockMax;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	

	public List<LineaVenta> getVentas() {
		return ventas;
	}

	public void setVentas(List<LineaVenta> ventas) {
		this.ventas = ventas;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public void mostrar() {
		System.out.println("Codigo: "+codigo+
				"\tNombre: "+nombre+
				"\tStockMinimo: "+stockMin+
				"\tStockReal: "+stockReal+
				"\tStockMaximo: "+stockMax+
				"\tProveedor: "+proveedor.getNombre()+
				"\tPrecio: "+precio);
	}
	
}
