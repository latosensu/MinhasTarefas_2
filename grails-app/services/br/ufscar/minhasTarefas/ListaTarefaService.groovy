package br.ufscar.minhasTarefas

import grails.converters.JSON
import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
class ListaTarefaService {

    def inserir(ListaTarefa novaLista) {
        Integer numeroMaximoListas = 3
        String tipoUsuario = "Normal"
        if (novaLista.usuario == "Grails") {
            tipoUsuario = "Premium"
        }

        if (tipoUsuario == "Premium") {
            numeroMaximoListas = 5
        }

        Integer numeroListasUsuario = ListaTarefa.countByUsuario(novaLista.usuario)

        if (numeroListasUsuario >= numeroMaximoListas) {
            throw new Exception("Usuário atingiu o número limite de listas: ${numeroMaximoListas}")
        }

        if (!novaLista.save()) {
            throw new ValidationException("", novaLista.errors)
        }
        novaLista
    }
}
