package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ClienteDAO;
import DAO.LocalidadDAO;
import DAOimpl.LocalidadDAOImpl;
import negocio.LocalidadNegocio;
import negocioImpl.LocalidadNegocioImpl;
import DAOimpl.ClienteDAOimpl;
import entidad.Localidad;
import negocio.ClienteNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/CargarLocalidades")
public class LocalidadesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// INTANCIAMOS LAS CAPAS
	private LocalidadNegocio localidadNegocio = new LocalidadNegocioImpl();

	public LocalidadesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String provinciaId = request.getParameter("provinciaId");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (provinciaId == null || provinciaId.trim().isEmpty()) {
			response.getWriter().write("[]");
			return;
		}

		try {
			List<Localidad> localidades = localidadNegocio.listarPorProvincia(provinciaId.trim());
			StringBuilder json = new StringBuilder();
			json.append("[");

			for (int i = 0; i < localidades.size(); i++) {
				Localidad loc = localidades.get(i);
				json.append("{");
				json.append("\"id\":\"").append(loc.getIdLocalidad_Loc()).append("\",");
				json.append("\"descripcion\":\"").append(loc.getDescripcion_Loc()).append("\"");
				json.append("}");
				if (i < localidades.size() - 1) {
					json.append(",");
				}
			}
			json.append("]");

			String jsonResult = json.toString();
			response.getWriter().write(jsonResult);

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("[]");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
