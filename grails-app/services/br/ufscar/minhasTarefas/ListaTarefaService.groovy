package br.ufscar.minhasTarefas

import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
class ListaTarefaService {

    def usuarioService

    def inserir(ListaTarefa novaLista) {
        String usuario = novaLista.usuario
        String tipoUsuario = usuarioService.obterTipoUsuario(usuario)
        Integer numeroMaximoListas = obterNumeroMaximoListas(tipoUsuario)
        Integer numeroListasUsuario = contarListasUsuario(usuario)

        if (numeroListasUsuario >= numeroMaximoListas) {
            throw new Exception("Usuário atingiu o número limite de listas: ${numeroMaximoListas}")
        }

        if (!novaLista.save()) {
            throw new ValidationException("", novaLista.errors)
        }
        novaLista
    }

    private Integer contarListasUsuario(String usuario) {
        ListaTarefa.countByUsuario(usuario)
    }

    private obterNumeroMaximoListas(String tipoUsuario) {
        if (tipoUsuario == "Premium") {
            return 5
        }
        return 3
    }

    def listar() {
        ListaTarefa.all
    }
}
