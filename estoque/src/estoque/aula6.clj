(ns estoque.aula6)

; MAP, FILTER E REDUCE COM HASHMAP

; coleções são hashmaps, vetores e listas

(def pedido {:mochila  {:quantidade 2, :preco 80, :taxa {:desconto 0}}
						 :camiseta {:quantidade 3, :preco 40}})

(defn imprime-e-15
	 [valor]
	 (println "valor" valor)
	 15)

(println (map imprime-e-15 pedido))

; Destructuring
(defn imprime-e-15 [[chave valor]]
	 (println chave "<e>" valor)
	 15)

(println (map imprime-e-15 pedido))

(defn imprime-e-15 [[chave valor]]
	 chave)

(println (map imprime-e-15 pedido))

(defn imprime-e-15 [[chave valor]]
	 valor)

(println (map imprime-e-15 pedido))

(defn preco-dos-produto [[_ valor]]
	 (* (:quantidade valor) (:preco valor)))

(println (map preco-dos-produto pedido))

(println (reduce + (map preco-dos-produto pedido)))

(defn total-do-pedido
	 [pedido]
	 (reduce + (map preco-dos-produto pedido)))

(println (total-do-pedido pedido))

; THREAD LAST MUITO UTILIZADA COM COLEÇOES

(defn total-do-pedido
	 [pedido]
	 (->> pedido
				(map preco-dos-produto,,,)                              ; ,,, é para indicar a posicao do argumento
				(reduce +,,,)))                                         ; ,,, virgulas vira espaços em brancos

(println (total-do-pedido pedido))

; Refatoraçao elegante

(defn preco-total-do-produto [produto]
	 (* (:quantidade produto) (:preco produto)))

;(println (preco-total-do-produto pedido))

(defn total-do-pedido
	 [pedido]
	 (->> pedido
				vals
				(map preco-total-do-produto)
				(reduce +)))

(println (total-do-pedido pedido))

(def pedido {:mochila  {:quantidade 2, :preco 80}
						 :camiseta {:quantidade 3, :preco 40}
						 :chaveiro {:quantidade 1}
						 :taxa     {:desconto 0}})

(defn gratuito?
	 [[_ item]]
	 (<= (get item :preco 0) 0))

(println (gratuito? (pedido 0)))

(println "Filtrando gratuitos")

(println (filter gratuito? pedido))

(defn gratuito?
	 [item]
	 (<= (get item :preco 0) 0))

(println (filter (fn [[chave item]] (gratuito? item)) pedido))

(println (filter #(gratuito? (second %)) pedido))

(println "aqui")
(println (vals pedido))
;(println (filter #(gratuito? (vals [%])) pedido))


(defn pago?
	 [item]
	 (not (gratuito? item)))

(println (pago? {:preco 50}))
(println (pago? {:preco 0}))

(println ((comp not gratuito?) {:preco 50}))

(def pago? (comp not gratuito?))
(println (pago? {:preco 50}))
(println (pago? {:preco 0}))