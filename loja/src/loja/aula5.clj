(ns loja.aula5
	 (:require [loja.db :as l.db]
						 [loja.logic :as l.logic]))

(defn gastou-bastante?
	 [info-do-usuario]
	 (let [preco-total (:preco-total info-do-usuario)]
			(if (> preco-total 500)
				 preco-total
				 nil)))


(let [pedidos (l.db/todos-os-pedidos)
			resumo (l.logic/resumo-por-usuario pedidos)]
	 (println "Resumo" resumo)
	 (println "Keep" (keep gastou-bastante? resumo)))

(println (range 10))
(println (take 2 (range 10000000000)))
; imutabilidade a sequencia é a mesma, a sequencia nao está sendo "gulosa" eager

(let [sequencia (range 10000000000)]
	 (println (take 2 sequencia))
	 (println (take 2 sequencia)))
; está sendo Lazy preguiçoso, ou seja nao está gerando os 10000000.. e imprimimindo 0 1, está imprimindo conforme vai sendo necessário


(defn filtro1 [x]
	 (println "filtro1" x)
	 x)

(println (map filtro1 (range 10)))
; parece estar na ordem certa

(defn filtro2 [x]
	 (println "filtro2" x)
	 x)

(println (map filtro2 (map filtro1 (range 10))))
; o map está parecendo "guloso" eager

(->> (range 50)
		 (map filtro1)
		 (map filtro2)
		 println)
; vendo com uma sequencia maior, map hibrido com eager e lazy, está utilizando chunked-seq => trabalha em blocos, consegue ser preguiçoso  executa somente quando necessário mas tmb consegue ser eager executa de uma vez só até certo numero, dá chunk e volta  a repetir.

(->> (range 50)
		 (mapv filtro1)
		 (mapv filtro2)
		 println)
; mapv força a ser eager

(->> [1 2 3 4 5 6 7 8 9 0 8 7 6 5 5 43 3 2 2 1 1 1 1 2 3 4 4 5 66 7 788 99 7 5 4 4 3422 2434 2 3 4 5 6 6 7 8 8 9 9 6 4 3 2 2 1 1 2 3 3 35 4 3 3 2 2]
		 (map filtro1)
		 (map filtro2)
		 println)
; é chunked (meio eager meio lazy)

(->> '(1 2 3 4 5 6 7 8 9 0 8 7 6 5 5 43 3 2 2 1 1 1 1 2 3 4 4 5 66 7 788 99 7 5 4 4 3422 2434 2 3 4 5 6 6 7 8 8 9 9 6 4 3 2 2 1 1 2 3 3 35 4 3 3 2 2)
		 (map filtro1)
		 (map filtro2)
		 println)
; lista ligada é 100% lazy, nao tem o hibrido dos chunks (meio eager meio lazy)


; lazy é bom para infinito mumeros grandes pq nao queremos travar e esperar por toda aquela execuçao
; eager é bom para coisas pequenas pq nao vai fazer uma grande diferença





