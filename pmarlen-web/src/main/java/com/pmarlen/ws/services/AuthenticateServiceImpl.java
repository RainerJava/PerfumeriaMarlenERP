package  com.pmarlen.ws.services;

import com.pmarlen.businesslogic.exception.AuthenticationException;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import javax.jws.WebService;

import com.pmarlen.wscommons.services.AuthenticateService;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.UsuarioJpaController;
import javax.persistence.NoResultException;

@WebService(endpointInterface = "com.pmarlen.wscommons.services.AuthenticateService")
@Repository("authenticateService ")
public class AuthenticateServiceImpl implements  AuthenticateService {

    private UsuarioJpaController usuarioJpaController;

    public Usuario authenticateUsuario(String usuarioId,String password) throws AuthenticationException {
        System.err.print("--->> call --->>> authenticateUsuario(String usaurioId="+usuarioId+",String password="+password+"); ");
        Usuario usuarioAuthenticated = null;
        try {
            usuarioAuthenticated =  usuarioJpaController.findUsuario(usuarioId,password);
        } catch(NoResultException nre) {
            throw new AuthenticationException("ERROR IN USER / PASSORD");
        }

        System.err.println(" usuarioAuthenticated ="+usuarioAuthenticated);

        return usuarioAuthenticated;
    }

    public List<Marca> getFirstData() {
        List<Marca> list = new ArrayList<Marca>();

        for(int i=0;i<10;i++) {
            Marca marca = new Marca();
            marca.setId(i);
            marca.setNombre("Marca X"+i);
            Linea linea = new Linea();
            linea.setId(i+100);
            linea.setNombre("Linea"+(i+100));
            marca.setLinea(linea);
            list.add(marca);
        }

        return list;
    }

    @Autowired
    public void setUsuarioJpaController( UsuarioJpaController usuarioJpaController){
        this.usuarioJpaController = usuarioJpaController;
    }


}
