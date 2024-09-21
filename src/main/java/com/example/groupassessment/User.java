public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    // Constructor
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
    GridPane signUpPane = new GridPane();
    signUpPane.setVgap(10);
    signUpPane.setHgap(10);

    Label usernameLabel = new Label("Username:");
    TextField usernameField = new TextField();

    Label emailLabel = new Label("Email:");
    TextField emailField = new TextField();

    Label passwordLabel = new Label("Password:");
    PasswordField passwordField = new PasswordField();

    Button signUpButton = new Button("Sign Up");

// Add elements to GridPane
signUpPane.add(usernameLabel, 0, 0);
signUpPane.add(usernameField, 1, 0);
signUpPane.add(emailLabel, 0, 1);
signUpPane.add(emailField, 1, 1);
signUpPane.add(passwordLabel, 0, 2);
signUpPane.add(passwordField, 1, 2);
signUpPane.add(signUpButton, 1, 3);
GridPane loginPane = new GridPane();
loginPane.setVgap(10);
loginPane.setHgap(10);

Label loginUsernameLabel = new Label("Username:");
TextField loginUsernameField = new TextField();

Label loginPasswordLabel = new Label("Password:");
PasswordField loginPasswordField = new PasswordField();

Button loginButton = new Button("Login");

// Add elements to GridPane
loginPane.add(loginUsernameLabel, 0, 0);
loginPane.add(loginUsernameField, 1, 0);
loginPane.add(loginPasswordLabel, 0, 1);
loginPane.add(loginPasswordField, 1, 1);
loginPane.add(loginButton, 1, 2);
public boolean signUp(String username, String email, String password) {
    if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
        System.out.println("Please fill in all fields.");
        return false;
    }

    // Hash the password (use a library like BCrypt)
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    // Save user in database
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, email);
        stmt.setString(3, hashedPassword);
        stmt.executeUpdate();
        System.out.println("Sign-up successful.");
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean login(String username, String password) {
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
        String sql = "SELECT password FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareState`ment(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String storedHash = rs.getString("password");
            if (BCrypt.checkpw(password, storedHash)) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Invalid credentials.");
                return false;
            }
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
signUpButton.setOnAction(e -> {
String username = usernameField.getText();
String email = emailField.getText();
String password = passwordField.getText();

    if (signUp(username, email, password)) {
        // Redirect to another window or display success message
        }
        });

        loginButton.setOnAction(e -> {
String username = loginUsernameField.getText();
String password = loginPasswordField.getText();

    if (login(username, password)) {
        // Redirect to another window or display success message
        }
        });
