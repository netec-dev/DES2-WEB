package wse;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/variosclientes")
public class VariosClientesWSEndpoint {

  private static Set<Session> clientes = Collections.synchronizedSet(new HashSet<Session>());

  @OnMessage
  public void onMessage(String _msj, Session _sesion) throws IOException {

    synchronized (clientes) {
      // Itera sobre los usuarios conectados y les envía un mensaje
      for (Session cliente : clientes) {
        if (!cliente.equals(_sesion)) {
          cliente.getBasicRemote().sendText(_msj);
        }
      }
    }

  }

  @OnOpen
  public void onOpen(Session session) {
    // Adiciona la sesión del cliente q se conecta
    clientes.add(session);
  }

  @OnClose
  public void onClose(Session session) {
    // Elimina la sesión del cliente q se desconecta
    clientes.remove(session);
  }
}