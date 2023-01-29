(ns hospital.aula2
	 (:use clojure.pprint)
	 (:require [schema.core :as s]))
; pode ser desativada globalmente a qualquer momento, nao é indicado para validacoes de regras de negócio
(s/set-fn-validation! true)

; record é expansível pode receber mais do que esperada
; por isso que a validaçao do schema nao gera efeito
;(s/defrecord Paciente
;	 [id :- Long, nome :- s/Str])
;
;(pprint (Paciente. 15 "Guilherme"))
;(pprint (Paciente. "15" "Guilherme"))
;
;(pprint (->Paciente 15 "Guilherme"))
;(pprint (->Paciente "15" "Guilherme"))
;
;(pprint (map->Paciente {15 "Guilherme"}))
;(pprint (map->Paciente {"15" "Guilherme"}))

(def Paciente
	 "Schema de um paciente"
	 {:id s/Num, :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 15, :nome "Guilherme"}))

; typo é pego pelo schema, mas poderiamos argumentar que esse
; tipo de erro seria pego em testes automatizados com cobertura boa
;(pprint (s/validate Paciente {:id 15, :name "Guilherme"}))
; mas... entra a questao de querer ser forward compatible ou nao
; entender esse trade-off
; sistemas externos nao me quebrarao ao adicionar campos novas (foward compatible)
; no nosso validate nao estamos sendo foward compatible (pode ser interessante quando quero analisar mudanças)

; nao aceita novas keyword por nao ser foward compatible
;(pprint (s/validate Paciente {:id 15, :nome "Guilherme" :plano [:raio-x, :ultrassom]}))

; chaves que sao keywords em schemas sao por padrao obrigatorias
;(pprint (s/validate Paciente {:nome "Guilherme"}))

(s/defn novo-paciente :- Paciente
	 [id :- s/Num, nome :- s/Str]
	 {:id id, :nome nome})

; garantias de entrada e saida da funcao
; tipo de retorno com schema, força a validacao na saida da funcao
;(s/defn novo-paciente :- Paciente
;	 [id :- s/Num, nome :- s/Str]
;	 {:id id, :nome nome, :plano []})

(pprint (novo-paciente 15 "Guilherme"))

(defn estritamente-positivo?
	 [x]
	 (> x 0))

;(def EstritamentePositivo (s/pred estritamente-positivo?))
(def EstritamentePositivo (s/pred estritamente-positivo? 'estritamente-positivo))
(pprint (s/validate EstritamentePositivo 15))               ; é independente do s/set-fn-validation global
;(pprint (s/validate EstritamentePositivo -15))
;(pprint (s/validate EstritamentePositivo 0))

 ;(def Paciente
	;	"Schema de um paciente"
	;	{:id (s/constrained s/Int estritamente-positivo?), :nome s/Str})

;(pprint (s/validate Paciente {:id -15 :nome "Guilherme"}))

(def Paciente
	 "Schema de um paciente"
	 {:id (s/constrained s/Int pos?), :nome s/Str})

;(pprint (s/validate Paciente {:id -15 :nome "Guilherme"}))


; perdeu a facilidade de testar o lambda isoladamente
; nomes ficam confusos e legibilidade se perde
(def Paciente
	 "Schema de um paciente"
	 {:id (s/constrained s/Int #(> % 0) 'inteiro-extritamente-positivo), :nome s/Str})

;(pprint (s/validate Paciente {:id -15 :nome "Guilherme"}))