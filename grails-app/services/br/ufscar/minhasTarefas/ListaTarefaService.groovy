package br.ufscar.minhasTarefas

import grails.converters.JSON
import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
class ListaTarefaService {

    def inserir(String nome, String usuario) {
        Integer numeroMaximoListas = 3
        String tipoUsuario = "Normal"
        if (usuario == "Grails") {
            tipoUsuario = "Premium"
        }

        if (tipoUsuario == "Premium") {
            numeroMaximoListas = 5
        }

        Integer numeroListasUsuario = ListaTarefa.countByUsuario(usuario)

        if (numeroListasUsuario >= numeroMaximoListas) {
            throw new Exception("Usuário atingiu o número limite de listas: ${numeroMaximoListas}")
        }

        ListaTarefa novaLista = new ListaTarefa(nome: nome, usuario: usuario)

        if (!novaLista.save()) {
            throw new ValidationException("", novaLista.errors)
        }
        novaLista
    }
}
