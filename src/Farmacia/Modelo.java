package Farmacia;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;



public class Modelo {

	private EntityManager em=null;
	
	public Modelo() {
		try {
			em=Persistence.createEntityManagerFactory("FARMACIA").createEntityManager();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected EntityManager getEm() {
		return em;
	}

	protected void setEm(EntityManager em) {
		this.em = em;
	}
	
	public void cerrar() {
		try {
			em.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	//Metodos

	public boolean mostrarProveedores() {
		boolean resultado = false;
		try {
			Query consulta=em.createQuery("from Proveedor");
			List<Proveedor> r=consulta.getResultList();
			for(Proveedor p:r) {
				p.mostrar();
			}
			if(r.size()!=0) {
				resultado = true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return resultado;
	}

	public boolean registrarProveedor(Proveedor prov) {
		boolean resultado=false;
		/*Tambien cada vez que hagamos un insert a la base de datos debemos limpiar la cache.*/
		EntityTransaction tra=null;
		try {
			/*Siempre que vayamos a modificar debemos de hacer una transaccion.*/
			tra=em.getTransaction();
			tra.begin();
				em.persist(prov);
			tra.commit();
			resultado=true;
			em.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tra.rollback();
		}
		return resultado;
		
	}
	
	public boolean registrarMedicamento(Medicamento medic, Proveedor prov) {
		// TODO Auto-generated method stub
				boolean resultado=false;
				/*Tambien cada vez que hagamos un insert a la base de datos debemos limpiar la cache.*/
				EntityTransaction tra=null;
				try {
					/*Siempre que vayamos a modificar debemos de hacer una transaccion.*/
					tra=em.getTransaction();
					tra.begin();
						Medicamento m=new Medicamento(medic.getNombre(),medic.getStockMin(),
								medic.getStockReal(),medic.getStockMax(),
								prov,medic.getPrecio());
						em.persist(m);
					tra.commit();
					resultado=true;
					em.clear();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					tra.rollback();
				}
				return resultado;
	}
	public boolean registrarPedido(Pedido p) {
		// TODO Auto-generated method stub
				boolean resultado=false;
				/*Tambien cada vez que hagamos un insert a la base de datos debemos limpiar la cache.*/
				EntityTransaction tra=null;
				try {
					/*Siempre que vayamos a modificar debemos de hacer una transaccion.*/
					tra=em.getTransaction();
					tra.begin();
					em.persist(p);
					tra.commit();
					resultado=true;
					em.clear();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					tra.rollback();
				}
				return resultado;
	}

	public void mostrarMedicamentos() {
		try {
			Query consulta=em.createQuery("from Medicamento");
			List<Medicamento> r=consulta.getResultList();
			for(Medicamento m:r) {
				m.mostrar();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public Medicamento obtenerMedicamento(Medicamento medic) {
		Medicamento resultado= null;
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Medicamento "
					+ "where codigo = :codigo");
			consulta.setParameter("codigo", medic.getCodigo());
			List<Medicamento> r = consulta.getResultList();
			if(!r.isEmpty()) {
				resultado=r.get(0);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return resultado;
	}

	public boolean generarVenta(Venta venta) {
		boolean resultado = false;
		EntityTransaction t = null;
		try {
		
			t=em.getTransaction();
			t.begin();
			em.persist(venta);
			
			for(LineaVenta lv : venta.getMedicamentos()) {
				if(lv.getUnidades()>(lv.getId().getMedicamento().getStockReal()-lv.getId().getMedicamento().getStockMin())) {
					System.out.println("No hay suficiente stock");
					Pedido pedido = new Pedido();
					pedido.setEntregado(false);
					pedido.setFecha(new Date());
					pedido.setMedicamento(lv.getId().getMedicamento());
					pedido.setUnidades((lv.getId().getMedicamento().getStockMax()
							-lv.getId().getMedicamento().getStockReal()));
					em.persist(pedido);
				}else {
					lv.getId().getMedicamento().setStockReal((lv.getId().getMedicamento().getStockReal()-lv.getUnidades()));
					em.persist(lv);	
				}
			}
		
			//limpiamos caché de em para que se actualicen los cambios
			t.commit();
			em.clear();
			resultado=true;
			
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			e.printStackTrace();
		}
		return resultado;
	}

	public Proveedor existeProveedor(Proveedor prov) {
		Proveedor resultado= null;
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Proveedor "
					+ "where codigo = :codigo");
			consulta.setParameter("codigo", prov.getCodigo());
			List<Proveedor> r = consulta.getResultList();
			if(!r.isEmpty()) {
				resultado=r.get(0);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultado;
	}

	public void mostrarPedidosPendientes() {
		try {
			Query consulta=em.createQuery("from Pedido where entregado =: entregado");
			consulta.setParameter("entregado", false);
			List<Pedido> r=consulta.getResultList();
			for(Pedido p:r) {
				p.mostrar();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

	public Pedido obtenerPedido(Pedido pedido) {
		Pedido resultado= null;
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Pedido "
					+ "where codigo = :codigo");
			consulta.setParameter("codigo", pedido.getCodigo());
			List<Pedido> r = consulta.getResultList();
			if(!r.isEmpty()) {
				resultado=r.get(0);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return resultado;
	}

	public boolean modificarEntregayStock(Pedido pedido) {
		boolean resultado=false;
		
		EntityTransaction tra=null;
		try {

			tra=em.getTransaction();
			tra.begin();
				
			tra.commit();
			resultado=true;
			em.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tra.rollback();
		}
		return resultado;
	}
	public boolean borrarProveedor(Proveedor p) {
		boolean resultado = false;
		EntityTransaction t=null;
		try {
			t=em.getTransaction();
			t.begin();
			Query consulta=em.createQuery("delete from Proveedor where codigo=:codigo");
			consulta.setParameter("codigo", p.getCodigo());
			int r=consulta.executeUpdate();
			if(r==1) {
				resultado=true;
			}
			t.commit();
			em.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			t.rollback();
		}
		return resultado;
	}
	public boolean borrarMedicamento(Medicamento medic) {
		boolean resultado = false;
		EntityTransaction t=null;
		try {
			t=em.getTransaction();
			t.begin();
			Query consulta3=em.createQuery("delete from LineaVenta where id.medicamento.codigo=:pCodigo3");
			consulta3.setParameter("pCodigo3", medic.getCodigo());
			int r3=consulta3.executeUpdate();
			
			Query consulta=em.createQuery("delete from Pedido where medicamento.codigo=:pCodigo1");
			consulta.setParameter("pCodigo1", medic.getCodigo());
			int r1=consulta.executeUpdate();
			
			Query consulta2=em.createQuery("delete from Medicamento where codigo=:pCodigo2");
			consulta2.setParameter("pCodigo2", medic.getCodigo());
			int r2=consulta2.executeUpdate();	
			
			if(r1==1 && r2==1 && r3==1) {
				resultado=true;
			}
			t.commit();
			em.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			t.rollback();
		}
	
	return resultado;
	}

	public List<Medicamento> obtenerMedicamentos() {
		List<Medicamento> r =null;
		
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Medicamento");
			r = consulta.getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return r;
	}

	public void mostrarEstadisticas() {

		try {
			Query consulta = 
					em.createQuery("select id.medicamento.nombre, count(unidades), sum(importe) from LineaVenta group by id.medicamento.codigo");
			List<Object[]> r = consulta.getResultList();
			for(Object[] o : r) {
				System.out.println("Nombre: "+ (String)o[0]+
						"\tUnidades Vendidas: "+(Long)o[1]+
						"\tImporte Total: "+(Double)o[2]);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public List<Venta> obtenerVenta() {
		List<Venta> r =null;
		
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Venta");
			r = consulta.getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return r;
	}
	
	public List<LineaVenta> obtenerLineaVenta() {
		List<LineaVenta> r =null;
		
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from LineaVenta");
			r = consulta.getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return r;
	}
	public boolean modificarProveedor(Proveedor p) {
		boolean resultado=false;
		
		EntityTransaction tra=null;
		try {

			tra=em.getTransaction();
			tra.begin();
			em.merge(p);
			tra.commit();
			resultado=true;
			em.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tra.rollback();
		}
		return resultado;
	}
	public boolean entregarPedido(Pedido p) {
		boolean resultado=false;
		
		EntityTransaction tra=null;
		try {

			tra=em.getTransaction();
			tra.begin();
			p.setEntregado(true);
			em.merge(p);
			tra.commit();
			resultado=true;
			em.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tra.rollback();
		}
		return resultado;
	}
	public boolean modificarMedicamento(Medicamento medic) {
		boolean resultado=false;
		
		EntityTransaction tra=null;
		try {

			tra=em.getTransaction();
			tra.begin();
			em.merge(medic);
			tra.commit();
			resultado=true;
			em.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tra.rollback();
		}
		return resultado;
	}
	
	public List<Proveedor> obtenerProveedores() {
		List<Proveedor> r =null;
		
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Proveedor");
			r = consulta.getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return r;
	}
	public List<Pedido> obtenerPedidos() {
		List<Pedido> r =null;
		
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Pedido");
			r = consulta.getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return r;
	}
	
	public List<Venta> obtenerVentasporFecha(Venta venta) {
		 List<Venta> resultado= null;
		try {
			//Recuperamos de la bd el medicamento buscado
			Query consulta = em.createQuery("from Venta "
					+ "where fecha = :fecha");
			consulta.setParameter("codigo",venta.getFecha());
			resultado = consulta.getResultList();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return resultado;
	}
	
}
