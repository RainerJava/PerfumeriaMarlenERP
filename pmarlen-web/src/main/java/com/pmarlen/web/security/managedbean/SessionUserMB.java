package com.pmarlen.web.security.managedbean;

import java.util.*;
import java.text.*;
import java.io.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.controller.UsuarioJpaController;
import com.pmarlen.model.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUserMB {

    private final Logger logger = LoggerFactory.getLogger(SessionUserMB.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_hh:mm:ss");
    private UsuarioJpaController usuarioJpaController;
    private Usuario usuarioAuthenticated;

    public void setUsuarioJpaController(UsuarioJpaController usuarioJpaController) {
        this.usuarioJpaController = usuarioJpaController;
    }

    public Usuario getUsuarioAuthenticated() {
        if (usuarioAuthenticated == null) {
            String strUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            logger.debug("FacesContext.getCurrentInstance().getExternalContext().getRemoteUser() = ->" + strUser + "<-");
            usuarioAuthenticated = this.usuarioJpaController.findUsuario(strUser);
        }
        return usuarioAuthenticated;
    }

    public String getTestMessage() {
        HttpServletRequest r = null;

        r = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        r.getSession().getMaxInactiveInterval();

        return "TestMessage@" + sdf.format(new Date()) + 
				", MaxInactiveInterval=" + r.getSession().getMaxInactiveInterval() + " secs."+
				", LastAccessedTime=" + (System.currentTimeMillis() - r.getSession().getLastAccessedTime()) / 1000 + " secs. ago. "+
				", UserPrincipal="+r.getUserPrincipal();
    }

    public String getSessionTimeOutMinus3SecsToDisplay() {
        HttpServletRequest r = null;

        r = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        r.getSession().getMaxInactiveInterval();

        return String.valueOf(r.getSession().getMaxInactiveInterval() - 3);
    }

    public String logout() throws IOException {
        logger.debug("============>>> logout !!");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        logger.debug("request [" + request + "]");
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        HttpSession session = request.getSession();
        logger.debug("session [" + session + "]");
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(externalContext.getRequestContextPath() + "/pages/home.jsf");
        return null;
    }

    public boolean isRootUser() {
        Perfil perfilFinalUser = new Perfil();
        Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
        perfilFinalUser.setId(Constants.PERFIL_ROOT);
        return perfilCollection.contains(perfilFinalUser);
    }

    public boolean isITHDUser() {
        Perfil perfilFinalUser = new Perfil();
        Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
        perfilFinalUser.setId(Constants.PERFIL_ITHD_USER);
        return isRootUser() || perfilCollection.contains(perfilFinalUser);
    }

    public boolean isFinalUser() {
        Perfil perfilFinalUser = new Perfil();
        Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
        perfilFinalUser.setId(Constants.PERFIL_FINAL_USER);
        return isRootUser() || perfilCollection.contains(perfilFinalUser);
    }

    public boolean isAdministratorUser() {
        Perfil perfilFinalUser = new Perfil();
        Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
        perfilFinalUser.setId(Constants.PERFIL_ADMINISTRATOR);
        return isRootUser() || perfilCollection.contains(perfilFinalUser);
    }

    public boolean isAnalystUser() {
        Perfil perfilFinalUser = new Perfil();
        Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
        perfilFinalUser.setId(Constants.PERFIL_ANALYST);
        return isRootUser() || perfilCollection.contains(perfilFinalUser);
    }

    public boolean isRegisterUser() {
        Perfil perfilFinalUser = new Perfil();
        Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
        perfilFinalUser.setId(Constants.PERFIL_REGISTER);
        return isRootUser() || perfilCollection.contains(perfilFinalUser);
    }

    public boolean isGuestUser() {
        Perfil perfilFinalUser = new Perfil();
        Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
        perfilFinalUser.setId(Constants.PERFIL_GUEST);
        return isRootUser() || perfilCollection.contains(perfilFinalUser);
    }
}
