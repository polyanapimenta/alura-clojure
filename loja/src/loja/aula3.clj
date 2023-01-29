(ns loja.aula3
	 (:require [loja.db :as l.db]))

(println (l.db/todos-os-pedidos))

(println (group-by :usuario (l.db/todos-os-pedidos)))

(defn minha-funcao-agrupamento
	 [elemento]
	 (println "Elemento" elemento)
	 (:usuario elemento))

(println (group-by minha-funcao-agrupamento (l.db/todos-os-pedidos)))

(println (count (vals (group-by :usuario (l.db/todos-os-pedidos)))))

; 15 [x,y,z,a]
; 10 [x]
; 20 [x,y]

(println (map count (vals (group-by :usuario (l.db/todos-os-pedidos)))))

























