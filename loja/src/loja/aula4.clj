(ns loja.aula4
	 (:require [loja.db :as l.db]
						 [loja.logic :as l.logic]))

; ORDENAÇAO, REVERSE EXTRAINDO LÓGICA DE FUNÇOES PURAS

(println (l.db/todos-os-pedidos))

(let [pedidos (l.db/todos-os-pedidos)
			resumo (l.logic/resumo-por-usuario pedidos)]
	 (println "Resumo" resumo)
	 (println "Ordenado" (sort-by :preco-total resumo))
	 (println "Ordenado ao contrário" (reverse (sort-by :preco-total resumo)))
	 (println "Ordenado por id" (sort-by :usuario-id resumo))

	 (println (get-in pedidos [0 :itens :mochila :quantidade])))

(defn resumo-por-usuario-ordenado [pedidos]
	 (->> pedidos
				(l.logic/resumo-por-usuario)
				(sort-by :preco-total)
				reverse))

(let [pedidos (l.db/todos-os-pedidos)
			resumo (resumo-por-usuario-ordenado pedidos)]
	 (println "Resumo" resumo))


(let [pedidos (l.db/todos-os-pedidos)
			resumo (resumo-por-usuario-ordenado pedidos)]
	 (println "Resumo" resumo)
	 (println "Pimeiro" (first resumo))
	 (println "Segundo" (second resumo))
	 (println "Resto" (rest resumo))
	 (println "Total" (count resumo))
	 (println "Class" (class resumo))
	 (println "Enésimo elemento nth 3" (nth resumo 3))
	 (println "get 1" (get resumo 1))
	 (println "take" (take 2 resumo))                          ; devolve os n primeiros elementos
	 )

; (println "get 1" (get resumo 1)) get acessa apenas os indices de vetores e nao de persistentList

(let [pedidos (l.db/todos-os-pedidos)
			resumo (resumo-por-usuario-ordenado pedidos)]
	 (println "Resumo" resumo)
	 (println "500 Filter"(filter #(> (:preco-total %) 500) resumo))
	 (println "500 filter empty not =>" (not (empty? (filter #(> (:preco-total %) 500) resumo))))
	 (println "500 some =>" (some #(> (:preco-total %) 500) resumo)))




















