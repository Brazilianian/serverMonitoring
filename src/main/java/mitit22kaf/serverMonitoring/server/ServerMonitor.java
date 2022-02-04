package mitit22kaf.serverMonitoring.server;

import com.google.gson.Gson;
import mitit22kaf.serverMonitoring.entities.ComputerData;
import mitit22kaf.serverMonitoring.entities.ComputerVariableData;
import mitit22kaf.serverMonitoring.repos.ComputerDataRepo;
import mitit22kaf.serverMonitoring.repos.ComputerVariableDataRepo;
import mitit22kaf.serverMonitoring.service.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

@Component
@Scope
public class ServerMonitor implements CommandLineRunner {


    private final ComputerDataRepo computerDataRepo;
    private final ComputerVariableDataRepo computerVariableDataRepo;

    @Autowired
    public ServerMonitor(ComputerDataRepo computerDataRepo, ComputerVariableDataRepo computerVariableDataRepo,
                         ComputerService computerService) {
        this.computerVariableDataRepo = computerVariableDataRepo;
        this.computerDataRepo = computerDataRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        startServer();
    }

    public void startServer() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(8888);
            server.setReuseAddress(true);

            System.out.println("Server is ready");

            while (true) {
                Socket client = server.accept();

                System.out.println("New client connected" + client.getInetAddress().getHostAddress());

                ClientHandler clientSock = new ClientHandler(client);

                new Thread(clientSock).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;

        private String ip;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {

            Gson gson = new Gson();

            BufferedReader in = null;

            ComputerData data = null;

            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line;

                data = gson.fromJson(in.readLine(), ComputerData.class);

                ip = data.getIpv4();

                data.setLoaded(true);

                ComputerData computerData = computerDataRepo.findByIpv4(data.getIpv4());

                if (computerData != null) {
                    data.setNumberClassroom(computerData.getNumberClassroom());
                    data.setNumberPс(computerData.getNumberPс());
                }
                computerDataRepo.save(data);
                System.out.println(data + " SAVED");

                try {
                    while ((line = in.readLine()) != null) {

                        ComputerVariableData computerVariableData = gson.fromJson(in.readLine(),
                                ComputerVariableData.class);

                        computerVariableDataRepo.save(computerVariableData);
                        System.out.println(computerVariableData + " SAVED");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (in != null & data != null) {
                        Optional<ComputerData> cd = computerDataRepo.findById(ip);
                        cd.ifPresent(computerData -> {
                            computerData.setLoaded(false);
                            computerDataRepo.save(computerData);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
