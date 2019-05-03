package cl.bhp.middleware.dao;

import cl.bhp.middleware.exception.ServiceException;
import cl.bhp.middleware.util.PropertiesUtil;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Clase que permite recuperar información expuesta por la API EspoCRM
 * @author Luis Oliveros
 */

public class DataAccessObject {
	static PropertiesUtil prop = new PropertiesUtil();
	
	private static final Logger LOGGER = Logger.getLogger(DataAccessObject.class);
	
	/**
	 * Recupera Información de los reclamos realizados por un empleado
	 * que son buscados por el rut
	 * @param rut
	 * @return JSONArray reclamosEmpleado
	 * @throws ServiceException
	 */
	
	public JSONArray listarReclamos (String rut) throws ServiceException {
		long init = System.currentTimeMillis();
		JSONObject Reclamos = new JSONObject();
		JSONArray reclamosEmpleado = new JSONArray();
		
		String URI = prop.getLocalProperties().getProperty("api.espocrm.uri");
		String auth = prop.getLocalProperties().getProperty("api.espocrm.auth");
		try {

					
			HttpResponse<String> responseUser = Unirest.get(URI+rut)
					  .header("Authorization", "Basic "+auth)
					  .header("cache-control", "no-cache")
					  .asString();
			
			Reclamos = new JSONObject(responseUser.getBody());
		
			LOGGER.info("API EspoCRM response Status: "+responseUser.getStatus()+" "+responseUser.getStatusText()
					+" con el rut: "+rut);
			LOGGER.info("API EspoCRM Body Response: "+Reclamos.toString());
			
			reclamosEmpleado = Reclamos.getJSONArray("list"); 
			
			} catch (UnirestException e) {
				e.printStackTrace();
				throw new ServiceException("456");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("456");
			}
		
	
		LOGGER.info("Tiempo en consulta EspoCRM "+(System.currentTimeMillis() - init)+" ms.");

		return reclamosEmpleado;
		
	}

}
