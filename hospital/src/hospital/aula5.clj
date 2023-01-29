(ns hospital.aula5
	 (:use [clojure.pprint])
	 (:require [hospital.logic :as h.logic]
						 [hospital.model :as h.model]))

(defn chega-em! [hospital pessoa]
	 (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere!
	 [hospital de para]
	 (swap! hospital h.logic/transfere de para))

(defn simula-um-dia []
	 (let [hospital (atom (h.model/novo-hospital))]
			(println "############## hospital esqueleto")
			(pprint hospital)

			(chega-em! hospital "maria")
			(chega-em! hospital "hyago")
			(chega-em! hospital "marco")
			(chega-em! hospital "poly")

			(println "############## hospital esqueleto alterado")
			(pprint hospital)

			(transfere! hospital :espera :laboratorio1)
			(transfere! hospital :espera :laboratorio2)
			(transfere! hospital :espera :laboratorio2)
			(transfere! hospital :laboratorio2 :laboratorio3)

			(println "############## hospital final")
			(pprint hospital)

			))

(simula-um-dia)