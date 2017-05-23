package edu.eci.pdsw.epicwino.logica.managedbeans.security;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;

@ManagedBean(name = "loginBean")
@ViewScoped
public class ShiroLoginBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ShiroLoginBean.class);
    private static final long serialVersionUID = 0x77d63d310L;

    private String username;
    private String password;
    private Boolean rememberMe;

    public ShiroLoginBean() {

    }

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * Try and authenticate the user
     */
    public void doLogin() {
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());

        try {
            subject.login(token);

            if (subject.hasRole("admin")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("restricted/RegistrarMateria.xhtml");
            } else if (subject.hasRole("coord")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("coordination/ReporteProgramacion.xhtml");
            } else if (subject.hasRole("assist")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("assistance/ProgramarMateria.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } catch (UnknownAccountException ex) {
            facesError("Unknown account");
            LOGGER.error(ex.getMessage(), ex);
        } catch (IncorrectCredentialsException ex) {
            facesError("Wrong password");
            LOGGER.error(ex.getMessage(), ex);
        } catch (LockedAccountException ex) {
            facesError("Locked account");
            LOGGER.error(ex.getMessage(), ex);
        } catch (AuthenticationException | IOException ex) {
            facesError("Unknown error: " + ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            token.clear();
        }
    }

    /**
     * Adds a new SEVERITY_ERROR FacesMessage for the ui
     *
     * @param message Error Message
     */
    private void facesError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean lembrar) {
        this.rememberMe = lembrar;
    }
}
