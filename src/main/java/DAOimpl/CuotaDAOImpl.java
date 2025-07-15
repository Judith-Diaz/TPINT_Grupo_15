package DAOimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import DAO.Conexion;
import DAO.CuotaDAO;

public class CuotaDAOImpl implements CuotaDAO {

    @Override
    public boolean marcarCuotaPagada(int idPrestamo, int nroCuota) throws Exception {
        String query = "UPDATE Cuotas SET Estado_Cuo = 'P' WHERE IDPrestamo_Cuo = ? AND NroCuota_Cuo = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idPrestamo);
            stmt.setInt(2, nroCuota);
            
            return stmt.executeUpdate() == 1;
        }
    }
}