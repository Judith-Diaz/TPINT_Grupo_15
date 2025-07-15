package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProvinciaDAO;
import DAOimpl.ProvinciaDAOImpl;
import entidad.Provincia;
import negocio.ProvinciaNegocio;
import negocioImpl.ProvinciaNegocioImpl;
import negocio.UsuarioNegocio;
import negocioImpl.UsuarioNegocioImpl;


@WebServlet("/CargarProvincias")
public class ProvinciasServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
	private ProvinciaNegocio provinciaNegocio = new ProvinciaNegocioImpl();
	
    public ProvinciasServelt() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        List<Provincia> listaProvincias = provinciaNegocio.listarProvincias();
	        request.setAttribute("provincias", listaProvincias);
	        request.getRequestDispatcher("/vistas/AltaCliente.jsp").forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}	
	    
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
