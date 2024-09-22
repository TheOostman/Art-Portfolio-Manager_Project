import com.example.groupassessment.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegisterUserTest {

    private LoginController loginController;
    private Connection mockConnection;
    private PreparedStatement mockStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        // Initialize the controller
        loginController = new LoginController();

        // Mocking the database connection and prepared statement
        mockConnection = Mockito.mock(Connection.class);
        mockStatement = Mockito.mock(PreparedStatement.class);

        // Simulating the preparation of SQL statement
        when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
    }

    @Test
    public void testRegisterNewUser() throws SQLException {
        // Arrange
        String newUsername = "newUser";
        String newPassword = "newPass";

        // Simulate registration logic (mocking database insert)
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);

        // Act: Call the register method (or similar method that you need to implement)
        loginController.registerUser(newUsername, newPassword, mockConnection);

        // Assert: Verify that the prepared statement was called with the correct parameters
        verify(mockStatement).setString(1, newUsername);
        verify(mockStatement).setString(2, newPassword);
        verify(mockStatement).executeUpdate();
    }
}
