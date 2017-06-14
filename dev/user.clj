;; from https://github.com/juxt/edge/blob/7909cb26ea131d8bf727ea744f4a3d5034f85e2a/dev/user.clj

(ns user
  (:require
   [clojure.pprint :refer [pprint]]
   [clojure.test :refer [run-all-tests]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.tools.namespace.repl :refer [refresh refresh-all]]
   [clojure.java.io :as io]
   ;[com.stuartsierra.component :as component]
   ;[clojure.core.async :as a :refer [>! <! >!! <!! chan buffer dropping-buffer sliding-buffer close! timeout alts! alts!! go-loop]]
   ;[edge.system :as system]
   [reloaded.repl :refer [system init start stop go reset reset-all]]
   ;[schema.core :as s]
   ;[yada.test :refer [response-for]]
   ))

;(defn new-dev-system
;  "Create a development system"
;  []
;  (component/system-using
;   (system/new-system-map (system/config :dev))
;   (system/new-dependency-map)))

;(reloaded.repl/set-init! new-dev-system)

;(defn check
;  "Check for component validation errors"
;  []
;  (let [errors
;        (->> system
;             (reduce-kv
;              (fn [acc k v]
;                (assoc acc k (s/check (type v) v)))
;              {})
;             (filter (comp some? second)))]
;
;    (when (seq errors) (into {} errors))))

(defn test-all []
  (run-all-tests #"edge.*test$"))

(defn reset-and-test []
  (reset)
  (time (test-all)))
