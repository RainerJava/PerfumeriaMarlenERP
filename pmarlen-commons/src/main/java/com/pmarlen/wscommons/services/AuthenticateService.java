package  com.pmarlen.wscommons.services;

import com.pmarlen.businesslogic.exception.AuthenticationException;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Usuario;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author alfred
 */

@WebService
public interface AuthenticateService {
    @WebMethod(operationName="authenticateUsuario")
    Usuario authenticateUsuario(
			@WebParam(name="usuarioId")String usuarioId,
			@WebParam(name="password")String password) throws AuthenticationException;

    @WebMethod(operationName="getFirstData")
    List<Marca> getFirstData();
}
