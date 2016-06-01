package br.ufscar.minhasTarefas

import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
class ListaTarefaService {

    def inserir(ListaTarefa novaLista) {

        String tipoUsuario = "Normal"
        if (novaLista.usuario == "Grails") {
            tipoUsuario = "Premium"
        }

        Integer numeroMaximoListas = obterNumeroMaximoListas(tipoUsuario)
        Integer numeroListasUsuario = ListaTarefa.countByUsuario(novaLista.usuario)

        if (numeroListasUsuario >= numeroMaximoListas) {
            throw new Exception("Usuário atingiu o número limite de listas: ${numeroMaximoListas}")
        }

        if (!novaLista.save()) {
            throw new ValidationException("", novaLista.errors)
        }
        novaLista
    }

    private obterNumeroMaximoListas(String tipoUsuario){
        if (tipoUsuario == "Premium") {
            return 5
        }
        return 3
    }
}
