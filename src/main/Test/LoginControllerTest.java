import com.example.groupassessment.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController loginController;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        // Mock the database connection and statements
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Inject the mock connection into LoginController
        loginController = new LoginController(mockConnection);
    }

    @Test
    public void testVerifyCredentials_Success() throws SQLException {
        // Set up mock behavior for successful credential verification
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);  // Simulate that a user was found
        when(mockResultSet.getString("password")).thenReturn("correct_password");

        // Test verifyCredentials with correct credentials
        boolean result = loginController.verifyCredentials("testUser", "correct_password");

        // Assert that verifyCredentials returns true for matching credentials
        assertTrue(result, "verifyCredentials should return true for correct credentials.");
    }

    @Test
    public void testVerifyCredentials_Failure() throws SQLException {
        // Set up mock behavior for failed credential verification
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);  // Simulate user not found

        // Test verifyCredentials with incorrect credentials
        boolean result = loginController.verifyCredentials("testUser", "wrong_password");

        // Assert that verifyCredentials returns false for incorrect credentials
        assertFalse(result, "verifyCredentials should return false for incorrect credentials.");
    }

    @Test
    public void testGetUserId_Success() throws SQLException {
        // Set up mock behavior to return a specific user ID
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);  // Simulate that a user was found
        when(mockResultSet.getInt("id")).thenReturn(123);

        // Test getUserId
        int userId = loginController.getUserId("testUser");

        // Assert that getUserId returns the correct ID
        assertEquals(123, userId, "getUserId should return the correct user ID.");
    }

    @Test
    public void testGetUserId_NotFound() throws SQLException {
        // Set up mock behavior to simulate user not found
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);  // Simulate user not found

        // Test getUserId for a non-existing user
        int userId = loginController.getUserId("unknownUser");

        // Assert that getUserId returns -1 if the user is not found
        assertEquals(-1, userId, "getUserId should return -1 if the user is not found.");
    }
}