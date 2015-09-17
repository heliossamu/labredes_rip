import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente implements Runnable{
	Scanner scanner;
	String entrada;
	String porta;
	String endereco;
	String mensagem;
	
	public Cliente(){
		scanner = new Scanner(System.in);
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//<endereco multicast> <porta> <mensagem>
			entrada = scanner.nextLine();
			if(entrada != ""){
				String[] resultado = entrada.split("\\s");
				if(resultado.length != 3){
					System.out.println("Erro: <gruopo(a ou b)> <porta> <mensagem>");
				}else{
					endereco = resultado[0];
					
					if(endereco.equals("a")){
						endereco = "239.0.0.1";
					}else if(endereco.equals("b")){
						endereco = "239.0.0.2";
					}
					porta = resultado[1];
					mensagem = resultado[2];
					
					try {
						byte[] b = mensagem.getBytes();
						InetAddress addr = InetAddress.getByName(endereco);
						DatagramSocket ds = new DatagramSocket();
						DatagramPacket pkg = new DatagramPacket(b, b.length, addr,
								Integer.parseInt(porta));
						ds.send(pkg);
						System.out.println("Mensagem enviada!");
					} catch (Exception e) {
						System.out.println("Nao foi possivel enviar a mensagem");
					}
					
				}
				
			}
		}
	}
}
