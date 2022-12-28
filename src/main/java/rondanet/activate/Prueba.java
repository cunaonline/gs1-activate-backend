package rondanet.activate;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import rondanet.activate.entidades.Actividad;
import rondanet.activate.entidades.Empresa;
import rondanet.activate.entidades.Marca;
import rondanet.activate.entidades.Producto;
import rondanet.activate.enums.EstadoProducto;

import rondanet.activate.repository.IEmpresaRepository;

@Component
public class Prueba {

	IEmpresaRepository empresa;

	public Prueba(IEmpresaRepository empresa) {
		this.empresa = empresa;
	}

	public void crearProducto() {
		Producto produ = new Producto();
		produ.setGtin(773654213);
		produ.setDescripcion("Primero");
		produ.setMarca("Mi marca");
		produ.setSubMarca("Segunda");
		Set<String> paisObjetivo = new HashSet<>();
		paisObjetivo.add("Uruguay");
		paisObjetivo.add("Argentina");
		produ.setMercadoObjetivo(paisObjetivo);
		produ.setFoto("url/tcs/tcss.png");

		Set<Producto> productos = new HashSet<Producto>();
		productos.add(produ);

		Set<Marca> marcas = new HashSet<Marca>();
		marcas.add(new Marca(0, "Agregar Nueva Marca"));
		marcas.add(new Marca(1, "Salus"));
		marcas.add(new Marca(2,"Cafe Netle"));
		marcas.add(new Marca(3,"Coca Cola"));
		marcas.add(new Marca(4,"Pan Bimbo"));
		marcas.add(new Marca(5,"Corona"));
		marcas.add(new Marca(6,"Patricia"));

		Set<Marca> subMarcas = new HashSet<Marca>();
		subMarcas.add(new Marca(0, "Agregar Nueva SubMarca"));
		subMarcas.add(new Marca(1,"Salus Plus"));
		subMarcas.add(new Marca(2,"Salus Manzana"));

		Actividad act1 = new Actividad();
		// act1.setFecha(new DateTime());
		act1.setEstado(EstadoProducto.Desarrollo);
		act1.setDescripcion("7730961920040 Lavandina en Gel Recarga");
		act1.setUsuario("Hernan Sagastume");
		Actividad act2 = new Actividad();
		// act2.setFecha(new DateTime());
		act2.setEstado(EstadoProducto.Alta);
		act2.setDescripcion("7730961920033 Desodorante Aerosol Twist Roser 113 g");
		act2.setUsuario("Hernan Sagastume");
		Actividad act3 = new Actividad();
		// act3.setFecha(new DateTime());
		act3.setEstado(EstadoProducto.Baja);
		act3.setDescripcion("7730961920088 Detergente Liquido Active Gel Pomelo SEN 600 ml");
		act3.setUsuario("Hernan Sagastume");
		Actividad act4 = new Actividad();
		// act4.setFecha(new DateTime());
		act4.setEstado(EstadoProducto.Borrador);
		act4.setDescripcion("7730961920125 Shampoo cabello suave Aroma Floral Roser 500 ml");
		act4.setUsuario("Hernan Sagastume");
		Set<Actividad> actividad = new HashSet<Actividad>();

		actividad.add(act1);
		actividad.add(act2);
		actividad.add(act3);
		actividad.add(act4);

		Empresa empresa = new Empresa();
		empresa.setGln("1777777");
		empresa.setNombre("Unilever");
		empresa.setRut("1777777");
		empresa.setProductos(productos);
		empresa.setMarcas(marcas);
		empresa.setSubMarcas(subMarcas);
		empresa.setActividades(actividad);
		Empresa empresa1 = new Empresa();
		empresa1.setGln("1888888");
		empresa1.setNombre("Tata");
		empresa1.setRut("1888888");
		empresa1.setProductos(productos);
		empresa1.setMarcas(marcas);
		empresa1.setSubMarcas(subMarcas);

		this.empresa.insert(empresa);
		this.empresa.insert(empresa1);
	}

}
