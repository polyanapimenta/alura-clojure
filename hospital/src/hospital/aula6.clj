(ns hospital.aula6
	 (:use [clojure.pprint])
	 (:require [hospital.model :as h.model]))

(defn cabe-na-fila?
	 [fila]
	 (-> fila
			 count
			 (< 5)))

(defn chega-em
	 [fila pessoa]
	 (if (cabe-na-fila? fila)
			(conj fila pessoa)
			(throw
				 (ex-info "Fila já está cheia"
									{:tentando-adicionar pessoa}))))

(defn chega-em!
	 "Troca de referencia via ref-set"
	 [hospital pessoa]
	 (let [fila (get hospital :espera)]
			(ref-set fila (chega-em @fila pessoa))))

(defn chega-em!
	 "Troca de referencia via alter"
	 [hospital pessoa]
	 (let [fila (get hospital :espera)]
			(alter fila chega-em pessoa)))


(defn simula-um-dia
	 []
	 (let [hospital {:espera       (ref h.model/fila-vazia)
									 :laboratorio1 (ref h.model/fila-vazia)
									 :laboratorio2 (ref h.model/fila-vazia)
									 :laboratorio3 (ref h.model/fila-vazia)}]
			(dosync
				 (chega-em! hospital "Polyana")
				 (chega-em! hospital "Maria")
				 (chega-em! hospital "Lucia")
				 (chega-em! hospital "Daniela")
				 (chega-em! hospital "Ana")
				 ;(chega-em! hospital "Paulo")
				 )
			(pprint hospital)
			))

;(simula-um-dia)

(defn async-chega-em!
	 [hospital pessoa]
	 (future
			(Thread/sleep (rand 5000))
			(dosync
				 (println "Tentando o cod sync para" pessoa)
				 (chega-em! hospital pessoa))))

(defn simula-um-dia-async
	 []
	 (let [hospital {:espera       (ref h.model/fila-vazia)
									 :laboratorio1 (ref h.model/fila-vazia)
									 :laboratorio2 (ref h.model/fila-vazia)
									 :laboratorio3 (ref h.model/fila-vazia)}
				 ;futures (mapv #(async-chega-em! hospital %) (range 10))
				 ]

			; simbolo global
			(def futures (mapv #(async-chega-em! hospital %) (range 10)))

			;(dotimes
			;	 [pessoa 10]
			;	 (async-chega-em! hospital pessoa))

			(future
				 (dotimes [n 4]
						(Thread/sleep 2000)
						(pprint hospital)
						(pprint futures)))))

(simula-um-dia-async)

;(println (future 15))
;(println (future ((Thread/sleep 1000) 15)))

; para testar o simbolo global 'futures' no REPL
;(use 'hospital.aula6) -> shift+enter
;futures -> enter