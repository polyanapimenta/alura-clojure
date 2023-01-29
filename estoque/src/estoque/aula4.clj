(ns estoque.aula4)

; COLEÇÕES

(def precos [30 700 1000])

(println (precos 0))
(println (get precos 0))                                    ; pega valor dentro do array
(println (get precos 1))
;(println (precos 17))                                      ; dá erro, IndexOutOfBoundsException
(println "valor padrao nil" (get precos 17))                ; usa get para evitar exceptions, pois nao retorna erro, retorna nil
(println "valor padrao 0" (get precos 17 0))
(println "valor padrao 0, mas existe" (get precos 2 0))

(println (conj precos 5))                                   ; vetor precos ainda nao foi atualizado, retorna um vetor novo
(println precos)

;(println (conj 5 precos))                                  ; long nao é uma colecao para persistencia IPersistentCollection

(println (+ 5 1))
(println (inc 5))
(println (update precos 0 inc))                             ; devolve um vetor novo, nao altera o vetor original nunca
(println precos)
(println (update precos 0 dec))
(println precos)

(defn soma-1
	 [valor]
	 (println "Estou somando 1 em" valor)
	 (+ valor 1))

(println (update precos 0 soma-1))
(println precos)


(println "################################")

(defn aplica-desconto?
	 [valor-bruto]
	 (> valor-bruto 100))

(defn valor-descontado
	 "Retorna o valor com desconto de 10% se deve aplicar desconto."
	 [valor-bruto]
	 (if (aplica-desconto? valor-bruto)
			(let [taxa-de-desconto (/ 10 100)
						desconto (* valor-bruto taxa-de-desconto)]
				 (- valor-bruto desconto))
			valor-bruto))


(println "Map")

; map vai mapear, vai aplicar a funcao "valor-descontado" para todos os "precos"

(println "map" (map valor-descontado precos))               ; nao altera o vetor precos
(println precos)

(println (range 10))
(println (filter even? (range 10)))                         ; mantém apenas no novo vetor valores verdadeiros

(println "filter"
				 (filter aplica-desconto? precos))                      ; novo vetor dos valore que aplica desconto

(println "map após o filter"
				 (map
						valor-descontado
						(filter aplica-desconto? precos)))

(println (reduce + precos))

(defn minha-soma
	 [valor-1 valor-2]
	 (println "Somando" valor-1 valor-2)
	 (+ valor-1 valor-2))

(println (reduce minha-soma precos))
(println (reduce minha-soma (range 10)))                    ; resultado da funcao soma com o proximo elemento
(println (reduce minha-soma 0 precos))
(println (reduce minha-soma 0 [15]))