package Farmacia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Principal {
	public static Scanner t=new Scanner(System.in);
	public static Modelo far=new Modelo();
	public static Medicamento medic = new Medicamento();
	public static Proveedor prov = new Proveedor();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int opcion=0;
		if(far.getEm()!=null) {
			
			do {
				System.out.println("0.-Salir");
				System.out.println("1.Crear Medicamento");
				System.out.println("2.Registrar Venta");
				System.out.println("3.Entregar Pedido");
				System.out.println("4.Mostar estadistica de venta por medicamento");
				System.out.println("5.Borrar Medicamento");
				System.out.println();
				System.out.print("Introduce una de las opciones anteriores: ");
				opcion=t.nextInt();t.nextLine();
				System.out.println();
				
				switch(opcion) {
				case 1:
					crearMedicamento();
					break;
				case 2:	
					registrarVenta();
					break;
				case 3:	
					entregarPedido();
					break;
				case 4:
					mostrarEstadistica();
					break;
				case 5:
					borrarMedicamento();
					
					break;					
				}
			}while(opcion!=0);
		}
		far.cerrar();
		
	}
	private static void mostrarEstadistica() {
		System.out.println("Sacar unidades vendidas");
		far.mostrarEstadisticas();
		
	}
	private static void borrarMedicamento() {
		far.mostrarMedicamentos();
		medic= new Medicamento();
		String opcion="";
		System.out.println("Introduce codigo medicamento");
		medic.setCodigo(t.nextInt());t.nextLine();
		medic=far.obtenerMedicamento(medic);
		if(medic!=null) {
			if(medic.getPedidos().size()>0) {
				System.out.println("Medicamento tiene pedidos --> se borran tambien los pedidos");
			}
			if(medic.getVentas().size()>0) {
				System.out.println("Medicamento tiene ventas --> se borran tambien las ventas");
			}
			System.out.println("Desea borrar el medicamento? si/no");
			opcion=t.nextLine();
			if(opcion.equalsIgnoreCase("si")) {
				
				if(far.borrarMedicamento(medic)) {
					System.out.println("Error al borrar el medicamento");
				}
			}else {
				System.out.println("No se borrara el medicamento");
			}
		}else {
			System.out.println("No existe el Medicamento");
		}
		
	}
	private static void entregarPedido() {
		far.mostrarPedidosPendientes();
		System.out.println("Introduce el codigo del pedido");
		Pedido pedido = new Pedido();
		pedido.setCodigo(t.nextInt());t.nextLine();
		pedido = far.obtenerPedido(pedido);
		if(pedido!=null) {
			pedido.setEntregado(true);
			pedido.getMedicamento().setStockReal((pedido.getMedicamento().getStockReal()+pedido.getUnidades()));
			if(!far.modificarEntregayStock(pedido)) {
				System.out.println("Error al entregar el Pedido");
			}
		}else {
			System.out.println("No existe el pedido");
		}
		
		
	}
	private static void registrarVenta() {
		Venta venta = new Venta(new Date());
		boolean sigue=true;
		String opcion;
		do {
			far.mostrarMedicamentos();
			medic = new Medicamento();
		System.out.println("Introduce codigo del medicamento\n");
		medic.setCodigo(t.nextInt());t.nextLine();
		medic=far.obtenerMedicamento(medic);
		if(medic!=null) {
			if(medic.getStockReal()>medic.getStockMin()) {
				System.out.println("Introduce la cantidad");
				LineaVenta lv= new LineaVenta();
				lv.setUnidades(t.nextInt());t.nextLine();
				lv.setId(new MedicamentoVentaId(venta,medic));
				lv.setImporte((medic.getPrecio()*lv.getUnidades()));
				venta.getMedicamentos().add(lv);
			}else {
				System.out.println("No hay suficiente stock");
			}
		}
		else {
			System.err.println("El Medicamento no existe");
		}
		System.out.println("Desa mas medicamentos? si/no");
		opcion=t.nextLine();
		if(opcion.equalsIgnoreCase("no")) {
			sigue=false;
		}
		}while(sigue);
		if(!far.generarVenta(venta)) {
			System.out.println("Error al generar la venta");
		}
	}

	private static void crearMedicamento() {
		
		System.out.println("Introduce el nombre");
		medic.setNombre(t.nextLine());
		System.out.println("Introduce Stock Minimo");
		medic.setStockMin(t.nextInt());t.nextLine();
		System.out.println("Introduce Stock Real");
		medic.setStockReal(t.nextInt());t.nextLine();
		System.out.println("Introduce Stock Maximo");
		medic.setStockMax(t.nextInt());t.nextLine();
		System.out.println("Introduce Precio");
		medic.setPrecio(t.nextFloat());t.nextLine();
		
		//Mostrar proveedores
		if(far.mostrarProveedores()) {
		System.out.println("Introduce codigo proveedor");
		prov.setCodigo(t.nextInt());t.nextLine();
		prov=far.existeProveedor(prov);
		if(prov==null) {
			System.out.println("No existe el proveedor");
		}else {
			if(!far.registrarMedicamento(medic,prov)) {
				System.out.println("Error al registrar el medicamento");
			}
		}
		
		}else {
			System.out.println("No hay proveedores");
			System.out.println("Creando Proveedor");
			System.out.println("Introduce Nombre del Proveedor");
			prov.setNombre(t.nextLine());
			if(far.registrarProveedor(prov)) {
				System.out.println("Proveedor creado correctamente");
			}else {
				System.out.println("Error al registrar Proveedor");
			}
			if(!far.registrarMedicamento(medic,prov)) {
				System.out.println("Error al registrar el medicamento");
			}
			
		}
		
	}
	
}
