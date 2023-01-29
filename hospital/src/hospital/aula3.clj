(ns hospital.aula3
	 (:use [clojure pprint])
	 (:require [hospital.logic :as h.logic]
						 [hospital.model :as h.model]))

; Qualquer arquivo que acessar esse namespace vai ter acesso a esse símbolo global
(def nome "Polyana")
(println nome)

; redefinir o simbolo (refiz o binding do símbolo)
(def nome 1232132)
(println nome)

(let [nome "Polyana"]
	 ; coisa 1
	 ; coisa 2
	 (println nome)
	 ; não estou refazendo o binding do  símbolo local
	 ; criando um novo símbolo local a este bloco e escondendo o anterior
	 ; Shadowing
	 (let [nome "Hyago"]
			; coisa 3
			; coisa 4
			(println nome))
	 (println nome))

(defn testa-atomao []
	 (let [hospital-poly (atom {:espera h.model/fila-vazia})] ; ATOM é uma casca é uma forma de isolar as retriabuiçoes de um simbolo, para que nao fique exposto de forma global
			(println hospital-poly)
			(pprint hospital-poly)
			(pprint (deref hospital-poly)) ; de-referenciar, extrair o valor de dentro, deref pega o que está dentro da Atom/casca
			(pprint @hospital-poly) ; @ é um atalho para o deref
			(pprint (assoc @hospital-poly :laboratorio1 h.model/fila-vazia)) ;  nao é assim que eu altero conteudo dentro de um atom, pq assim só altera o valor extraido do atomo, mas nao é o atomo em si
			(pprint hospital-poly)
			; essa é uma das maneiras de alterar conteudo dentro de um atom
			(swap! hospital-poly assoc :laboratorio1 h.model/fila-vazia) ; troca, funcao com ! sao funcao com efeitos colaterais, mesmo que nao use o valor retornado ela vai alterar o valor do atomo, gerando efeito colateral
			(pprint hospital-poly)

			; update tradicional imutavel, com deferencia, que nao trará efeito colateral
			(update @hospital-poly :laboratorio1 conj "111")
			(swap! hospital-poly update :laboratorio1 conj "111")
			(pprint hospital-poly)
			))

;(testa-atomao)


(defn chega-em-malvado! [hospital pessoa]
	 (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa) ; swap é como se fosse uma transasao, se nao conseguir retenta para nao ter mais o problema da concorrencia, alguem já alterou o valor, retenta para obter esse valor novo.. existe limites nos retries, quebrou a atomicidade retenta, nao precisa se preocupar com mais com lock
	 (println "após inserir" pessoa))

; problema em trabalhar com espaços de memória compartilhada, multiplas threads, alterando símbolos globais
(defn simula-um-dia-em-paralelo []
	 (println "#Simula um dia em paralelo")
	 (let [hospital (atom (h.model/novo-hospital))]
			(.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
			(.start (Thread. (fn [] (Thread/sleep 8000)
													(pprint hospital)))))
	 )

; forçando situaçoes de retries
;(simula-um-dia-em-paralelo)

; o ideal é utilizar swap! com funcoes puras para nao gerar tantos retries, apesar que consegue resolver os retries, mas se for um grande numero pode gerar diversos retries que podem causar muitas retentativas e demorar um pouco, um loop quase infinito
(defn chega-em-malvado! [hospital pessoa]
	 (swap! hospital h.logic/chega-em :espera pessoa) ; swap é como se fosse uma transasao, se nao conseguir retenta para nao ter mais o problema da concorrencia, alguem já alterou o valor, retenta para obter esse valor novo.. existe limites nos retries, quebrou a atomicidade retenta, nao precisa se preocupar com mais com lock
	 (println "após inserir" pessoa))

(defn simula-um-dia-em-paralelo []
	 (println "#Simula um dia em paralelo")
	 (let [hospital (atom (h.model/novo-hospital))]
			(.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
			(.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
			(.start (Thread. (fn [] (Thread/sleep 8000)
													(pprint hospital)))))
	 )

; SEM forçar situaçoes de retries
(simula-um-dia-em-paralelo)