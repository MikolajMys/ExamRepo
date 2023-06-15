public class Main {
    public static void main(String[] args) {
        Server server;
        server = new Server(3000);
        server.listen();
    }
}