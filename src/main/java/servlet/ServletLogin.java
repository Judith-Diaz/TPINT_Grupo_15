package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UsuarioDAO;
import DAOimpl.UsuarioDAOImpl;
import entidad.Cuenta;
import entidad.Usuario;

import negocio.CuentaNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocio.UsuarioNegocio;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private UsuarioDAO usuarioDAO; // Declaración correcta

	private UsuarioNegocio usuarioNeg= new UsuarioNegocioImpl();
//	public ServletLogin() {
//		super();
//		this.usuarioDAO = new UsuarioDAOImpl();
//	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);
		

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String Usuario = request.getParameter("usuario");
		String Contrasenia = request.getParameter("contrasenia");
		// Validar usuario
		Usuario usuarioValidado = usuarioNeg.validarUsuarioLogin(Usuario, Contrasenia);
		if (usuarioValidado == null) {
			request.setAttribute("mensaje", "Usuario o contraseña no existen"); // ESTO ESTÁ MAL en un DAO
		}
		if (usuarioValidado != null) {
			// Login exitoso
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuarioValidado);
			session.setAttribute("nombreUsuario", usuarioValidado.getNombreUsu());
			session.setAttribute("dniUsuario", usuarioValidado.getDniUsu());//para usar despues vuando necesite consultar por el dni del que tiene iniciada la session
			if (usuarioValidado.getRolUsu() == 'A') {
				response.sendRedirect(request.getContextPath() + "/vistas/HomeAdministrador.jsp");
			} else if (usuarioValidado.getRolUsu() == 'C') {
				 try {
				CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
	            List<Cuenta> cuentas = cuentaNegocio.CargarDDlCuentas(usuarioValidado.getDniUsu());
	            session.setAttribute("listaCuentas", cuentas);
	            request.getRequestDispatcher("/vistas/HomeCliente.jsp").forward(request, response);
	            
				 } catch (Exception e) {
				        e.printStackTrace(); // opcional: para ver el error en consola
				        // Redirigir al login con mensaje de error
				        request.setAttribute("error", "Error al cargar las cuentas del cliente.");
				        request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);
				    }
				//response.sendRedirect(request.getContextPath() + "/vistas/HomeCliente.jsp");
			} else {
				request.setAttribute("mensaje", "Usuario o contraseña no existen"); // ESTO ESTÁ MAL en un DAO
				request.setAttribute("error", "Rol de usuario no válido.");
				request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);
			}
		} else {
			// Login fallido
			request.setAttribute("mensaje", "Usuario o contraseña no existen"); // ESTO ESTÁ MAL en un DAO
			request.setAttribute("error", "Usuario o contraseña incorrectos.");
			request.setAttribute("usuario", Usuario);
			request.getRequestDispatcher("/vistas/Login.jsp").forward(request, response);
		}
	}
}
