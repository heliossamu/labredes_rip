

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Servidor implements Runnable{
	private int port;
	private String address;

	//construtor base, recebe a porta e o endereco do servidor
	public Servidor(int port, String address){
		this.port = port;
		this.address = address;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				MulticastSocket mcs = new MulticastSocket(port);
				InetAddress grp = InetAddress.getByName(address);
				mcs.joinGroup(grp);
				byte rec[] = new byte[256];
				DatagramPacket pkg = new DatagramPacket(rec, rec.length);
				mcs.receive(pkg);
				String data = new String(pkg.getData());
				System.out.println("Dados recebidos:" + data);
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
	}
}
