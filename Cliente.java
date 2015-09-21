import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente implements Runnable{
	String entrada;
	String porta;
	String endereco;
	String mensagem;
	int[][] copy_table;
	int vizinhos[];
	int custoVizinhos[];
	int noOrigem;
	
	public Cliente(int[][] rt, int noOrigem, int vizinhos[]){
		copy_table = rt;
		this.noOrigem = noOrigem;
		this.vizinhos = vizinhos; //com a lista de vizinhos é possivel
								  //saber a quem enviar a tabela do no origem
	}
	
	//envia tabela para 'node' a um custo 'cost'
	public void sendTable(int de, int para){
		if(de == 0){
			if(para == 1) endereco = "239.0.0.1";
			else if(para == 2) endereco = "239.0.0.3";
			else if(para == 3) endereco = "239.0.0.2";
		}else if(de == 1){
			if(para == 0) endereco = "239.0.0.1";
			else if(para == 2) endereco = "239.0.0.4";
			else endereco = "invalido";
		}else if(de == 2){
			if(para == 1) endereco = "239.0.0.4";
			else if(para == 0) endereco = "239.0.0.3";
			else endereco = "239.0.0.5";
		}else{
			if(para == 2) endereco = "239.0.0.5";
			else if(para == 0) endereco = "239.0.0.2";
			else endereco = "invalido";
		}
		
		if(para == 0) porta = "2000";
		else if(para == 1) porta = "2001";
		else if(para == 2) porta = "2002";
		else porta = "2003";
		
		//parsing: Inteiro para String 
		mensagem = "";
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				mensagem += copy_table[i][j] + " ";
			}
		}
		
		//enviando mensagem na forma de String
		if(!endereco.equals("invalido")){
			try {
				byte[] b = mensagem.getBytes();
				InetAddress addr = InetAddress.getByName(endereco);
				DatagramSocket ds = new DatagramSocket();
				DatagramPacket pkg = new DatagramPacket(b, b.length, addr,
						Integer.parseInt(porta));
				ds.send(pkg);
				//System.out.println("Mensagem enviada para "+ praquem + "!");
				
			} catch (Exception e) {
				//System.out.println("Nao foi possivel enviar a mensagem");
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			//sempre enviando tabelas para os vizinhos
			
			try {
				for(int i = 0; i < vizinhos.length; i++){
					if(vizinhos[i] != noOrigem){
						sendTable(noOrigem, vizinhos[i]);
					}
				}
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
