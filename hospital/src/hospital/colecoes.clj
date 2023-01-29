(ns hospital.colecoes
	 (:use [clojure.pprint]))

(defn testa-vetor []
	 (let [espera [111 222]]
			(println "Vetor" espera)
			(println (conj espera 333))                              ; add no final
			(println (conj espera 444))
			(println (pop espera))                                   ; remove do final
			))

(testa-vetor)

(defn testa-lista []
	 (let [espera '(111 222)]
			(println "Lista" espera)
			(println (conj espera 333))                              ; add no inicio
			(println (conj espera 444))
			(println (pop espera))                                   ; remove do inicio
			))

(testa-lista)

(defn testa-conjunto []
	 (let [espera #{111 222}]
			(println "Conjunto" espera)
			(println (conj espera 111)) ; ordem nao importal, mas nao deixa add um elemento que já existe dentro do conjunto
			(println (conj espera 333))
			(println (conj espera 444))
			;(println (pop espera)) nao funciona pop
			))

(testa-conjunto)

(defn testa-fila []
	 (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
			(println "Fila" (seq espera))
			(println (seq (conj espera "333")))                      ; add no final
			(println (seq (conj espera "444")))
			(println (seq (pop espera)))                             ; remove o primeiro
			(println (peek espera))                                  ; vê quem é o primeiro
			(pprint espera)))                                        ; pprint nao precisa transformar a fila em sequencia para poder ser impressa

(testa-fila)