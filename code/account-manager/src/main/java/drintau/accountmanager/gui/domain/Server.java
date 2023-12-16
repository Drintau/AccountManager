package drintau.accountmanager.gui.domain;

import lombok.Data;

@Data
public class Server {

    private Integer port;

    private Servlet servlet;

}
