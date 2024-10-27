import com.example.groupassessment.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        loginController = new LoginController();
    }

    @Test
    public void testVerifyCredentials_Success() throws SQLException {
        // Mocking the database connection, prepared statement, and result set
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Simulating database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Simulate finding a user
        when(mockResultSet.getString("password")).thenReturn("correct_password");

        // Inject mock connection into loginController
        boolean result = loginController.verifyCredentials("testUser", "correct_password");

        // Verify expected behavior
        assertTrue(result, "verifyCredentials should return true for correct credentials.");
    }

    @Test
    public void testVerifyCredentials_Failure() throws SQLException {
        // Mocking the database connection, prepared statement, and result set
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Simulating database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Simulate not finding a user

        // Inject mock connection into loginController
        boolean result = loginController.verifyCredentials("testUser", "wrong_password");

        // Verify expected behavior
        assertFalse(result, "verifyCredentials should return false for incorrect credentials.");
    }

    @Test
    public void testGetUserId_Success() throws SQLException {
        // Mocking the database connection, prepared statement, and result set
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Simulating database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Simulate finding a user
        when(mockResultSet.getInt("id")).thenReturn(123);

        int userId = loginController.getUserId("testUser");

        assertEquals(123, userId, "getUserId should return the correct user ID.");
    }

    @Test
    public void testGetUserId_NotFound() throws SQLException {
        // Mocking the database connection, prepared statement, and result set
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Simulating database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Simulate user not found

        int userId = loginController.getUserId("unknownUser");

        assertEquals(-1, userId, "getUserId should return -1 if the user is not found.");
    }
}
