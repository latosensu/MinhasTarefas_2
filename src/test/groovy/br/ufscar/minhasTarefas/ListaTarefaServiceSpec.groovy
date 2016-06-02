package br.ufscar.minhasTarefas

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ListaTarefaService)
@Mock(ListaTarefa)
class ListaTarefaServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Listar listas de tarefas quando não existe nenhuma cadastrada no banco"() {
        expect: "Lista vazia"
        service.listar().empty
    }

    void "Listar listas de tarefas quando existem listas cadastradas no banco"() {
        given: "Duas listas cadastradas"
        def listaTarefa1 = new ListaTarefa().save(flush: true, validate: false)
        def listaTarefa2 = new ListaTarefa().save(flush: true, validate: false)

        when: "Lista as listas de tarefa"
        def listas = service.listar()

        then: "A lista tem tamanho 2"
        listas.size() == 2

        and: "A lista contém as duas listas cadastradas"
        listas.containsAll(listaTarefa1, listaTarefa2)
    }

    @Unroll("O filtro retorna #tamanhoEsperado listas para o usuario #usuario")
    void "Filtrar listas de tarefas pelo usuario"() {
        setup: "Existem duas listas para o usuario Spock"
        def listaTarefa1 = new ListaTarefa(usuario: "Spock").save(flush: true, validate: false)
        def listaTarefa2 = new ListaTarefa(usuario: "Spock").save(flush: true, validate: false)

        and: "Uma lista para o usuario Klingon"
        def listaTarefa3 = new ListaTarefa(usuario: "Klingon").save(flush: true, validate: false)

        when: "Filtra as listas de tarefas de um usuário"
        def listas = service.filtrar(null, usuario)

        then: "A lista tem o tamanho esperado"
        listas.size() == tamanhoEsperado

        where:
        usuario   || tamanhoEsperado
        "Spock"   || 2
        "Klingon" || 1
        "Kirk"    || 0
    }

    void "Inserir primeira lista para usuário normal"() {
        given: "Dada uma nova lista"
        ListaTarefa novaLista = new ListaTarefa(nome: "Primeira Lista", usuario: "Klingon").save(flush: true, failOnError: true)

        and: "Mock do usuario service"
        def mockUsuarioService = GroovyMock(UsuarioService)
        mockUsuarioService.obterTipoUsuario(_) >> "Normal"
        service.usuarioService = mockUsuarioService

        when: "Inserir a primeira lista"
        service.inserir(novaLista)

        then: "O usuário tem uma lista cadastrada"
        ListaTarefa.findByUsuario("Klingon") == novaLista
    }

    void "Inserir quarta lista para usuário normal"() {
        given: "3 listas de um usuario normal"
        3.times {
            new ListaTarefa(usuario: "Klingon").save(flush: true, validate: false)
        }

        "Dada uma nova lista"
        ListaTarefa novaLista = new ListaTarefa(nome: "Quart Lista", usuario: "Klingon").save(flush: true, failOnError: true)

        and: "Mock do usuario service"
        def mockUsuarioService = GroovyMock(UsuarioService)
        mockUsuarioService.obterTipoUsuario(_) >> "Normal"
        service.usuarioService = mockUsuarioService

        when: "Inserir a quarta lista"
        service.inserir(novaLista)

        then: "Uma exceção é lançada"
        thrown Exception
    }

}
