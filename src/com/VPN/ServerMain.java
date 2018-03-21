package com.VPN;

import com.VPN.Server.Server;

public class ServerMain implements GUIInterface {
	
	private final int port = 8080;
	private final String sharedKey = "1234";
	private CryptoInterface m_crypto;
	
	public ServerMain() {
		m_crypto = new Server(this);
	}
	
	public void start() {
		if(m_crypto.startServer(port, sharedKey))
		{
			printf("Started Server.\n Waiting for connections...");
			Thread t = new Thread(new Runnable(){
				public void run() {
					m_crypto.acceptConnection();
				}
			});
			t.start();
		}
		else
		{
			printf("Server unable to start.");
		}
	}
	
	@Override
	public void printf(String s) {
		System.out.println(s);
	}
	
	@Override
	public void connectionReady() {
		printf("Connection established.");
		Thread t = new Thread(new Runnable(){
			public void run(){
				m_crypto.receiveMessages();
			}
		});
		t.start();
		//m_crypto.receiveMessages();
	}
	
	@Override
	public void connectionClosed() {
		printf("Conection closed");
	}

	public static void main(String[] args) {
		new ServerMain().start();
	}
}
