(ns hospital.aula1
	 (:use clojure.pprint))


(defn adiciona-paciente
	 [pacientes paciente]

	 (if-let [id (:id paciente)]
			(assoc pacientes id paciente)
			(throw
				 (ex-info "Paciente não possui id",
									{:paciente paciente})))
	 )

(defn testa-uso-pacientes
	 []
	 (let [pacientes {}
				 polyana {:id 11 :nome "Polyana" :nascimento "05/01/1965"}
				 daniela {:id 03 :nome "Daniela" :nascimento "12/04/1948"}
				 fabricio {:id 104 :nome "Fabricio" :nascimento "05/01/1923"}
				 ;paulo {:id nil :nome "Paulo" :nascimento "03/01/1968"}
				 ]

			(pprint (adiciona-paciente pacientes polyana))
			(pprint (adiciona-paciente pacientes daniela))
			(pprint (adiciona-paciente pacientes fabricio))
			;(pprint (adiciona-paciente pacientes paulo))
			))

(testa-uso-pacientes)

;(defrecord Paciente [ˆLong id, ˆString nome, ˆString nascimento])
; Fazendo interoperabilidade com Java para trabalhar orientado aos objetos
; A decisão de usar um Protocol ou um mapa pode estar relacionada a performance ou a questão de modelar aquela parte da aplicação com OO.
; o acesso a um campo de uma classe é mais rápido se feito de forma direta do que através de uma invocação dinâmica
(defrecord Paciente [id nome nascimento])

(println (->Paciente 15 "Guilherme" "12/05/1988"))
(pprint (->Paciente 15 "Guilherme" "12/05/1988"))

; instanciar uma classe java / construtor
(pprint (Paciente. "Guilherme" 15 "12/05/1988"))

(pprint
	 (map->Paciente {:id 15 :nome "Polyana" :nascimento "12/05/1988"}))

(let [guilherme (->Paciente 15 "Guilherme" "12/05/1988")]
	 (println (:id guilherme)) ; invocacao dinamica nao tao rapido quanto a invocao da forma direta .nome do java
	 (println (vals guilherme))
	 (println (class guilherme))
	 (println (record? guilherme))
	 (println (.nome guilherme)) ; acesso ao campo mais rápido, forma direta
	 )

; construir através de um map ele permite remover e add novas coisas
; serve para estruturas opcionais, nao tao definidas tipo NoSQL

(pprint
	 (map->Paciente {:id 15 :nome "Polyana" :nascimento "12/05/1988" :rg "22222222"}))

(pprint
	 (map->Paciente {:nome "Polyana" :nascimento "12/05/1988" :rg "22222222"}))

; Forma obrigotória / aridades obrigatória, de construir, mais semelhante a um construtor de um objeto da OO, remover e add novos campos nao é permitido
;(pprint (Paciente.  15 "12/05/1988"))
;(pprint (Paciente. "Polyana" 15 "12/05/1988" "2222222"))

;(pprint (->Paciente 15 "12/05/1988"))
;(pprint (->Paciente "Polyana" 15 "12/05/1988" "2222222"))


(pprint (assoc (Paciente. 15 "Polyana" "12/05/1988") :id 38))
(pprint (assoc (Paciente. nil "Polyana" "12/05/1988") :id 38))
(pprint (class (assoc (Paciente. nil "Polyana" "12/05/1988") :id 38)))
;(pprint (Paciente. 15 "Polyana" "12/05/1988"))

(pprint (= (Paciente. 15 "Polyana" "12/05/1988") (Paciente. 15 "Polyana" "12/05/1988")))
(pprint (= (Paciente. 135 "Polyana" "12/05/1988") (Paciente. 15 "Polyana" "12/05/1988")))






















