(ns hospital.aula3
	 (:use [clojure.pprint])
	 (:require [hospital.logic :as h.logic]))

(defn carrega-paciente
	 [id]
	 (println "Carregando" id)
	 (Thread/sleep 1000)
	 {:id id :carregado-em (h.logic/agora)})

;(pprint (carrega-paciente 30))
;(pprint (carrega-paciente 12))

; é uma funcao pura, funcoes puras nao causam efeitos colaterais
(defn carrega-se-nao-existe
	 [cache id carregadora]
	 (if (contains? cache id )
			cache
			(let [paciente (carregadora id)]
				 (assoc cache id paciente))))

;(pprint (carrega-se-nao-existe {} 15 carrega-paciente))
;(pprint (carrega-se-nao-existe {15 {:id 15 }} 15 carrega-paciente))

; componentizando / ou seja encapsulando o comportamento do cache em um objeto
; quando quer implementar componentes com caracteristicas de orientaçao a objestos
(defprotocol Carregavel
	 (carrega! [this id]))

(defrecord Cache
	 [cache carregadora]
	 Carregavel
	 (carrega! [this id]
			(swap! cache carrega-se-nao-existe id carregadora)
			(get @cache id)))

(def pacientes (->Cache (atom {}) carrega-paciente))

(pprint pacientes)

(carrega! pacientes 20)
(carrega! pacientes 21)
(carrega! pacientes 20)
(pprint pacientes)

;Encapsulamento pode ser feito com o uso adequado de namespaces e funções definidas com def-.
;(println "teste dentro de outro namespace, nao dá pois nao é publico" h.logic/teste)

;Ciclo de vida é combinar estado com funções.
; Implementamos estados com atoms e refs.

; É possível implementar ciclo de vida, estado e encapsulamento em Clojure sem utilizar inter operabilidade com Java. Portanto é uma escolha ir por componentes OO ou funcionais.