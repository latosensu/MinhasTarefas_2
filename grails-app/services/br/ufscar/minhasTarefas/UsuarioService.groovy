package br.ufscar.minhasTarefas

import grails.transaction.Transactional

@Transactional
class UsuarioService {

    String obterTipoUsuario(String usuario) {
        String tipoUsuario = "Normal"
        if (usuario == "Grails") {
            tipoUsuario = "Premium"
        }
        tipoUsuario
    }
}
