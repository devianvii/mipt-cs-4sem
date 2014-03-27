/**
 * Server.java
 * Created on Mar 24, 2014
 */
package ru.mipt.spring2014.class07.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements Runnable
{
	private final int port;

	public Server (int port)
	{
		this.port = port;
	}

	public void run ()
	{
		
		final ExecutorService execSrv = Executors.newFixedThreadPool (1);

		final ServerSocket socket;
		
		try{
			System.out.println ("Server: Start accepting connections");
			socket = new ServerSocket (port);

			try{
				while (!Thread.interrupted ())
				{
					execSrv.submit (new RequestHandler (socket.accept ()));
					System.out.println ("Server: Connection accepted");
				}
			} finally
			{
				System.out.println ("Server: Stop accepting requests");
				socket.close ();
			}
		} catch (IOException ex)
		{
			System.err.println ("Server: Exception in server thread: " + ex);
		}
	}
}
