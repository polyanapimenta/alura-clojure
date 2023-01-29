(ns hospital.model
	 (:use clojure.pprint))

; extendendo tipos que já existe no Java/ou clojure
; serve para quando queremos ter uma interoperabilidade / integrçao ou criar um novo componente OO
(defprotocol Dateable
	 (to-ms [this]))

; todos os numeros em java vao ter uma funcao que converte para milisegundos
(extend-type java.lang.Number
	 Dateable
	 ;(to-ms [this] this) ou
	 (to-ms [this] (identity this)))

;(pprint (to-ms 56))

; todas as datas em java vao ter uma funcao que converte para milisegundos
(extend-type java.util.Date
	 Dateable
	 (to-ms [this] (.getTime this)))

;(pprint (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
	 Dateable
	 (to-ms [this] (to-ms (.getTime this))))

;(pprint (to-ms (java.util.GregorianCalendar.)))

;(pprint (to-ms "polyana"))

;Quando utilizar oo em clojure
;quando vc vai ter um componente, esconder / encapsular o comportamento interno
; funcional: basicamente são funcoes puras e imutabilidade
; OO: basicamente quando vc quer agrupar funçoes e comportamentos