package org.example.task2;

import org.example.task1.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public long create(String name) throws SQLException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "INSERT INTO clients (name) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
        }
    }

    public String getById(long id) throws SQLException {
        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "SELECT name FROM clients WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    throw new SQLException("Client with id " + id + " not found.");
                }
            }
        }
    }

    public void setName(long id, String name) throws SQLException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "UPDATE clients SET name = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setLong(2, id);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new SQLException("Client with id " + id + " not found.");
                }
            }
        }
    }

    public void deleteById(long id) throws SQLException {
        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "DELETE FROM clients WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 0) {
                    throw new SQLException("Client with id " + id + " not found.");
                }
            }
        }
    }

    public List<Client> listAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "SELECT id, name FROM clients";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    clients.add(new Client(id, name));
                }
            }
        }
        return clients;
    }
}
