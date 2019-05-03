package cl.bhp.middleware.bean;

import cl.bhp.middleware.dao.DataAccessObject;
import cl.bhp.middleware.exception.ServiceException;

import org.apache.camel.Exchange;
import org.json.JSONArray;

/**
 * Clase que permite la realización de forward de peticiones generadas desde
 * una ruta camel, busca realizar validaciones previas antes de ir por el 
 * negocio en búsqueda de resultados
 * @author Luis Oliveros
 */

public class bean {
	
	/**
	 * Obtiene un listado de Reclamos de un empleado. mediante la busqueda con el parametro: rut,
	 * la cual sera devuelta en un JSON Array como resultado final
	 * @param ex
	 * @return JSONArray
	 * @throws ServiceException
	 */

	public JSONArray reclamo(Exchange ex)  throws ServiceException {
		String rut = null;
		rut = (String) ex.getIn().getHeader("rut");

		if(rut == null) {
			throw new ServiceException("400");
		}	
		
		return new DataAccessObject().listarReclamos(rut);
	}
	
}
