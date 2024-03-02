package org.example;


import org.example.task1.DatabaseInitService;
import org.example.task1.DatabasePopulateService;
import org.example.task2.Client;
import org.example.task2.ClientService;
import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        DatabaseInitService.initDatabase();
    DatabasePopulateService. populateDatabase();

        ClientService clientService = new ClientService();

        try {
            long clientId = clientService.create("Andrey Tan");
            System.out.println("New client ID: " + clientId);


            String clientName = clientService.getById(clientId);
            System.out.println("Client name by ID " + clientId + ": " + clientName);


            clientService.setName(clientId, "Michael Dudikov");


            clientService.deleteById(clientId);


            List<Client> allClients = clientService.listAll();
            System.out.println("All clients: " + allClients);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
