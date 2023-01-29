(ns hospital.logic
	 (:require [hospital.model :as h.model]))

(defn agora []
	 (h.model/to-ms (java.util.Date.)))

(defn- teste [] "123")

(println "teste dentro de logic" (teste))