package com.ejemplo.reportejasper.controller;

import com.ejemplo.reportejasper.model.Cliente;
import com.ejemplo.reportejasper.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClienteVistaController {

    private final ClienteService clienteService;

    public ClienteVistaController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "clientes"; 
    }
}
